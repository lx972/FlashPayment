package cn.lx.payment.merchant.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "StoreDTO", description = "门店信息")
public class StoreDTO implements Serializable {

    @ApiModelProperty(value = "主键id",dataType = "Long")
    private Long id;

    /**
     * 门店名称
     */
    @ApiModelProperty(value = "门店名称",dataType = "String")
    private String storeName;

    /**
     * 门店编号
     */
    @ApiModelProperty(value = "门店编号",dataType = "Long")
    private Long storeNumber;

    /**
     * 所属商户
     */
    @ApiModelProperty(value = "所属商户",dataType = "Long")
    private Long merchantId;

    /**
     * 父门店
     */
    @ApiModelProperty(value = "父门店id",dataType = "Long")
    private Long parentId;

    /**
     * 0表示禁用，1表示启用
     */
    @ApiModelProperty(value = "是否启用",dataType = "Boolean")
    private Boolean storeStatus;

    /**
     * 门店地址
     */
    @ApiModelProperty(value = "门店地址",dataType = "String")
    private String storeAddress;


}
