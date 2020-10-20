package cn.lx.payment.agent.rocketmq;

import cn.lx.payment.agent.dto.AliConfigParam;
import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.domain.PaymentResponseDTO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * cn.lx.payment.agent.rocketmq
 *
 * @Author Administrator
 * @date 17:16
 */
@Component
@Slf4j
public class MqSendUtil {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    /**
     * 发送延时消息，30分钟后查询订单状态，30分钟内未支付，订单关闭
     *
     * @param out_trade_no   闪聚订单号
     * @param channel        原始支付渠道
     * @param aliConfigParam 支付宝相关参数
     */
    public void sendDelayMsg(String out_trade_no, String channel, AliConfigParam aliConfigParam) {
        PaymentResponseDTO<AliConfigParam> rocketMQMsg = new PaymentResponseDTO<>();
        rocketMQMsg.setOutTradeNo(out_trade_no);
        //原始支付渠道
        rocketMQMsg.setMsg(channel);
        //支付宝相关参数
        rocketMQMsg.setContent(aliConfigParam);
        //发送异步消息
        sendAsyncMsg(rocketMQMsg,"TP_PAYMENT_ORDER", 5);

    }

    /**
     * 发送异步消息
     *
     * @param rocketMQMsg
     * @param topic
     * @param level
     */
    public void sendAsyncMsg(PaymentResponseDTO rocketMQMsg, String topic,int level) {
        Message message = new Message(topic, JSON.toJSONBytes(rocketMQMsg));
        //延时时间为30分钟
        message.setDelayTimeLevel(level);
        try {
            //发送消息
            rocketMQTemplate.getProducer().send(message, new SendCallback() {

                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("延时消息发送成功:{}", sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    log.error("延时消息发送失败:{}", e);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_300015);
        }
    }
}
