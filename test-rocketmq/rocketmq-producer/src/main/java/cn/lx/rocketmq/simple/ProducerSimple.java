package cn.lx.rocketmq.simple;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rocketmq发送消息类
 * cn.lx.rocketmq.simple
 *
 * @Author Administrator
 * @date 9:19
 */
@Component
public class ProducerSimple {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送同步消息
     *
     * @param topic
     * @param msg
     */
    public void sendSyncMsg(String topic, String msg) {
        rocketMQTemplate.syncSend(topic, msg);
    }
}
