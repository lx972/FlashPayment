package cn.lx.payment.merchant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "AppCreateVO", description = "创建应用的相关参数")
public class AppCreateVO implements Serializable {


    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称",dataType = "String")
    private String appName;

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
