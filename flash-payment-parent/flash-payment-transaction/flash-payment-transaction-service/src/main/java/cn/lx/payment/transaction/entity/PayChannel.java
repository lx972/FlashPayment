package cn.lx.payment.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("pay_channel")
public class PayChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 原始支付渠道名称
     */
    @TableField("CHANNEL_NAME")
    private String channelName;

    /**
     * 原始支付渠道编码
     */
    @TableField("CHANNEL_CODE")
    private String channelCode;


}
