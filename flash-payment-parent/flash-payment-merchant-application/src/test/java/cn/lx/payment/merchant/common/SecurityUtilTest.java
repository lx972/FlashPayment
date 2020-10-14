package cn.lx.payment.merchant.common;

import cn.lx.payment.merchant.api.IMerchantService;
import cn.lx.payment.merchant.dto.MerchantDTO;
import cn.lx.payment.util.EncryptUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.dubbo.config.annotation.Reference;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * cn.lx.payment.merchant.common
 *
 * @Author Administrator
 * @date 9:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityUtilTest {

    @Reference
    private IMerchantService iMerchantService;

    @Test
    public void createToken() throws JSONException {

        Long merchantId=1308713732683186177l;
        MerchantDTO merchantDTO = iMerchantService.queryMerchantById(merchantId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile",merchantDTO.getMobile());
        jsonObject.put("user_name",merchantDTO.getUsername());
        jsonObject.put("merchantId",merchantDTO.getId());
        String base64 = EncryptUtil.encodeUTF8StringBase64(jsonObject.toString());
        String token="Bearer "+base64;
        System.out.println(token);
    }

    @Test
    public void queryMerchantByTenantId() throws JSONException {


        MerchantDTO merchantDTO = iMerchantService.queryMerchantByTenantId(12l);
        System.out.println(merchantDTO);
    }

}
