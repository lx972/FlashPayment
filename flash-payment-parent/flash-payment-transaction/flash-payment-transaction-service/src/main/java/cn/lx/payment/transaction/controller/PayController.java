package cn.lx.payment.transaction.controller;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.domain.PaymentResponseDTO;
import cn.lx.payment.merchant.api.IAppService;
import cn.lx.payment.merchant.dto.AppDTO;
import cn.lx.payment.transaction.api.IPayOrderService;
import cn.lx.payment.transaction.covert.PayOrderCovert;
import cn.lx.payment.transaction.dto.PayOrderDTO;
import cn.lx.payment.transaction.entity.PayOrder;
import cn.lx.payment.transaction.util.BrowserType;
import cn.lx.payment.transaction.vo.OrderConfirmVO;
import cn.lx.payment.util.EncryptUtil;
import cn.lx.payment.util.IPUtil;
import cn.lx.payment.util.ParseURLPairUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * cn.lx.payment.transaction.controller
 *
 * @Author Administrator
 * @date 9:55
 */
@Controller
@Api(value = "支付-下单相关",tags = "支付-下单相关",description = "支付-下单相关")
@Slf4j
public class PayController {

    @Reference
    private IAppService iAppService;


    @Autowired
    private IPayOrderService iPayOrderService;

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


    @ApiOperation("支付宝下单")
    @ApiImplicitParam(value = "orderConfirmVO", name = "确认支付所需信息", required = true, dataType = "OrderConfirmVO", paramType = "body")
    @PostMapping(value = "/createAliPayOrder")
    public void createAlipayOrderForStore(OrderConfirmVO orderConfirmVO, HttpServletRequest request, HttpServletResponse response) {
        if (orderConfirmVO==null||orderConfirmVO.getAppId()==null){
            throw new BusinessException(CommonErrorCode.E_300003);
        }
        PayOrderDTO payOrderDTO = PayOrderCovert.INSTANCE.vo2dto(orderConfirmVO);
        //设置访问ip
        payOrderDTO.setClientIp(IPUtil.getIpAddr(request));
        //查询应用的相关信息
        AppDTO appDTO = iAppService.queryApp(orderConfirmVO.getAppId());
        //设置商户id
        payOrderDTO.setMerchantId(appDTO.getMerchantId());
        //设置支付渠道
        payOrderDTO.setPayChannel("ALIPAY_WAP");
        //保存订单,调用支付代理服务，进行支付
        PaymentResponseDTO<String> paymentResponseDTO = iPayOrderService.submitOrderByAli(payOrderDTO);

        log.info("支付宝H5支付响应的结果：" + paymentResponseDTO.getContent());
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_400002);
        }
        writer.write(paymentResponseDTO.getContent());
        writer.flush();
        writer.close();
    }
}
