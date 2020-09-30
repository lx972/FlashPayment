package cn.lx.payment.merchant.api;

import cn.lx.payment.merchant.dto.MerchantDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2020-09-22
 */
public interface IMerchantService {

    /**
     * 根据id查询企业所有人信息
     * @param id
     * @return
     */
    MerchantDTO queryMerchantById(String id);

    /**
     * 注册商户
     * @param merchantDTO
     */
    void registerMerchant(MerchantDTO merchantDTO);

    /**
     * 根据租户id查询企业所有人信息
     * @param tenantId
     * @return
     */
    MerchantDTO queryMerchantByTenantId(Long tenantId);

    /**
     * 商户资质申请
     * @param merchantId 商户id
     * @param merchantDTO
     */
    void applayMerchant(Long merchantId, MerchantDTO merchantDTO);
}
