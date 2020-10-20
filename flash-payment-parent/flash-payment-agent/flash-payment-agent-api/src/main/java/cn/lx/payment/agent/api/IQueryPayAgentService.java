package cn.lx.payment.agent.api;

import cn.lx.payment.agent.dto.AliConfigParam;
import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.PaymentResponseDTO;

/**
 * cn.lx.payment.agent.api
 *
 * @Author Administrator
 * @date 12:09
 */
public interface IQueryPayAgentService {

    /**
     * 根据付宝交易号或商户订单号查询订单信息
     *
     * @param out_trade_no   支付时传入的商户订单号
     * @param trade_no       支付时返回的支付宝交易号
     * @param aliConfigParam 阿里支付的相关配置
     * @return
     */
    PaymentResponseDTO<Object> queryAliOrder(String out_trade_no, String trade_no, AliConfigParam aliConfigParam) ;
}
