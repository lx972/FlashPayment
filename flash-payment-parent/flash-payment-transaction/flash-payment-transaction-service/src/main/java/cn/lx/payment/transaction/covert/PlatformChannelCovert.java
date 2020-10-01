package cn.lx.payment.transaction.covert;

import cn.lx.payment.transaction.dto.PlatformChannelDTO;
import cn.lx.payment.transaction.entity.PlatformChannel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * cn.lx.payment.merchant.covert
 *
 * @Author Administrator
 * @date 18:10
 */
@Mapper
public interface PlatformChannelCovert {

    PlatformChannelCovert INSTANCE= Mappers.getMapper(PlatformChannelCovert.class);

    PlatformChannel dto2entity(PlatformChannelDTO platformChannelDTO);

    PlatformChannelDTO entity2dto(PlatformChannel platformChannel);

    List<PlatformChannelDTO> entityList2dtoList(List<PlatformChannel> platformChannelList);
}
