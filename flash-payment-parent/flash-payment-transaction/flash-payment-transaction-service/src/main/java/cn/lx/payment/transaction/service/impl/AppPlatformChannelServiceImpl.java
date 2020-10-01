package cn.lx.payment.transaction.service.impl;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.transaction.api.IAppPlatformChannelService;
import cn.lx.payment.transaction.entity.AppPlatformChannel;
import cn.lx.payment.transaction.mapper.AppPlatformChannelMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 说明了应用选择了平台中的哪些支付渠道 服务实现类
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
@Slf4j
@Service
@Transactional
public class AppPlatformChannelServiceImpl implements IAppPlatformChannelService {

    @Autowired
    private AppPlatformChannelMapper appPlatformChannelMapper;

    /**
     * 为应用绑定支付服务
     *
     * @param appId                应用id
     * @param platformChannelCodes 服务类型code
     */
    @Override
    public void bindPlatformChannelForApp(String appId, String platformChannelCodes) throws BusinessException {
        //判断是是否已经绑定
        Integer count = appPlatformChannelMapper.selectCount(new LambdaQueryWrapper<AppPlatformChannel>()
                .eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannelCodes));
        if (count > 0) {
            throw new BusinessException(CommonErrorCode.E_300011);
        }
        AppPlatformChannel appPlatformChannel = new AppPlatformChannel();
        appPlatformChannel.setAppId(appId);
        appPlatformChannel.setPlatformChannel(platformChannelCodes);
        appPlatformChannelMapper.insert(appPlatformChannel);
    }

    /**
     * 查询应用是否已经绑定某个支付服务
     *
     * @param appId                应用id
     * @param platformChannelCodes 服务类型code
     * @return 1为已经绑定 0为未绑定
     */
    @Override
    public int queryBindPlatformForApp(String appId, String platformChannelCodes) throws BusinessException {
        //判断是是否已经绑定
        Integer count = appPlatformChannelMapper.selectCount(new LambdaQueryWrapper<AppPlatformChannel>()
                .eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannelCodes));
        if (count > 0) {
            return 1;
        }
        return 0;
    }


}
