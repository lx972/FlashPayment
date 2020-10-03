package cn.lx.payment.merchant.api;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.merchant.dto.StaffDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2020-09-22
 */
public interface IStaffService {


    /**
     * 商户新增员工
     * @param staffDTO
     * @return
     * @throws BusinessException
     */
    StaffDTO createStaff(StaffDTO staffDTO)throws BusinessException;

}
