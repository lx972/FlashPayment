package cn.lx.payment.user.convert;

import cn.lx.payment.user.api.dto.tenant.AccountDTO;
import cn.lx.payment.user.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountConvert {

    AccountConvert INSTANCE = Mappers.getMapper(AccountConvert.class);

    AccountDTO entity2dto(Account entity);

    Account dto2entity(AccountDTO dto);

}
