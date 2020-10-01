package cn.lx.payment.merchant.covert;

import cn.lx.payment.merchant.dto.AppDTO;
import cn.lx.payment.merchant.vo.AppCreateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * cn.lx.payment.merchant.covert
 *
 * @Author Administrator
 * @date 11:20
 */
@Mapper
public interface AppCreateCovert {

    AppCreateCovert INSTANCE= Mappers.getMapper(AppCreateCovert.class);

    AppDTO vo2dto(AppCreateVO appCreateVO);

    AppCreateVO dto2vo(AppDTO appDTO);
}
