package cn.lx.payment.merchant.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "AppDTO", description = "应用信息")
public class AppDTO implements Serializable {


    @ApiModelProperty(value = "主键id",dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "应用id",dataType = "String")
    private String appId;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称",dataType = "String")
    private String appName;

    /**
     * 所属商户
     */
    @ApiModelProperty(value = "所属商户的id",dataType = "Long")
    private Long merchantId;

    /**
     * 应用公钥(RSAWithSHA256)
     */
    @ApiModelProperty(value = "应用公钥",dataType = "String")
    private String publicKey;

    /**
     * 授权回调地址
     */
    @ApiModelProperty(value = "授权回调地址",dataType = "String")
    private String notifyUrl;


}
