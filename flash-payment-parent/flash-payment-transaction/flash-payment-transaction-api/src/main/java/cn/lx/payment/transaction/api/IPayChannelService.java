package cn.lx.payment.transaction.api;


import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.transaction.dto.PayChannelDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
public interface IPayChannelService {

    /**
     * 根据平台服务类型获取支付渠道列表
     * @param platformChannelCode   平台服务类型code
     * @return
     */
    List<PayChannelDTO> queryPayChannelByPlatformChannel(String platformChannelCode)throws BusinessException;
}
