package cn.lx.payment.transaction.api;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.transaction.dto.PayChannelParamDTO;

import java.util.List;

/**
 * <p>
 * 某商户针对某一种原始支付渠道的配置参数 服务类
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
public interface IPayChannelParamService {

    /**
     * 更新和添加登录商户的支付参数
     *
     * @param merchantId         商户id
     * @param payChannelParamDTO 支付参数
     */
    void savePayChannelParam(Long merchantId, PayChannelParamDTO payChannelParamDTO) throws BusinessException;

    /**
     * 获取指定应用指定服务类型下所包含的原始支付渠道参数列表
     * @param appId 应用id
     * @param platformChannel   平台服务类型编码
     * @return
     */
    List<PayChannelParamDTO> queryPayChannelParams(String appId, String platformChannel);


    /**
     * 获取指定应用指定服务类型下指定原始支付方式所包含的原始支付渠道参数
     * @param appId 应用id
     * @param platformChannel   平台服务类型编码
     * @param payChannel    原始支付渠道编码
     * @return
     */
    PayChannelParamDTO queryPayChannelParam(String appId, String platformChannel, String payChannel);
}
