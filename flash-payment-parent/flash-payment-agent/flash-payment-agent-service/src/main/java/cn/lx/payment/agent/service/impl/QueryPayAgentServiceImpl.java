package cn.lx.payment.agent.service.impl;

import cn.lx.payment.agent.api.IQueryPayAgentService;
import cn.lx.payment.agent.common.AliCodeConstants;
import cn.lx.payment.agent.common.CovertTradeStatus;
import cn.lx.payment.agent.dto.AliConfigParam;
import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.domain.PaymentResponseDTO;
import cn.lx.payment.domain.TradeStatus;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * cn.lx.payment.agent.service.impl
 *
 * @Author Administrator
 * @date 10:04
 */
@Service
@Slf4j
public class QueryPayAgentServiceImpl implements IQueryPayAgentService {

    /**
     * 根据付宝交易号或商户订单号查询订单信息
     *
     * @param out_trade_no   支付时传入的商户订单号
     * @param trade_no       支付时返回的支付宝交易号
     * @param aliConfigParam 阿里支付的相关配置
     * @return
     */
    @Override
    public PaymentResponseDTO<Object> queryAliOrder(String out_trade_no, String trade_no, AliConfigParam aliConfigParam){
        AlipayClient alipayClient = new DefaultAlipayClient(aliConfigParam.getUrl(),
                aliConfigParam.getAppId(),
                aliConfigParam.getRsaPrivateKey(),
                aliConfigParam.getFormat(),
                aliConfigParam.getCharest(),
                aliConfigParam.getAlipayPublicKey(),
                aliConfigParam.getSigntype());

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                " \"out_trade_no\":\"SJ1317409012386033664\"," +
                " \"trade_no\":\"2020101722001456430501139296\"" +
                " }");//设置业务参数
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            log.error("查询支付宝订单信息失败:{}", e.getErrMsg());
            //throw new BusinessException(CommonErrorCode.E_400003);
        }
        if (AliCodeConstants.SUCCESSCODE.equals(response.getCode())) {
            log.info("查询支付宝订单信息成功:{}", response.getBody());
            //将支付宝响应的状态转换为闪聚平台的状态
            TradeStatus tradeStatus = CovertTradeStatus.covertAliTradeStatusToShanjuCode(response.getTradeStatus());
            //规范返回信息
             return PaymentResponseDTO.success(
                    response.getTradeNo(),
                    response.getOutTradeNo(),
                    tradeStatus,
                    response.getMsg() + " " + response.getSubMsg());


        } else {
            log.error("查询支付宝订单信息失败");
        }
        return  PaymentResponseDTO.fail("查询支付宝支付结果异常",
                response.getOutTradeNo(),
                TradeStatus.UNKNOWN);
    }
}
