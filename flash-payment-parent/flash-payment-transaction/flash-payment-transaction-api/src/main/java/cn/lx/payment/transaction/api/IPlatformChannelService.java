package cn.lx.payment.transaction.api;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.transaction.dto.PlatformChannelDTO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
public interface IPlatformChannelService {

    /**
     * 查询平台的支付渠道
     *
     * @return
     */
    List<PlatformChannelDTO> queryPlatformChannel() throws BusinessException;
}
