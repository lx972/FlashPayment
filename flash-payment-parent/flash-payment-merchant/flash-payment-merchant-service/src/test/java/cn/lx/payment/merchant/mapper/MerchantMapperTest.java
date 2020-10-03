package cn.lx.payment.merchant.mapper;

import cn.lx.payment.merchant.entity.Merchant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * cn.lx.payment.merchant.mapper
 *
 * @Author Administrator
 * @date 16:32
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MerchantMapperTest {

    @Autowired
    private MerchantMapper merchantMapper;

    @Test
    public void test1(){
        Merchant merchant = new Merchant();
        merchant.setTenantId(111l);
        Merchant merchant1 = merchantMapper.selectById("1308713732683186177");
        System.out.println(merchant1);
        //merchantMapper.insert(merchant);
    }
}
