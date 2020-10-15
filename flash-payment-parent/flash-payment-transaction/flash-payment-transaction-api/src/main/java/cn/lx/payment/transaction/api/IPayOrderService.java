package cn.lx.payment.transaction.api;

import cn.lx.payment.domain.BusinessException;

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
}
