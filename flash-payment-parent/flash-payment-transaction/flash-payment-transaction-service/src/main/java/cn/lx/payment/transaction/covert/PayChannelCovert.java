package cn.lx.payment.transaction.covert;

import cn.lx.payment.transaction.dto.PayChannelDTO;
import cn.lx.payment.transaction.entity.PayChannel;
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
public interface PayChannelCovert {

    PayChannelCovert INSTANCE= Mappers.getMapper(PayChannelCovert.class);

    PayChannel dto2entity(PayChannelDTO payChannelDTO);

    PayChannelDTO entity2dto(PayChannel payChannel);

    List<PayChannelDTO> entityList2dtoList(List<PayChannel> payChannels);
}
