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
}
