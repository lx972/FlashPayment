package cn.lx.payment.transaction.mapper;

import cn.lx.payment.transaction.dto.PayChannelParamDTO;
import cn.lx.payment.transaction.entity.PayChannelParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 某商户针对某一种原始支付渠道的配置参数 Mapper 接口
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
@Repository
public interface PayChannelParamMapper extends BaseMapper<PayChannelParam> {

    /**
     * 获取指定应用指定服务类型下所包含的原始支付渠道参数列表
     *
     * @param appId           应用id
     * @param platformChannel 平台服务类型编码
     * @return
     */
    List<PayChannelParam> queryPayChannelParams(@Param("appId") String appId
            , @Param("platformChannel") String platformChannel);

    /**
     * 获取指定应用指定服务类型下指定原始支付方式所包含的原始支付渠道参数
     *
     * @param appId           应用id
     * @param platformChannel 平台服务类型编码
     * @param payChannel      原始支付渠道编码
     * @return
     */
    PayChannelParam queryPayChannelParam(@Param("appId") String appId
            , @Param("platformChannel") String platformChannel
            , @Param("payChannel") String payChannel);


    /**
     * 根据订单号查询出支付者的阿里支付参数
     *
     * @param out_trade_no 支付时传入的商户订单号
     * @param trade_no     支付时返回的支付宝交易号
     * @return
     */
    PayChannelParam queryPayChannelParamByNo(@Param("out_trade_no") String out_trade_no, @Param("trade_no") String trade_no);
}
