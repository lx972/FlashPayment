package cn.lx.payment.transaction.api;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.PaymentResponseDTO;
import cn.lx.payment.transaction.dto.PayOrderDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
public interface IPayOrderService {

    /**
     * 生成闪聚服务的订单和二维码
     * @param merchantId
     * @param appId
     * @param storeId
     * @return
     */
    String createCScanBStoreQRCode(Long merchantId, String appId, Long storeId) throws BusinessException;

    /**
     * 保存订单,调用支付代理服务，进行支付
     * @param payOrderDTO
     * @return
     */
    PaymentResponseDTO<String> submitOrderByAli(PayOrderDTO payOrderDTO)throws BusinessException;
}
