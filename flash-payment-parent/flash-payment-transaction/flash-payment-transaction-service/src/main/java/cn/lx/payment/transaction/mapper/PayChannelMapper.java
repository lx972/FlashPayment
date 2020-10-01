package cn.lx.payment.transaction.mapper;

import cn.lx.payment.transaction.entity.PayChannel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
@Repository
public interface PayChannelMapper extends BaseMapper<PayChannel> {

    /**
     * 根据平台服务类型获取支付渠道列表
     *
     * @param platformChannelCode
     * @return
     */
    List<PayChannel> queryPayChannelByPlatformChannel(@Param("platformChannelCode") String platformChannelCode);
}
