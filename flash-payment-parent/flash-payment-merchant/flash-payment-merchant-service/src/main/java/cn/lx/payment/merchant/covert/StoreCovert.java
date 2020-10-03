package cn.lx.payment.merchant.covert;

import cn.lx.payment.merchant.dto.StoreDTO;
import cn.lx.payment.merchant.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * cn.lx.payment.merchant.covert
 *
 * @Author Administrator
 * @date 18:07
 */
@Mapper
public interface StoreCovert {

    StoreCovert instance= Mappers.getMapper(StoreCovert.class);

    Store dto2entity(StoreDTO storeDTO);

    StoreDTO entity2dto(Store store);

    List<StoreDTO> entityList2dtoList(List<Store> stores);
}
