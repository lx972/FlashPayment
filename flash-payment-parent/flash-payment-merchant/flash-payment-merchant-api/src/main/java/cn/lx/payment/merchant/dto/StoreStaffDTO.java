package cn.lx.payment.merchant.dto;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="StoreStaffDTO", description="门店员工关联")
public class StoreStaffDTO implements Serializable {


    private Long id;

    @ApiModelProperty(value = "门店标识")
    private Long storeId;

    @ApiModelProperty(value = "员工标识")
    private Long staffId;


}
