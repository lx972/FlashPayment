package cn.lx.payment.merchant.service.impl;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.PageVO;
import cn.lx.payment.merchant.api.IStoreService;
import cn.lx.payment.merchant.covert.StoreCovert;
import cn.lx.payment.merchant.dto.StoreDTO;
import cn.lx.payment.merchant.entity.App;
import cn.lx.payment.merchant.entity.Store;
import cn.lx.payment.merchant.mapper.StoreMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据商户id分页查询该商户下的门店
     *
     * @param merchantId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageVO<StoreDTO> queryStoreByPage(Long merchantId, Integer pageNo, Integer pageSize) {
        Page<Store> page = new Page<>(pageNo, pageSize);
        IPage<Store> selectPage = storeMapper.selectPage(page, new LambdaQueryWrapper<Store>().eq(Store::getMerchantId, merchantId));
        List<StoreDTO> storeDTOS = StoreCovert.instance.entityList2dtoList(selectPage.getRecords());
        return new PageVO<StoreDTO>(storeDTOS,
                selectPage.getTotal(),
                pageNo,
                pageSize);
    }

    /**
     * 校验门店是否属于当前商户
     *
     * @param merchantId
     * @param storeId
     * @return
     */
    @Override
    public Boolean queryStoreInMerchant(Long merchantId, Long storeId) {
        Integer count = storeMapper.selectCount(new LambdaQueryWrapper<Store>().eq(Store::getMerchantId, merchantId).eq(Store::getId, storeId));
        if (count>0){
            return true;
        }
        return false;
    }
}
