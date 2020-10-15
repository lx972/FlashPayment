package cn.lx.payment.transaction.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * cn.lx.payment.transaction.dto
 *
 * @Author Administrator
 * @date 17:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "QRCodeDTO",description = "生成二维码相关参数")
public class QRCodeDTO implements Serializable {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "应用id")
    private String appId;

    @ApiModelProperty(value = "门店id")
    private Long storeId;

    @ApiModelProperty(value = "商品标题")
    private String subject;//商品标题

    @ApiModelProperty(value = "订单描述")
    private String body;//订单描述
}
