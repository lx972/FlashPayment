package cn.lx.payment.merchant.controller;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.merchant.common.SecurityUtil;
import cn.lx.payment.merchant.covert.PayChannelParamAddCovert;
import cn.lx.payment.merchant.covert.PayChannelParamUpdateCovert;
import cn.lx.payment.merchant.vo.PayChannelParamAddVO;
import cn.lx.payment.merchant.vo.PayChannelParamUpdateVO;
import cn.lx.payment.transaction.api.IPayChannelParamService;
import cn.lx.payment.transaction.api.IPayChannelService;
import cn.lx.payment.transaction.api.IPlatformChannelService;
import cn.lx.payment.transaction.dto.PayChannelDTO;
import cn.lx.payment.transaction.dto.PayChannelParamDTO;
import cn.lx.payment.transaction.dto.PlatformChannelDTO;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 某商户针对某一种原始支付渠道的配置参数 前端控制器
 * </p>
 *
 * @author lx
 * @since 2020-09-30
 */
@Slf4j
@RestController
@Api(description = "商户平台‐渠道和支付参数相关")
public class PayChannelParamController {


    @Reference
    private IPlatformChannelService iPlatformChannelService;

    @Reference
    private IPayChannelService iPayChannelService;

    @Reference
    private IPayChannelParamService iPayChannelParamService;


    @ApiOperation(value = "查询平台的支付渠道")
    @GetMapping(value = "/my/platform-channels")
    public List<PlatformChannelDTO> queryPlatformChannel() {
        List<PlatformChannelDTO> platformChannelDTOList = iPlatformChannelService.queryPlatformChannel();
        return platformChannelDTOList;
    }

    @ApiOperation(value = "根据平台服务类型获取支付渠道列表")
    @ApiImplicitParam(name = "platformChannelCode", value = "平台服务类型code", required = true, dataType = "string", paramType = "path")
    @GetMapping(value = "/my/pay-channels/platform-channel/{platformChannelCode}")
    public List<PayChannelDTO> queryPayChannelByPlatformChannel(@PathVariable("platformChannelCode") String platformChannelCode) {
        List<PayChannelDTO> payChannelDTOList = iPayChannelService.queryPayChannelByPlatformChannel(platformChannelCode);
        return payChannelDTOList;
    }


    @ApiOperation(value = "为应用添加支付参数")
    @ApiImplicitParam(name = "payChannelParamAddVO", value = "支付参数", required = true,
            dataType = "PayChannelParamAddVO", paramType = "body")
    @PostMapping(value = "/my/pay-channel-params")
    public void createPayChannelParam(@RequestBody PayChannelParamAddVO payChannelParamAddVO) {
        Long merchantId = SecurityUtil.getMerchantId();
        if (null == merchantId) {
            throw new BusinessException(CommonErrorCode.E_999912);
        }
        if (payChannelParamAddVO == null) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        PayChannelParamDTO payChannelParamDTO = PayChannelParamAddCovert.instance.vo2dto(payChannelParamAddVO);
        iPayChannelParamService.savePayChannelParam(merchantId, payChannelParamDTO);
    }

    @ApiOperation(value = "更新支付参数")
    @ApiImplicitParam(name = "payChannelParamUpdateVO", value = "支付参数", required = true,
            dataType = "PayChannelParamUpdateVO", paramType = "body")
    @PutMapping(value = "/my/pay-channel-params")
    public void updatePayChannelParam(@RequestBody PayChannelParamUpdateVO payChannelParamUpdateVO) {
        Long merchantId = SecurityUtil.getMerchantId();
        if (null == merchantId) {
            throw new BusinessException(CommonErrorCode.E_999912);
        }
        if (payChannelParamUpdateVO == null) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        PayChannelParamDTO payChannelParamDTO = PayChannelParamUpdateCovert.instance.vo2dto(payChannelParamUpdateVO);
        iPayChannelParamService.savePayChannelParam(merchantId, payChannelParamDTO);
    }

    @ApiOperation(value = "获取指定应用指定服务类型下所包含的原始支付渠道参数列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "appId", value = "应用id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "platformChannel", value = "平台服务类型编码", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping(value = "/my/pay-channel-params/apps/{appId}/platform-channels/{platformChannel}")
    public List<PayChannelParamDTO> queryPayChannelParams(@PathVariable("appId") String appId
            , @PathVariable("platformChannel") String platformChannel) {
        if (Strings.isNullOrEmpty(appId)||Strings.isNullOrEmpty(platformChannel)) {
            throw new BusinessException(CommonErrorCode.E_300009);
        }
        List<PayChannelParamDTO> payChannelParamDTOList = iPayChannelParamService.queryPayChannelParams(appId, platformChannel);
        return payChannelParamDTOList;
    }


    @ApiOperation(value = "获取指定应用指定服务类型下指定原始支付方式所包含的原始支付渠道参数")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "appId", value = "应用id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "platformChannel", value = "平台服务类型编码", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "payChannel", value = "原始支付渠道编码", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping(value = "/my/pay-channel-params/apps/{appId}/platform-channels/{platformChannel}/pay-channels/{payChannel}")
    public PayChannelParamDTO queryPayChannelParam(@PathVariable("appId") String appId
            , @PathVariable("platformChannel") String platformChannel
            , @PathVariable("payChannel") String payChannel) {
        if (Strings.isNullOrEmpty(appId)
                ||Strings.isNullOrEmpty(platformChannel)
                ||Strings.isNullOrEmpty(payChannel)) {
            throw new BusinessException(CommonErrorCode.E_300009);
        }
       PayChannelParamDTO payChannelParamDTO = iPayChannelParamService.queryPayChannelParam(appId, platformChannel,payChannel);
        return payChannelParamDTO;
    }
}
