package cn.lx.payment.merchant.service.impl;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.merchant.service.ISmsService;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * cn.lx.payment.merchant.service.impl
 *
 * @Author Administrator
 * @date 17:38
 */
@Service
public class SmsSerivceImpl implements ISmsService {

    @Value("${sms.url}")
    private String smsUrl;

    @Value("${sms.effectiveTime}")
    private String effectiveTime;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    @Override
    public String getSMSCode(String phone) {
        if (Strings.isNullOrEmpty(phone)){
            throw new BusinessException(CommonErrorCode.E_100112);
        }
        String url = smsUrl + "/generate" + "?effectiveTime=" + effectiveTime + "&name=sms";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity httpEntity = new HttpEntity<String>("{\n" +
                "\"mobile\":" + phone +
                "}", headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String body = exchange.getBody();
        Map<String, Object> resp = JSON.parseObject(body, Map.class);
        if (resp.get("result") == null) {
            throw new BusinessException(CommonErrorCode.E_100103);
        }
        Map<String, String> result = (Map<String, String>) resp.get("result");
        return result.get("key");
    }

    /**
     * 校验验证码
     *
     * @param verifiyCode
     * @param verifiykey
     */
    @Override
    public void checkCode(String verifiyCode, String verifiykey) {
        String url = smsUrl + "/verify" + "?verificationCode=" + verifiyCode + "&verificationKey=" + verifiykey + "&name=sms";
        //String url="http://localhost:56085/sailing/verify?verificationCode=200081&verificationKey=sms:ce0b0c63dc37449a9655ffed04c256ea&name=sms";
        Map<String, Object> resp=null;
        try {
            ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
            String body = exchange.getBody();
            resp = JSON.parseObject(body, Map.class);
        }catch (Exception e){
            throw new BusinessException(CommonErrorCode.E_100102);
        }

        if (resp==null||resp.get("result") == null||(boolean)resp.get("result")==false) {
            throw new BusinessException(CommonErrorCode.E_100102);
        }
    }
}
