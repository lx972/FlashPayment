package cn.lx.payment.transaction.service.impl;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.transaction.api.IPlatformChannelService;
import cn.lx.payment.transaction.covert.PlatformChannelCovert;
import cn.lx.payment.transaction.dto.PlatformChannelDTO;
import cn.lx.payment.transaction.entity.PlatformChannel;
import cn.lx.payment.transaction.mapper.PlatformChannelMapper;
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
 * @author lx
 * @since 2020-09-30
 */
@Slf4j
@Service
@Transactional
public class PlatformChannelServiceImpl implements IPlatformChannelService {

    @Autowired
    private PlatformChannelMapper platformChannelMapper;

    /**
     * 查询平台的支付渠道
     *
     * @return
     */
    @Override
    public List<PlatformChannelDTO> queryPlatformChannel() throws BusinessException {
        List<PlatformChannel> platformChannels = platformChannelMapper.selectList(null);
        List<PlatformChannelDTO> platformChannelDTOList = PlatformChannelCovert.INSTANCE.entityList2dtoList(platformChannels);
        return platformChannelDTOList;
    }
}
