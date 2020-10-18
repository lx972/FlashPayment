package cn.lx.payment.transaction.controller;

import cn.lx.payment.agent.dto.AliConfigParam;
import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.transaction.api.IPayChannelParamService;
import cn.lx.payment.transaction.api.IPayOrderService;
import cn.lx.payment.transaction.dto.PayChannelParamDTO;
import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.AlipaySignature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * cn.lx.payment.transaction.controller
 *
 * @Author Administrator
 * @date 9:55
 */
@RestController
@Api(value = "支付-异步通知相关", tags = "支付-异步通知相关", description = "支付-异步通知相关")
@Slf4j
public class NotifyController {

    @Autowired
    private IPayChannelParamService iPayChannelParamService;

    @Autowired
    private IPayOrderService iPayOrderService;


    @ApiOperation("支付宝异步通知结果")
    @ApiImplicitParam(value = "paramsMap", name = "通知结果", required = true, dataType = "Map", paramType = "path")
    @RequestMapping(value = "/ali/notify")
    public String payEntry(@RequestParam Map<String, String> paramsMap) {

        //根据条件查询支付参数信息
        PayChannelParamDTO payChannelParamDTO = iPayChannelParamService.queryPayChannelParam(
                "a94f0fd09d5d4dcd980b42dfa667cdd7",
                "shanju_c2b",
                "ALIPAY_WAP");

        if (payChannelParamDTO == null) {
            throw new BusinessException(CommonErrorCode.E_300007);
        }
        //支付宝渠道参数
        AliConfigParam aliConfigParam = JSON.parseObject(payChannelParamDTO.getParam(), AliConfigParam.class);

        aliConfigParam.setCharest("utf-8");
        try {
            //调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap,
                    aliConfigParam.alipayPublicKey,
                    aliConfigParam.getCharest(),
                    aliConfigParam.getSigntype());

            if (signVerified) {
// TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
                /*
                 * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，2
                 * 、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
                 * 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no
                 * 这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
                 * 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，
                 * 则表明本次通知是异常通知，务必忽略
                 * */
                //先判断支付宝应用id是否正确
                if (aliConfigParam.getAppId().equals(paramsMap.get("app_id"))) {
                    //正确，去服务层校验订单号和支付金额
                    iPayOrderService.verifyReturnParam(paramsMap.get("out_trade_no"),
                            paramsMap.get("total_amount"),
                            paramsMap.get("trade_no"),
                            paramsMap.get("gmt_create"));
                    log.info("支付宝异步通知结果验证成功");
                    return "success";
                }
            } else {
// TODO 验签失败则记录异常日志，并在response中返回failure.
                log.error("支付宝异步通知结果验证失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝异步通知结果验证失败");
        }
        return "failure";
    }

}
