package cn.lx.payment.merchant.covert;

import cn.lx.payment.merchant.dto.AppDTO;
import cn.lx.payment.merchant.entity.App;
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
public interface AppCovert {

    AppCovert instance= Mappers.getMapper(AppCovert.class);

    App dto2entity(AppDTO appDTO);

    AppDTO entity2dto(App app);

    List<AppDTO> entityList2dtoList(List<App> apps);
}
