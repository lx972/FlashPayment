package cn.lx.payment.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("platform_pay_channel")
public class PlatformPayChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 平台支付渠道编码
     */
    @TableField("PLATFORM_CHANNEL")
    private String platformChannel;

    /**
     * 原始支付渠道名称
     */
    @TableField("PAY_CHANNEL")
    private String payChannel;


}
