package cn.lx.payment.agent.service.impl;

import cn.lx.payment.agent.api.IPayChannelAgentService;
import cn.lx.payment.agent.dto.AliConfigParam;
import cn.lx.payment.agent.dto.AlipayBean;
import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.domain.PaymentResponseDTO;
import cn.lx.payment.agent.rocketmq.MqSendUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * cn.lx.payment.agent.service.impl
 *
 * @Author Administrator
 * @date 12:10
 */
@Service
@Slf4j
public class PayChannelAgentServiceImpl implements IPayChannelAgentService {

    @Autowired
    private MqSendUtil mqSendUtil;

    /**
     * 支付宝支付wap
     *
     * @param aliConfigParam 支付宝支付接口公共参数
     * @param alipayBean     个人相关
     * @return
     */
    @Override
    public PaymentResponseDTO<String> createPayOrderByAliWAP(AliConfigParam aliConfigParam, AlipayBean alipayBean) throws BusinessException {

        AlipayClient alipayClient = new DefaultAlipayClient(aliConfigParam.getUrl(),
                aliConfigParam.getAppId(),
                aliConfigParam.getRsaPrivateKey(),
                aliConfigParam.getFormat(),
                aliConfigParam.getCharest(),
                aliConfigParam.getAlipayPublicKey(),
                aliConfigParam.getSigntype());

        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();

        AlipayTradeWapPayModel alipayTradeWapPayModel = new AlipayTradeWapPayModel();
        //封装订单相关参数
        alipayTradeWapPayModel.setOutTradeNo(alipayBean.getOutTradeNo());
        alipayTradeWapPayModel.setSubject(alipayBean.getSubject());
        alipayTradeWapPayModel.setTotalAmount(alipayBean.getTotalAmount());
        alipayTradeWapPayModel.setProductCode(alipayBean.getProductCode());
        alipayTradeWapPayModel.setBody(alipayBean.getBody());
        alipayTradeWapPayModel.setTimeoutExpress(alipayBean.getExpireTime());
        //支付宝订单相关参数
        request.setBizModel(alipayTradeWapPayModel);
        //设置同步地址
        request.setReturnUrl(aliConfigParam.getReturnUrl());
        //设置异步地址
        request.setNotifyUrl(aliConfigParam.getNotifyUrl());
        AlipayTradeWapPayResponse response = null;
        try {
            //发送延时消息，30分钟后查询订单状态，30分钟内未支付，订单关闭
            mqSendUtil.sendDelayMsg(alipayBean.getOutTradeNo(), "ALIPAY_WAP", aliConfigParam);
            response = alipayClient.pageExecute(request);
        } catch (AlipayApiException e) {
            throw new BusinessException(CommonErrorCode.E_400002);
        }
        if (response.isSuccess()) {
            log.info("调用agent成功");
            PaymentResponseDTO<String> paymentResponseDTO = new PaymentResponseDTO<>();
            paymentResponseDTO.setContent(response.getBody());
            return paymentResponseDTO;
        } else {
            log.info("调用agent失败");
            return null;
        }

    }

}
