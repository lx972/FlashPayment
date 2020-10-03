package cn.lx.payment.merchant.api;


import cn.lx.payment.domain.BusinessException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2020-09-22
 */
public interface IStoreStaffService {


    /**
     * 为门店设置管理员
     *
     * @param staffId 员工id
     * @param storeId 门店id
     * @throws BusinessException
     */
    void bindStaffToStore(Long staffId, Long storeId) throws BusinessException;
}
