package cn.lx.payment.merchant.service.impl;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.merchant.api.IMerchantService;
import cn.lx.payment.merchant.api.IStaffService;
import cn.lx.payment.merchant.api.IStoreStaffService;
import cn.lx.payment.merchant.covert.MerchantCovert;
import cn.lx.payment.merchant.dto.MerchantDTO;
import cn.lx.payment.merchant.dto.StaffDTO;
import cn.lx.payment.merchant.entity.Merchant;
import cn.lx.payment.merchant.entity.Store;
import cn.lx.payment.merchant.mapper.MerchantMapper;
import cn.lx.payment.merchant.mapper.StoreMapper;
import cn.lx.payment.user.api.TenantService;
import cn.lx.payment.user.api.dto.tenant.CreateTenantRequestDTO;
import cn.lx.payment.user.api.dto.tenant.TenantDTO;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
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

    @Reference
    private TenantService tenantService;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private IStaffService iStaffService;

    @Autowired
    private IStoreStaffService iStoreStaffService;


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
     * @return
     */
    @Override
    public MerchantDTO registerMerchant(MerchantDTO merchantDTO) throws BusinessException {

        //手机号唯一性校验
        Integer count = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getMobile, merchantDTO.getMobile()));
        if (count > 0) {
            throw new BusinessException(CommonErrorCode.E_100113);
        }

        //添加租户 和账号 并绑定关系
        CreateTenantRequestDTO createTenantRequest = new CreateTenantRequestDTO();
        //设置租户名称
        createTenantRequest.setName(merchantDTO.getUsername());
        //设置租户类型编码
        createTenantRequest.setTenantTypeCode("shanju-merchant");
        //设置套餐编码
        createTenantRequest.setBundleCode("shanju-merchant");
        //设置租户管理员登录手机号
        createTenantRequest.setMobile(merchantDTO.getMobile());
        //设置租户管理员登录用户名
        createTenantRequest.setUsername(merchantDTO.getUsername());
        //设置租户管理员登录密码
        createTenantRequest.setPassword(merchantDTO.getPassword());
        log.info("商户中心调用统一账号服务，新增租户和账号");
        TenantDTO tenantAndAccount = tenantService.createTenantAndAccount(createTenantRequest);
        if (tenantAndAccount==null||tenantAndAccount.getId()==null){
            throw new BusinessException(CommonErrorCode.E_200012);
        }
        //判断租户下是否已经注册过商户
        Integer tenantCount = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>()
                .eq(Merchant::getTenantId, tenantAndAccount.getId()));
        if (tenantCount>0){
            throw new BusinessException(CommonErrorCode.E_200017);
        }
        Merchant merchant = MerchantCovert.instance.dto2entity(merchantDTO);
        //设置状态：未审核
        merchant.setAuditStatus("0");
        //设置租户id
        merchant.setTenantId(tenantAndAccount.getId());
        log.info("保存商户注册信息");
        //保存商户注册信息
        merchantMapper.insert(merchant);

        //新增门店，创建根门店
        Store store = new Store();
        //门店绑定商户id
        store.setMerchantId(merchant.getId());
        store.setStoreName("根门店");
        //0表示禁用，1表示启用
        store.setStoreStatus(1);
        storeMapper.insert(store);
        log.info("商户下新增门店"+ JSON.toJSONString(store));

        //新增员工，并设置归属门店
        StaffDTO staffDTO = new StaffDTO();
        //设置商户id
        staffDTO.setMerchantId(merchant.getId());
        //设置手机号
        staffDTO.setMobile(merchant.getMobile());
        //设置员工用户名
        staffDTO.setUsername(merchant.getUsername());
        //为员工选择归属门店,此处为根门店
        staffDTO.setStoreId(store.getId());
        //设置员工状态 0表示禁用，1表示启用
        staffDTO.setStaffStatus(1);
        staffDTO = iStaffService.createStaff(staffDTO);

        //为门店设置管理员
        iStoreStaffService.bindStaffToStore(store.getId(), staffDTO.getId());

        return MerchantCovert.instance.entity2dto(merchant);
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
     *  @param merchantId  商户id
     * @param merchantDTO
     * @return
     */
    @Override
    public MerchantDTO applayMerchant(Long merchantId, MerchantDTO merchantDTO) throws BusinessException {
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

        return MerchantCovert.instance.entity2dto(merchantUpdate);
    }


}
