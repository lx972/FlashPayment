package cn.lx.payment.transaction.mapper;

import cn.lx.payment.transaction.entity.AppPlatformChannel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 说明了应用选择了平台中的哪些支付渠道 Mapper 接口
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
@Repository
public interface AppPlatformChannelMapper extends BaseMapper<AppPlatformChannel> {

}
