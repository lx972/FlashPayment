package cn.lx.payment.transaction.service.impl;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.transaction.api.IPayChannelService;
import cn.lx.payment.transaction.covert.PayChannelCovert;
import cn.lx.payment.transaction.dto.PayChannelDTO;
import cn.lx.payment.transaction.entity.PayChannel;
import cn.lx.payment.transaction.mapper.PayChannelMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
@Slf4j
@Service
@Transactional
public class PayChannelServiceImpl implements IPayChannelService {

    @Autowired
    private PayChannelMapper payChannelMapper;

    /**
     * 根据平台服务类型获取支付渠道列表
     *
     * @param platformChannelCode 平台服务类型code
     * @return
     */
    @Override
    public List<PayChannelDTO> queryPayChannelByPlatformChannel(String platformChannelCode) throws BusinessException {
        List<PayChannel> payChannels = payChannelMapper.queryPayChannelByPlatformChannel(platformChannelCode);
        List<PayChannelDTO> payChannelDTOList = PayChannelCovert.INSTANCE.entityList2dtoList(payChannels);
        return payChannelDTOList;
    }
}
