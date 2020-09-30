package cn.lx.payment.merchant.covert;

import cn.lx.payment.merchant.dto.MerchantDTO;
import cn.lx.payment.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * cn.lx.payment.merchant.covert
 *
 * @Author Administrator
 * @date 18:07
 */
@Mapper
public interface MerchantCovert {

    MerchantCovert instance= Mappers.getMapper(MerchantCovert.class);

    Merchant dto2entity(MerchantDTO merchantDTO);

    MerchantDTO entity2dto(Merchant merchant);
}
