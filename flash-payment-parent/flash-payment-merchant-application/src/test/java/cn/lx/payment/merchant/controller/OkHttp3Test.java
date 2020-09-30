package cn.lx.payment.merchant.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * cn.lx.payment.merchant.controller
 *
 * @Author Administrator
 * @date 11:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OkHttp3Test {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void getBaiduHtml(){
        String url="http://www.baidu.com/";
        ResponseEntity<String> exchange = restTemplate.getForEntity(url, String.class);
        System.out.println(exchange.getBody());
    }

    @Test
    public void testSms(){
        String url="http://localhost:56085/sailing/generate?effectiveTime=60&name=sms";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity httpEntity = new HttpEntity<String>("{\n" +
                "\"mobile\":\"17607139545\"\n" +
                "}",headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST,httpEntity, String.class);
        System.out.println(exchange.getBody());
    }


    @Test
    public void checkSms(){
        String url="http://localhost:56085/sailing/verify?verificationCode=200081&verificationKey=sms:ce0b0c63dc37449a9655ffed04c256ea&name=sms";
        ResponseEntity<String> exchange = restTemplate.getForEntity(url, String.class);
        System.out.println(exchange.getBody());
    }
}
