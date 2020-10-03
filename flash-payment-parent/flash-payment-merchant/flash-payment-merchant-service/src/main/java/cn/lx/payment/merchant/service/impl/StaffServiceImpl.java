package cn.lx.payment.merchant.service.impl;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.merchant.api.IStaffService;
import cn.lx.payment.merchant.covert.StaffCovert;
import cn.lx.payment.merchant.dto.StaffDTO;
import cn.lx.payment.merchant.entity.Staff;
import cn.lx.payment.merchant.mapper.StaffMapper;
import cn.lx.payment.util.PhoneUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-09-22
 */
@Slf4j
@Service
@Transactional
public class StaffServiceImpl implements IStaffService {

    @Autowired
    private StaffMapper staffMapper;

    /**
     * 商户新增员工
     *
     * @param staffDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public StaffDTO createStaff(StaffDTO staffDTO) throws BusinessException {
        //校验手机号
        checkMobile(staffDTO.getMerchantId(), staffDTO.getMobile());
        //校验用户名
        checkUsername(staffDTO.getMerchantId(), staffDTO.getUsername());
        Staff staff = StaffCovert.instance.dto2entity(staffDTO);
        staffMapper.insert(staff);
        log.info("商户新增员工"+ JSON.toJSONString(staff));
        return StaffCovert.instance.entity2dto(staff);
    }

    /**
     * 校验用户名
     *
     * @param merchantId 商户id
     * @param username   用户名
     * @throws BusinessException
     */
    private void checkUsername(Long merchantId, String username) throws BusinessException {
        //校验用户名是否为空
        if (Strings.isNullOrEmpty(username)) {
            throw new BusinessException(CommonErrorCode.E_100110);
        }
        //校验用户名的唯一性
        Integer count = staffMapper.selectCount(new LambdaQueryWrapper<Staff>()
                .eq(Staff::getMerchantId, merchantId)
                .eq(Staff::getMobile, username));
        if (count > 0) {
            throw new BusinessException(CommonErrorCode.E_100114);
        }
    }

    /**
     * 校验手机号
     *
     * @param merchantId 商户id
     * @param mobile     手机号
     */
    private void checkMobile(Long merchantId, String mobile) throws BusinessException {
        //校验手机号是否为空
        if (Strings.isNullOrEmpty(mobile)) {
            throw new BusinessException(CommonErrorCode.E_100112);
        }
        //校验手机号的格式
        if (!PhoneUtil.isMatches(mobile)) {
            throw new BusinessException(CommonErrorCode.E_100109);
        }
        //校验手机号的唯一性
        Integer count = staffMapper.selectCount(new LambdaQueryWrapper<Staff>()
                .eq(Staff::getMerchantId, merchantId)
                .eq(Staff::getMobile, mobile));
        if (count > 0) {
            throw new BusinessException(CommonErrorCode.E_100113);
        }
    }
}
