package cn.lx.payment.transaction.controller;

import cn.lx.payment.agent.api.IQueryPayAgentService;
import cn.lx.payment.agent.dto.AliConfigParam;
import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.domain.PaymentResponseDTO;
import cn.lx.payment.transaction.api.IPayChannelParamService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * cn.lx.payment.transaction.controller
 *
 * @Author Administrator
 * @date 10:06
 */
@RestController
public class QueryController {

    @Reference
    private IQueryPayAgentService iQueryPayAgentService;

    @Autowired
    private IPayChannelParamService iPayChannelParamService;

    @ApiOperation("根据付宝交易号或商户订单号查询订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "out_trade_no",value = "支付时传入的商户订单号",dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "trade_no",value = "支付时返回的支付宝交易号",dataType = "string",paramType = "query")
    })
    @GetMapping(value = "/query/ali/order")
    public PaymentResponseDTO<Object> queryAliOrder(@RequestParam(required = false) String out_trade_no,
                                                    @RequestParam(required = false) String trade_no){
        if (out_trade_no==null&&trade_no==null){
            //至少一个不为空
            throw new BusinessException(CommonErrorCode.E_300009);
        }
        //根据订单号查询出支付者的阿里支付参数
        AliConfigParam aliConfigParam=iPayChannelParamService.queryPayChannelParamByNo(out_trade_no,trade_no);
        //去代理服务查询
        return iQueryPayAgentService.queryAliOrder(out_trade_no,trade_no,aliConfigParam);
    }
}
