package cn.lx.payment.merchant.covert;

import cn.lx.payment.merchant.dto.StaffDTO;
import cn.lx.payment.merchant.entity.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * cn.lx.payment.merchant.covert
 *
 * @Author Administrator
 * @date 18:07
 */
@Mapper
public interface StaffCovert {

    StaffCovert instance= Mappers.getMapper(StaffCovert.class);

    Staff dto2entity(StaffDTO staffDTO);

    StaffDTO entity2dto(Staff staff);

    List<StaffDTO> entityList2dtoList(List<Staff> staffs);
}
