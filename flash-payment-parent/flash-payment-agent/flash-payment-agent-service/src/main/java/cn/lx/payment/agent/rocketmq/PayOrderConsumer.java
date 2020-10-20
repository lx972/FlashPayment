package cn.lx.payment.agent.rocketmq;

import cn.lx.payment.agent.api.IQueryPayAgentService;
import cn.lx.payment.agent.dto.AliConfigParam;
import cn.lx.payment.domain.PaymentResponseDTO;
import cn.lx.payment.domain.TradeStatus;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
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
@RocketMQMessageListener(topic = "TP_PAYMENT_ORDER",consumerGroup = "CID_PAYMENT_CONSUMER")
public class PayOrderConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    private IQueryPayAgentService iQueryPayAgentService;

    @Autowired
    private MqSendUtil mqSendUtil;


    /**
     * 接收延时消息
     * @param message
     */
    @Override
    public void onMessage(MessageExt message) {
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        //不能直接使用泛型接收，这样是无法转化的，当你取出泛型的类型时，会报json转化异常
        PaymentResponseDTO paymentResponseDTO = JSON.parseObject(body, PaymentResponseDTO.class);
        String content = String.valueOf(paymentResponseDTO.getContent());
        AliConfigParam aliConfigParam = JSON.parseObject(content, AliConfigParam.class);
        PaymentResponseDTO responseDTO=null;
        if ("ALIPAY_WAP".equals(paymentResponseDTO.getMsg())){
            //查询支付宝订单状态
            responseDTO = iQueryPayAgentService.queryAliOrder(paymentResponseDTO.getOutTradeNo(),
                    null,
                    aliConfigParam);
        }else {
            //其他的支付方式
        }

        //返回查询获得的支付状态
        if (TradeStatus.UNKNOWN.equals(responseDTO.getTradeState())
                || TradeStatus.USERPAYING .equals(responseDTO.getTradeState())) {
            //在支付状态未知或支付中，抛出异常会重新消息此消息
            log.info("支付代理‐‐‐支付状态未知，等待重试");
            //TODO:去关闭订单
        }

        //向mq发送消息，交易服务监听，修改数据库订单状态
        mqSendUtil.sendAsyncMsg(responseDTO,"TP_PAYMENT_RESULT",1);

    }
}
