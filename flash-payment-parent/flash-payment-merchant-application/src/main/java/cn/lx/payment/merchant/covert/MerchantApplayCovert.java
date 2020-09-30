package cn.lx.payment.merchant.covert;

import cn.lx.payment.merchant.dto.MerchantDTO;
import cn.lx.payment.merchant.vo.MerchantApplayVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * cn.lx.payment.merchant.covert
 *
 * @Author Administrator
 * @date 18:10
 */
@Mapper
public interface MerchantApplayCovert {

    MerchantApplayCovert INSTANCE= Mappers.getMapper(MerchantApplayCovert.class);

    MerchantApplayVO dto2vo(MerchantDTO merchantDTO);

    MerchantDTO vo2dto(MerchantApplayVO merchantApplayVO);
}
