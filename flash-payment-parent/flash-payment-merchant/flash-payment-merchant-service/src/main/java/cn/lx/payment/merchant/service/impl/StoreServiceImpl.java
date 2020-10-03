package cn.lx.payment.merchant.service.impl;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.merchant.api.IStoreService;
import cn.lx.payment.merchant.covert.StoreCovert;
import cn.lx.payment.merchant.dto.StoreDTO;
import cn.lx.payment.merchant.entity.Store;
import cn.lx.payment.merchant.mapper.StoreMapper;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2020-09-22
 */
@Slf4j
@Service
@Transactional
public class StoreServiceImpl implements IStoreService {

    @Autowired
    private StoreMapper storeMapper;

    /**
     * 商户下新增门店
     *
     * @param storeDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public StoreDTO createStore(StoreDTO storeDTO) throws BusinessException {
        Store store = StoreCovert.instance.dto2entity(storeDTO);
        storeMapper.insert(store);
        log.info("商户下新增门店"+ JSON.toJSONString(store));
        return StoreCovert.instance.entity2dto(store);
    }
}
