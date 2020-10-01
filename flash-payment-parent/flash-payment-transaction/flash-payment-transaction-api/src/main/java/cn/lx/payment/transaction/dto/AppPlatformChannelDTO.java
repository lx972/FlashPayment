package cn.lx.payment.transaction.dto;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="AppPlatformChannelDTO", description="说明了应用选择了平台中的哪些支付渠道")
public class AppPlatformChannelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "应用id")
    private String appId;

    @ApiModelProperty(value = "平台支付渠道")
    private String platformChannel;


}
