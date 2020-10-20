package cn.lx.payment.transaction.api;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.PaymentResponseDTO;
import cn.lx.payment.transaction.dto.PayOrderDTO;

import java.text.ParseException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
public interface IPayOrderService {

    /**
     * 生成闪聚服务的订单和二维码
     *
     * @param merchantId
     * @param appId
     * @param storeId
     * @return
     */
    String createCScanBStoreQRCode(Long merchantId, String appId, Long storeId) throws BusinessException;

    /**
     * 保存订单,调用支付代理服务，进行支付
     *
     * @param payOrderDTO
     * @return
     */
    PaymentResponseDTO<String> submitOrderByAli(PayOrderDTO payOrderDTO) throws BusinessException;


    /**
     * 校验订单号和支付金额
     *
     * @param out_trade_no 商户订单号
     * @param total_amount 支付金额
     * @param trade_no     支付宝交易号
     * @param gmt_payment  付款成功时间
     */
    void verifyReturnParam(String out_trade_no, String total_amount, String trade_no, String gmt_payment) throws ParseException, BusinessException;

    /**
     * 修改数据库订单状态
     *
     * @param outTradeNo 商户订单号
     * @param tradeNo    支付宝交易号
     * @param state      状态
     */
    void updateTradeStateByNo(String outTradeNo, String tradeNo, String state);
}
