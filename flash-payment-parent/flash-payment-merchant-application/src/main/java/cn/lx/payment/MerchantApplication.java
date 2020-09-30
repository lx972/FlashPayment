package cn.lx.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * cn.lx.payment.merchant
 * EnableDiscoveryClient EnableEurekaClient都是能够让注册中心能够发现，扫描到改服务
 * @Author Administrator
 * @date 11:36
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MerchantApplication {

    public static void main(String[] args) {
        SpringApplication.run(MerchantApplication.class,args);
    }

    /**
     * 使用okhttp3构建restTemplate
     * @return
     */
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        //获取消息转换器
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        //将消息格式转换为utf-8
        messageConverters.set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
}
