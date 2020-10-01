package cn.lx.payment.transaction.api;


import cn.lx.payment.domain.BusinessException;

/**
 * <p>
 * 说明了应用选择了平台中的哪些支付渠道 服务类
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
public interface IAppPlatformChannelService {

    /**
     * 为应用绑定支付服务
     *
     * @param appId                应用id
     * @param platformChannelCodes 服务类型code
     */
    void bindPlatformChannelForApp(String appId, String platformChannelCodes) throws BusinessException;

    /**
     * 查询应用是否已经绑定某个支付服务
     *  @param appId                应用id
     * @param platformChannelCodes 服务类型code
     * @return 1为已经绑定 0为未绑定
     */
    int queryBindPlatformForApp(String appId, String platformChannelCodes) throws BusinessException;
}
