package cn.lx.payment.merchant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="PayChannelParamAddVO", description="新增一种原始支付渠道的配置参数")
public class PayChannelParamAddVO implements Serializable {


    @ApiModelProperty("应用的appId,是业务id")
    private String appId;

    @ApiModelProperty("应用绑定的服务类型对应的code")
    private String platformChannelCode;

    @ApiModelProperty("参数配置名称")
    private String channelName;

    @ApiModelProperty("原始支付渠道编码")
    private String payChannel;

    @ApiModelProperty("原始支付渠道参数配置内容，json格式")
    private String param;



}
