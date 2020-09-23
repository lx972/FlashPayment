package cn.lx.payment.merchant.service.impl;

import cn.lx.payment.merchant.api.IMerchantService;
import cn.lx.payment.merchant.dto.MerchantDTO;
import cn.lx.payment.merchant.entity.Merchant;
import cn.lx.payment.merchant.mapper.MerchantMapper;
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
public class MerchantServiceImpl  implements IMerchantService {

    @Autowired
    private MerchantMapper merchantMapper;


    /**
     * 根据id查询企业所有人信息
     *
     * @param id
     * @return
     */
    @Override
    public MerchantDTO queryMerchantById(String id) {
        Merchant merchant = merchantMapper.selectById(id);

        return merchant;
    }
}
