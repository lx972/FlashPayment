package cn.lx.payment.transaction.rocketmq;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.domain.PaymentResponseDTO;
import cn.lx.payment.domain.TradeStatus;
import cn.lx.payment.transaction.api.IPayOrderService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * cn.lx.payment.agent.rocketmq
 *
 * @Author Administrator
 * @date 16:36
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "TP_PAYMENT_RESULT", consumerGroup = "CID_ORDER_CONSUMER")
public class PayResultConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    private IPayOrderService iPayOrderService;


    /**
     * 接收支付结果
     *
     * @param message
     */
    @Override
    public void onMessage(MessageExt message) {
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        PaymentResponseDTO paymentResponseDTO = JSON.parseObject(body, PaymentResponseDTO.class);
        if (TradeStatus.SUCCESS.equals(paymentResponseDTO.getTradeState())) {
            //支付成功，去修改数据库
            iPayOrderService.updateTradeStateByNo(
                    paymentResponseDTO.getOutTradeNo(),
                    paymentResponseDTO.getTradeNo(),
                    "2"
            );
        } else if (TradeStatus.FAILED.equals(paymentResponseDTO.getTradeState())) {
            //支付失败，去修改数据库
            iPayOrderService.updateTradeStateByNo(
                    paymentResponseDTO.getOutTradeNo(),
                    paymentResponseDTO.getTradeNo(),
                    "5"
            );
        } else if (TradeStatus.REVOKED.equals(paymentResponseDTO.getTradeState())) {
            //支付撤销，去修改数据库
            iPayOrderService.updateTradeStateByNo(
                    paymentResponseDTO.getOutTradeNo(),
                    paymentResponseDTO.getTradeNo(),
                    "4"
            );
        } else {
            throw new BusinessException(CommonErrorCode.E_300016);
        }


    }
}
