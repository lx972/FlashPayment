package cn.lx.rocketmq.simple;

import cn.lx.rocketmq.domain.OrderExt;
import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * cn.lx.rocketmq.simple
 *
 * @Author Administrator
 * @date 9:24
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerSimpleTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送同步消息
     */
    @Test
    public void sendSyncMsg() {
        rocketMQTemplate.syncSend("my-topic", "第2条同步消息");
        System.out.println("发送成功");
    }

    /**
     * 发送异步消息
     * 消息发送完成后，不管是否发送成功与否，都直接返回，
     * 发送结果会新建一个线程通过回调函数通知
     */
    @Test
    public void sendAsyncMsg() throws InterruptedException {

        rocketMQTemplate.asyncSend("my-topic", "第1条异步消息", new SendCallback() {

            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
            }

            @Override
            public void onException(Throwable e) {
                System.out.println("发送失败");
            }
        });
        //异步消息，为跟踪回调线程这里加入延迟，
        // 如果不加，会导致主线程结束，消息直接发送失败
        Thread.sleep(3000);
    }

    /**
     * 发送单向消息
     * 消息发送完成后，不管是否发送成功与否，都直接返回，不等待broker 服务器的结果
     */
    @Test
    public void sendOneWayMsg() {

        rocketMQTemplate.sendOneWay("my-topic", "第1条单向消息");

    }

    /**
     * 发送json格式的消息
     */
    @Test
    public void sendJsonMsg() {
        OrderExt orderExt = new OrderExt(
                "12",
                new Date(),
                12l,
                "测试"
        );
        //rocketMQTemplate.syncSend("my-topic",orderExt);
        Message<OrderExt> build = MessageBuilder.withPayload(orderExt).build();
        rocketMQTemplate.syncSend("my-topic", build);

    }

    /**
     * 发送json格式的延时消息
     */
    @Test
    public void sendJsonDelayMsg() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        OrderExt orderExt = new OrderExt(
                "12567",
                new Date(),
                12l,
                "测试"
        );
        //同步
        //Message<OrderExt> build = MessageBuilder.withPayload(orderExt).build();
        //rocketMQTemplate.syncSend("my-topic",build,3000,5);
        //异步
        org.apache.rocketmq.common.message.Message message = new
                org.apache.rocketmq.common.message.Message(
                "my-topic", JSON.toJSONBytes(orderExt));
        //设置延时等级
        message.setDelayTimeLevel(2);
        rocketMQTemplate.getProducer().send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
        Thread.sleep(3000);
    }
}
