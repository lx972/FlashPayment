package cn.lx.payment.merchant.service;

/**
 * cn.lx.payment.merchant.service
 *
 * @Author Administrator
 * @date 17:37
 */
public interface ISmsService {

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    String getSMSCode(String phone);

    /**
     * 校验验证码
     * @param verifiyCode
     * @param verifiykey
     */
    void checkCode(String verifiyCode, String verifiykey);
}
