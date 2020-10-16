package cn.lx.payment.agent.api;

import cn.lx.payment.agent.dto.AliConfigParam;
import cn.lx.payment.agent.dto.AlipayBean;
import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.PaymentResponseDTO;

/**
 * cn.lx.payment.agent.api
 *
 * @Author Administrator
 * @date 12:09
 */
public interface IPayChannelAgentService {

    /**
     * 支付宝支付
     * @param aliConfigParam 支付宝支付接口公共参数
     * @param alipayBean    个人相关
     * @return
     */
    PaymentResponseDTO<String> createPayOrderByAliWAP(AliConfigParam aliConfigParam, AlipayBean alipayBean) throws BusinessException;
}
