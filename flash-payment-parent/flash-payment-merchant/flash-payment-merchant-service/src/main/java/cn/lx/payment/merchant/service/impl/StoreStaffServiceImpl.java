package cn.lx.payment.merchant.service.impl;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.merchant.api.IStoreStaffService;
import cn.lx.payment.merchant.entity.StoreStaff;
import cn.lx.payment.merchant.mapper.StoreStaffMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2020-09-22
 */
@Slf4j
@Service
@Transactional
public class StoreStaffServiceImpl implements IStoreStaffService {

    @Autowired
    private StoreStaffMapper storeStaffMapper;


    /**
     * 为门店设置管理员
     *
     * @param staffId 员工id
     * @param storeId 门店id
     * @throws BusinessException
     */
    @Override
    public void bindStaffToStore(Long staffId, Long storeId) throws BusinessException {
        StoreStaff storeStaff = new StoreStaff();
        storeStaff.setStaffId(staffId);
        storeStaff.setStoreId(storeId);
        storeStaffMapper.insert(storeStaff);
    }
}
