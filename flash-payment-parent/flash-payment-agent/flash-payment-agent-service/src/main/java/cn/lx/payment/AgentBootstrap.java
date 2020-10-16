package cn.lx.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * cn.lx.payment.agent
 *
 * @Author Administrator
 * @date 11:56
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AgentBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(AgentBootstrap.class, args);
    }

}
