package cn.lx.payment.transaction.covert;

import cn.lx.payment.transaction.dto.PayChannelParamDTO;
import cn.lx.payment.transaction.entity.PayChannelParam;
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
public interface PayChannelParamCovert {

    PayChannelParamCovert INSTANCE= Mappers.getMapper(PayChannelParamCovert.class);

    PayChannelParam dto2entity(PayChannelParamDTO payChannelParamDTO);

    PayChannelParamDTO entity2dto(PayChannelParam payChannelParam);

    List<PayChannelParamDTO> entityList2dtoList(List<PayChannelParam> payChannelParamList);
}
