package cn.lx.payment.transaction.service.impl;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.merchant.api.IMerchantService;
import cn.lx.payment.merchant.dto.MerchantDTO;
import cn.lx.payment.transaction.api.IPayOrderService;
import cn.lx.payment.transaction.entity.PayOrder;
import cn.lx.payment.transaction.mapper.PayOrderMapper;
import cn.lx.payment.util.EncryptUtil;
import cn.lx.payment.util.QRCodeUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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

        /*//币种CNY
        payOrder.setCurrency("￥");
        //创建时间
        payOrder.setCreateTime(new Date());
        //订单过期时间,2小时
        payOrder.setExpireTime(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2));
        //交易状态支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成,4-关闭
        payOrder.setTradeState("0");
        //聚合支付的渠道
        payOrder.setChannel("shanju_c2b");*/

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
}
