package cn.lx.payment.transaction.mapper;

import cn.lx.payment.transaction.entity.PayChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * cn.lx.payment.transaction.mapper
 *
 * @Author Administrator
 * @date 9:56
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PayChannelMapperTest {

    @Autowired
    private PayChannelMapper payChannelMapper;

    @Test
    public void queryPayChannelByPlatformChannel() {
        List<PayChannel> payChannels = payChannelMapper.queryPayChannelByPlatformChannel("shanju_c2b");
        System.out.println(payChannels);
    }
}
