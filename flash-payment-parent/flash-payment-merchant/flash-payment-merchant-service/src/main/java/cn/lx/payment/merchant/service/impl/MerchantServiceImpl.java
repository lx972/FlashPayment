package cn.lx.payment.merchant.service.impl;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.merchant.api.IMerchantService;
import cn.lx.payment.merchant.covert.MerchantCovert;
import cn.lx.payment.merchant.dto.MerchantDTO;
import cn.lx.payment.merchant.entity.Merchant;
import cn.lx.payment.merchant.mapper.MerchantMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
public class MerchantServiceImpl implements IMerchantService {

    @Autowired
    private MerchantMapper merchantMapper;


    /**
     * 根据id查询企业所有人信息
     *
     * @param id
     * @return
     */
    @Override
    public MerchantDTO queryMerchantById(String id) throws BusinessException {
        Merchant merchant = merchantMapper.selectById(id);
        MerchantDTO merchantDTO = MerchantCovert.instance.entity2dto(merchant);
        return merchantDTO;
    }

    /**
     * 注册商户
     *
     * @param merchantDTO
     */
    @Override
    public void registerMerchant(MerchantDTO merchantDTO) throws BusinessException {

        //手机号唯一性校验
        Integer count = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getMobile, merchantDTO.getMobile()));
        if (count > 0) {
            throw new BusinessException(CommonErrorCode.E_100113);
        }
        Merchant merchant = MerchantCovert.instance.dto2entity(merchantDTO);
        //状态：未审核
        merchant.setAuditStatus("0");
        merchantMapper.insert(merchant);
    }

    /**
     * 根据租户id查询企业所有人信息
     *
     * @param tenantId
     * @return
     */
    @Override
    public MerchantDTO queryMerchantByTenantId(Long tenantId) throws BusinessException {
        //租户id非空校验
        if (tenantId == null) {
            throw new BusinessException(CommonErrorCode.E_999910);
        }
        Merchant merchant = merchantMapper.selectOne(new LambdaQueryWrapper<Merchant>().eq(Merchant::getTenantId, tenantId));
        if (null == merchant) {
            throw new BusinessException(CommonErrorCode.E_200012);
        }
        MerchantDTO merchantDTO = MerchantCovert.instance.entity2dto(merchant);
        return merchantDTO;
    }

    /**
     * 商户资质申请
     *
     * @param merchantId  商户id
     * @param merchantDTO
     */
    @Override
    public void applayMerchant(Long merchantId, MerchantDTO merchantDTO) throws BusinessException {
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant == null) {
            throw new BusinessException(CommonErrorCode.E_200002);
        }
        Merchant merchantUpdate = MerchantCovert.instance.dto2entity(merchantDTO);
        //待审核
        merchantUpdate.setAuditStatus("1");
        //租户id
        merchantUpdate.setTenantId(merchant.getTenantId());
        merchantUpdate.setId(merchantId);
        //更新
        merchantMapper.updateById(merchantUpdate);

    }


}
