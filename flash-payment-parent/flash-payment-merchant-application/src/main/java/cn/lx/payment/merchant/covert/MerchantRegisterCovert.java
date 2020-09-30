package cn.lx.payment.merchant.covert;

import cn.lx.payment.merchant.dto.MerchantDTO;
import cn.lx.payment.merchant.vo.MerchantRegisterVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * cn.lx.payment.merchant.covert
 *
 * @Author Administrator
 * @date 18:02
 */
@Mapper
public interface MerchantRegisterCovert {

    MerchantRegisterCovert instance= Mappers.getMapper(MerchantRegisterCovert.class);

    MerchantRegisterVO dto2vo(MerchantDTO merchantDTO);

    MerchantDTO vo2dto(MerchantRegisterVO merchantRegisterVO);

}
