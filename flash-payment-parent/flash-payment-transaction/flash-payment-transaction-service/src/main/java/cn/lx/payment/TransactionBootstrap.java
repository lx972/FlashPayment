package cn.lx.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * cn.lx.payment
 * EnableDiscoveryClient EnableEurekaClient都是能够让注册中心能够发现，扫描到改服务
 * @Author Administrator
 * @date 11:36
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TransactionBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(TransactionBootstrap.class,args);
    }
}
