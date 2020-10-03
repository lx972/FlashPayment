package cn.lx.payment.merchant.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "MerchantDTO", description = "企业信息")
public class MerchantDTO implements Serializable {


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id",dataType = "Long")
    private Long id;

    /**
     * 商户名称
     */
    @ApiModelProperty(value = "商户名称",dataType = "String")
    private String merchantName;

    /**
     * 企业编号
     */
    @ApiModelProperty(value = "企业编号",dataType = "String")
    private String merchantNo;

    /**
     * 企业地址
     */
    @ApiModelProperty(value = "企业地址",dataType = "String")
    private String merchantAddress;

    /**
     * 商户类型
     */
    @ApiModelProperty(value = "商户类型",dataType = "String")
    private String merchantType;

    /**
     * 营业执照（企业证明）
     */
    @ApiModelProperty(value = "营业执照",dataType = "String")
    private String businessLicensesImg;

    /**
     * 法人身份证正面照片
     */
    @ApiModelProperty(value = "法人身份证正面照片",dataType = "String")
    private String idCardFrontImg;

    /**
     * 法人身份证反面照片
     */
    @ApiModelProperty(value = "法人身份证反面照片",dataType = "String")
    private String idCardAfterImg;

    /**
     * 联系人姓名
     */
    @ApiModelProperty(value = "联系人姓名",dataType = "String")
    private String username;

    /**
     * 联系人手机号(关联统一账号)
     */
    @ApiModelProperty(value = "联系人手机号",dataType = "String")
    private String mobile;

    /**
     * 联系人地址
     */
    @ApiModelProperty(value = "联系人地址",dataType = "String")
    private String contactsAddress;

    /**
     * 审核状态 0-未申请,1-已申请待审核,2-审核通过,3-审核拒绝
     */
    @ApiModelProperty(value = "审核状态",dataType = "String")
    private String auditStatus;

    /**
     * 租户ID,关联统一用户
     */
    @ApiModelProperty(value = "租户ID",dataType = "Long")
    private Long tenantId;


    @ApiModelProperty("管理员登录密码")
    private String password;


}
