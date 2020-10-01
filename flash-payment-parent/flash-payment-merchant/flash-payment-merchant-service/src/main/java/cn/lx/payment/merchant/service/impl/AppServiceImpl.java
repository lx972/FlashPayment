package cn.lx.payment.merchant.service.impl;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.merchant.api.IAppService;
import cn.lx.payment.merchant.covert.AppCovert;
import cn.lx.payment.merchant.dto.AppDTO;
import cn.lx.payment.merchant.entity.App;
import cn.lx.payment.merchant.entity.Merchant;
import cn.lx.payment.merchant.mapper.AppMapper;
import cn.lx.payment.merchant.mapper.MerchantMapper;
import cn.lx.payment.util.RandomUuidUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
public class AppServiceImpl implements IAppService {

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    /**
     * 创建应用
     *
     * @param merchantId 商户id
     * @param appDTO
     * @return
     */
    @Override
    public AppDTO createApp(Long merchantId, AppDTO appDTO) throws BusinessException {
        //校验商户是否已经通过资质审核
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant == null) {
            throw new BusinessException(CommonErrorCode.E_200002);
        }
        if (!"2".equals(merchant.getAuditStatus())) {
            throw new BusinessException(CommonErrorCode.E_200003);
        }
        //校验应用名是否已经被使用
        Integer count = appMapper.selectCount(new LambdaQueryWrapper<App>().eq(App::getAppName, appDTO.getAppName()));
        if (count > 0) {
            throw new BusinessException(CommonErrorCode.E_200004);
        }
        App app = AppCovert.instance.dto2entity(appDTO);
        app.setAppId(RandomUuidUtil.getUUID());
        app.setMerchantId(merchantId);
        appMapper.insert(app);
        return AppCovert.instance.entity2dto(app);
    }

    /**
     * 查询当前商户的应用
     *
     * @param merchantId
     * @return
     */
    @Override
    public List<AppDTO> queryApps(Long merchantId) throws BusinessException {
        List<App> apps = appMapper.selectList(new LambdaQueryWrapper<App>().eq(App::getMerchantId, merchantId));
        List<AppDTO> appDTOList = AppCovert.instance.entityList2dtoList(apps);
        return appDTOList;
    }

    /**
     * 根据应用id查询应用的详细信息
     *
     * @param appId
     * @return
     */
    @Override
    public AppDTO queryApp(String appId) throws BusinessException {
        App app = appMapper.selectOne(new LambdaQueryWrapper<App>().eq(App::getAppId, appId));
        AppDTO appDTO = AppCovert.instance.entity2dto(app);
        return appDTO;
    }
}
