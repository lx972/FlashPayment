package cn.lx.payment.merchant.api;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.merchant.dto.MerchantDTO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author author
 * @since 2020-09-22
 */
public interface IMerchantService {

    /**
     * 根据id查询企业所有人信息
     *
     * @param id
     * @return
     */
    MerchantDTO queryMerchantById(String id) throws BusinessException;

    /**
     * 注册商户
     *
     * @param merchantDTO
     * @return
     */
    MerchantDTO registerMerchant(MerchantDTO merchantDTO) throws BusinessException;

    /**
     * 根据租户id查询企业所有人信息
     *
     * @param tenantId
     * @return
     */
    MerchantDTO queryMerchantByTenantId(Long tenantId) throws BusinessException;

    /**
     * 商户资质申请
     *  @param merchantId  商户id
     * @param merchantDTO
     * @return
     */
    MerchantDTO applayMerchant(Long merchantId, MerchantDTO merchantDTO) throws BusinessException;

}
