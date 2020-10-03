package cn.lx.payment.merchant.api;


import cn.lx.payment.domain.BusinessException;
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

}
