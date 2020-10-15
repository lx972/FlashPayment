package cn.lx.payment.merchant.api;


import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.PageVO;
import cn.lx.payment.merchant.dto.StoreDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2020-09-22
 */
public interface IStoreService {

    /**
     * 商户下新增门店
     * @param storeDTO
     * @return
     * @throws BusinessException
     */
    StoreDTO createStore(StoreDTO storeDTO)throws BusinessException;

    /**
     * 根据商户id分页查询该商户下的门店
     * @param merchantId
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageVO<StoreDTO> queryStoreByPage(Long merchantId, Integer pageNo, Integer pageSize);

    /**
     * 校验门店是否属于当前商户
     * @param merchantId
     * @param storeId
     * @return
     */
    Boolean queryStoreInMerchant(Long merchantId, Long storeId);
}
