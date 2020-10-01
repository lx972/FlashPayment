package cn.lx.payment.merchant.covert;

import cn.lx.payment.merchant.vo.PayChannelParamUpdateVO;
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
public interface PayChannelParamUpdateCovert {

    PayChannelParamUpdateCovert instance= Mappers.getMapper(PayChannelParamUpdateCovert.class);

    PayChannelParamUpdateVO dto2vo(PayChannelParamDTO payChannelParamDTO);

    PayChannelParamDTO vo2dto(PayChannelParamUpdateVO payChannelParamUpdateVO);

}
