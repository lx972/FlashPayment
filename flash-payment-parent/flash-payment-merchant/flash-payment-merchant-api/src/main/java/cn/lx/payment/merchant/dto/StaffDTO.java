package cn.lx.payment.merchant.dto;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="StaffDTO", description="员工信息")
public class StaffDTO implements Serializable {


    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商户ID")
    private Long merchantId;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "用户名(关联统一用户)")
    private String username;

    @ApiModelProperty(value = "手机号(关联统一用户)")
    private String mobile;

    @ApiModelProperty(value = "员工所属门店")
    private Long storeId;

    @ApiModelProperty(value = "最后一次登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "0表示禁用，1表示启用")
    private Boolean staffStatus;


}
