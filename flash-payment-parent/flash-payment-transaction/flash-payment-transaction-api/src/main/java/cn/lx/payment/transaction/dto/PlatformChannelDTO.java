package cn.lx.payment.transaction.dto;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="PlatformChannelDTO", description="")
public class PlatformChannelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "平台支付渠道名称")
    private String channelName;

    @ApiModelProperty(value = "平台支付渠道编码")
    private String channelCode;


}
