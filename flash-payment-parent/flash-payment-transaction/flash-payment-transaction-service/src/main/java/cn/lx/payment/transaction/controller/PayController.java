package cn.lx.payment.transaction.controller;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.transaction.entity.PayOrder;
import cn.lx.payment.transaction.util.BrowserType;
import cn.lx.payment.util.EncryptUtil;
import cn.lx.payment.util.ParseURLPairUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * cn.lx.payment.transaction.controller
 *
 * @Author Administrator
 * @date 9:55
 */
@Controller
public class PayController {

    @ApiOperation("去订单页面")
    @ApiImplicitParam(value = "ticket", name = "页面信息相关参数", required = true, dataType = "string", paramType = "path")
    @RequestMapping(value = "/pay-entry/{ticket}")
    public String payEntry(@PathVariable("ticket") String ticket, HttpServletRequest request) {
        try {
            //进行base64解码
            String stringBase64 = EncryptUtil.decodeUTF8StringBase64(ticket);
            PayOrder payOrder = JSON.parseObject(stringBase64, PayOrder.class);
            //将对象转成url格式
            String params = ParseURLPairUtil.parseURLPair(payOrder);
            String userAgent = request.getHeader("User-Agent");
            BrowserType browserType = BrowserType.valueOfUserAgent(userAgent);
            switch (browserType){
                case ALIPAY:
                    return "forward:/pay-page?"+params;
                case WECHAT:
                    return "forward:/pay-page?"+params;
                default:
                    return "forward:/error-page";
            }
        } catch (Exception e) {
            throw new BusinessException(CommonErrorCode.E_300012);
        }
    }
}
