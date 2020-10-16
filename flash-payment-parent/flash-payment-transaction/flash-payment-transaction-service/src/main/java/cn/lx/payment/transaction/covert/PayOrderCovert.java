package cn.lx.payment.transaction.covert;

import cn.lx.payment.transaction.dto.PayOrderDTO;
import cn.lx.payment.transaction.entity.PayOrder;
import cn.lx.payment.transaction.vo.OrderConfirmVO;
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
public interface PayOrderCovert {

    PayOrderCovert INSTANCE= Mappers.getMapper(PayOrderCovert.class);

    PayOrder dto2entity(PayOrderDTO payOrderDTO);

    PayOrderDTO entity2dto(PayOrder payOrder);

    PayOrderDTO vo2dto(OrderConfirmVO orderConfirmVO);

    List<PayOrderDTO> entityList2dtoList(List<PayOrder> payOrders);
}
