package cn.lx.payment.merchant.api;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.merchant.dto.AppDTO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author author
 * @since 2020-09-22
 */
public interface IAppService {

    /**
     * 创建应用
     *
     * @param merchantId 商户id
     * @param appDTO
     * @return
     */
    AppDTO createApp(Long merchantId, AppDTO appDTO) throws BusinessException;


    /**
     * 查询当前商户的应用
     *
     * @param merchantId
     * @return
     */
    List<AppDTO> queryApps(Long merchantId) throws BusinessException;

    /**
     * 根据应用id查询应用的详细信息
     *
     * @param appId
     * @return
     */
    AppDTO queryApp(String appId) throws BusinessException;


    /**
     * 校验应用是否属于当前商户
     * @param merchantId
     * @param appId
     * @return
     */
    Boolean queryAppInMerchant(Long merchantId, String appId);
}
