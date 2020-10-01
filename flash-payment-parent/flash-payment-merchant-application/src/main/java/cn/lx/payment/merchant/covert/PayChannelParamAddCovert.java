package cn.lx.payment.merchant.covert;

import cn.lx.payment.merchant.vo.PayChannelParamAddVO;
import cn.lx.payment.transaction.dto.PayChannelParamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * cn.lx.payment.merchant.covert
 *
 * @Author Administrator
 * @date 18:02
 */
@Mapper
public interface PayChannelParamAddCovert {

    PayChannelParamAddCovert instance= Mappers.getMapper(PayChannelParamAddCovert.class);

    PayChannelParamAddVO dto2vo(PayChannelParamDTO payChannelParamDTO);

    PayChannelParamDTO vo2dto(PayChannelParamAddVO payChannelParamAddVO);

}
