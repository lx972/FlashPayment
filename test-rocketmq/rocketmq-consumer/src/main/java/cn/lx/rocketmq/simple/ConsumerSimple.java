package cn.lx.rocketmq.simple;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * topic 监听的主题
 * consumerGroup 消费组，相同消费组的消费者共同消费该主题的消息，它们组成一个集群
 * demo-producer-group 是我们在生产者的配置文件中设置的
 */
@Component
@RocketMQMessageListener(topic = "my-topic", consumerGroup = "demo-producer-group")
public class ConsumerSimple implements RocketMQListener<MessageExt> {


    /**
     * 消息接收的方法
     * @param message
     */
    @Override
    public void onMessage(MessageExt message) {
        int reconsumeTimes = message.getReconsumeTimes();
        if (reconsumeTimes>2){
            //将消息取出保存到数据库的操作
            try {
                System.out.println(new String(message.getBody(),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return;
        }
        throw new RuntimeException("模拟消费失败");
    }
}
