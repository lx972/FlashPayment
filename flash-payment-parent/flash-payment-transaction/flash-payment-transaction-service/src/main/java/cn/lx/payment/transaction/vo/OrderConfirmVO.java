package cn.lx.payment.transaction.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * cn.lx.payment.transaction.vo
 *
 * @Author Administrator
 * @date 14:14
 */

@Data
@ApiModel(value = "OrderConfirmVO",description = "确认支付所需信息")
public class OrderConfirmVO {

    @ApiModelProperty("应用id")
    private String appId;

    @ApiModelProperty("交易单号")
    private String tradeNo;

    @ApiModelProperty("微信openid")
    private String openId;

    @ApiModelProperty("门店id")
    private String storeId;

    @ApiModelProperty("服务类型")
    private String channel;

    @ApiModelProperty("订单描述")
    private String body;

    @ApiModelProperty("订单标题")
    private String subject;

    @ApiModelProperty("金额")
    private String totalAmount;
}
