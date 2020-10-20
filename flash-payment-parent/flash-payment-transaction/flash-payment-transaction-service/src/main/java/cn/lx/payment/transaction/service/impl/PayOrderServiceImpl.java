package cn.lx.payment.transaction.service.impl;

import cn.lx.payment.agent.api.IPayChannelAgentService;
import cn.lx.payment.agent.dto.AliConfigParam;
import cn.lx.payment.agent.dto.AlipayBean;
import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.domain.PaymentResponseDTO;
import cn.lx.payment.merchant.api.IMerchantService;
import cn.lx.payment.merchant.dto.MerchantDTO;
import cn.lx.payment.transaction.api.IPayChannelParamService;
import cn.lx.payment.transaction.api.IPayOrderService;
import cn.lx.payment.transaction.covert.PayOrderCovert;
import cn.lx.payment.transaction.dto.PayChannelParamDTO;
import cn.lx.payment.transaction.dto.PayOrderDTO;
import cn.lx.payment.transaction.entity.PayOrder;
import cn.lx.payment.transaction.mapper.PayOrderMapper;
import cn.lx.payment.util.EncryptUtil;
import cn.lx.payment.util.PaymentUtil;
import cn.lx.payment.util.QRCodeUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
@Slf4j
@Service
@Transactional
public class PayOrderServiceImpl implements IPayOrderService {

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Reference
    private IMerchantService iMerchantService;

    @Reference
    private IPayChannelAgentService iPayChannelAgentService;

    @Autowired
    private IPayChannelParamService iPayChannelParamService;


    @Value("${flash.pay.c2b.subject}")
    private String subject;//商品标题

    @Value("${flash.pay.c2b.body}")
    private String body;//订单描述

    @Value("${flash.pay.c2b.payurl}")
    private String payurl;//支付路径


    /**
     * 生成闪聚服务的订单和二维码
     *
     * @param merchantId
     * @param appId
     * @param storeId
     * @return
     */
    @Override
    public String createCScanBStoreQRCode(Long merchantId, String appId, Long storeId) throws BusinessException {

        MerchantDTO merchantDTO = iMerchantService.queryMerchantById(merchantId);

        subject = subject.replace("%s", merchantDTO.getMerchantName());
        body = body.replace("%s", merchantDTO.getUsername());

        PayOrder payOrder = new PayOrder();
        //所属商户
        payOrder.setMerchantId(merchantId);
        //所属应用
        payOrder.setAppId(appId);
        //商户下门店
        payOrder.setStoreId(storeId);
        //商品标题
        payOrder.setSubject(subject);
        //订单描述
        payOrder.setBody(body);


        String payOrderString = JSON.toJSONString(payOrder);
        log.info("二维码信息 {}" + payOrderString);

        String ticket = EncryptUtil.encodeUTF8StringBase64(payOrderString);
        //支付入口
        String payEntryUrl = payurl + ticket;
        log.info("支付入口 {}", payEntryUrl);

        //生成二维码
        try {
            String qrCode = QRCodeUtil.createQRCode(payEntryUrl, 200, 200);
            return qrCode;
        } catch (IOException e) {
            throw new BusinessException(CommonErrorCode.E_200007);
        }
    }

    /**
     * 保存订单,调用支付代理服务，进行支付
     *
     * @param payOrderDTO
     * @return
     */
    @Override
    public PaymentResponseDTO<String> submitOrderByAli(PayOrderDTO payOrderDTO) throws BusinessException {
        //币种CNY
        payOrderDTO.setCurrency("￥");
        //创建时间
        payOrderDTO.setCreateTime(new Date());
        //订单过期时间,30分钟
        payOrderDTO.setExpireTime(new Date(System.currentTimeMillis() + 1000 * 60 * 30));
        //交易状态支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成,4-关闭
        payOrderDTO.setTradeState("0");
        //聚合支付的渠道
        payOrderDTO.setChannel("shanju_c2b");
        //原始支付渠道
        payOrderDTO.setPayChannel("ALIPAY_WAP");

        PayOrder payOrder = PayOrderCovert.INSTANCE.dto2entity(payOrderDTO);
        //设置聚合支付订单号
        payOrder.setTradeNo(PaymentUtil.genUniquePayOrderNo());
        //保存
        payOrderMapper.insert(payOrder);


        AlipayBean alipayBean = new AlipayBean();
        //设置聚合订单号
        alipayBean.setOutTradeNo(payOrder.getTradeNo());
        //订单名称
        alipayBean.setSubject(payOrder.getSubject());
        //付款金额
        alipayBean.setTotalAmount(payOrder.getTotalAmount().toString());
        //产品编号
        alipayBean.setProductCode("QUICK_WAP_PAY");
        //商品描述
        alipayBean.setBody(payOrder.getBody());
        //超时时间
        alipayBean.setExpireTime("30m");
        //门店id
        alipayBean.setStoreId(payOrder.getStoreId());
        //根据条件查询支付参数信息
        PayChannelParamDTO payChannelParamDTO = iPayChannelParamService.queryPayChannelParam(payOrder.getAppId(), payOrder.getChannel(), payOrder.getPayChannel());
        if (payChannelParamDTO == null) {
            throw new BusinessException(CommonErrorCode.E_300007);
        }
        //支付宝渠道参数
        AliConfigParam aliConfigParam = JSON.parseObject(payChannelParamDTO.getParam(), AliConfigParam.class);
        aliConfigParam.setCharest("utf-8");

        //去agent服务创建支付宝订单支付
        return iPayChannelAgentService.createPayOrderByAliWAP(aliConfigParam, alipayBean);
    }


    /**
     * 校验订单号和支付金额
     *
     * @param out_trade_no 商户订单号
     * @param total_amount 支付金额
     * @param trade_no     支付宝交易号
     * @param gmt_payment  付款成功时间
     */
    @Override
    public void verifyReturnParam(String out_trade_no, String total_amount, String trade_no, String gmt_payment) throws ParseException, BusinessException {
        PayOrder payOrder = payOrderMapper.selectOne(new LambdaQueryWrapper<PayOrder>().eq(PayOrder::getTradeNo, out_trade_no)
                .eq(PayOrder::getTotalAmount, total_amount));
        if (payOrder == null) {
            throw new BusinessException(CommonErrorCode.E_300013);
        }
        //支付宝交易凭证号
        payOrder.setOutTradeNo(trade_no);
        //交易状态支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成,4-关闭
        payOrder.setTradeState("2");
        //更新时间
        payOrder.setUpdateTime(new Date());
        //支付成功时间
        payOrder.setPaySuccessTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(gmt_payment));

        //更新数据库
        payOrderMapper.updateById(payOrder);
    }

    /**
     * 修改数据库订单状态
     *
     * @param outTradeNo 商户订单号
     * @param tradeNo    支付宝交易号
     * @param state      状态
     */
    @Override
    public void updateTradeStateByNo(String outTradeNo, String tradeNo, String state) {

        payOrderMapper.update(null
                , new LambdaUpdateWrapper<PayOrder>()
                        .eq(PayOrder::getTradeNo, outTradeNo)
                        .set(PayOrder::getOutTradeNo, tradeNo)
                        .set(PayOrder::getTradeState, state));
    }
}
