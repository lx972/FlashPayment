package cn.lx.payment.user.convert;

import cn.lx.payment.user.api.dto.tenant.BundleDTO;
import cn.lx.payment.user.entity.Bundle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BundleConvert {

    BundleConvert INSTANCE = Mappers.getMapper(BundleConvert.class);

    BundleDTO entity2dto(Bundle entity);

    Bundle dto2entity(BundleDTO dto);

    List<BundleDTO> entitylist2dto(List<Bundle> bundle);
}
