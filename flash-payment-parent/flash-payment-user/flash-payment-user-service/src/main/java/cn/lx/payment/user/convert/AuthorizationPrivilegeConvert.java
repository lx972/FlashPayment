package cn.lx.payment.user.convert;

import cn.lx.payment.user.api.dto.authorization.PrivilegeDTO;
import cn.lx.payment.user.entity.AuthorizationPrivilege;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthorizationPrivilegeConvert {
    //获取接口的一个实例，实例是系统自动生成的
    AuthorizationPrivilegeConvert INSTANCE = Mappers.getMapper(AuthorizationPrivilegeConvert.class);

    //方法名自定义，它是根据参数类型和返回类型生成转化的具体实现
    PrivilegeDTO entity2dto(AuthorizationPrivilege entity);

    AuthorizationPrivilege dto2entity(PrivilegeDTO dto);

    //list集合也可以相互转化
    List<PrivilegeDTO> entitylist2dto(List<AuthorizationPrivilege>  authorizationRole);
}
