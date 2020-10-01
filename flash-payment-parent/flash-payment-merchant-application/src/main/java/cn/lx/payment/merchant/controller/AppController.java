package cn.lx.payment.merchant.controller;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.merchant.api.IAppService;
import cn.lx.payment.merchant.common.SecurityUtil;
import cn.lx.payment.merchant.covert.AppCreateCovert;
import cn.lx.payment.merchant.dto.AppDTO;
import cn.lx.payment.merchant.vo.AppCreateVO;
import cn.lx.payment.transaction.api.IAppPlatformChannelService;
import cn.lx.payment.transaction.api.IPayChannelService;
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
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-09-22
 */
@Slf4j
@RestController
@Api(description = "商户平台-应用相关")
public class AppController {

    @Reference
    private IAppService iAppService;

    @Reference
    private IAppPlatformChannelService iAppPlatformChannelService;


    @ApiOperation(value = "创建应用")
    @ApiImplicitParam(name = "appCreateVO", value = "应用创建的相关信息", required = true, dataType = "AppCreateVO", paramType = "body")
    @PostMapping(value = "/my/apps")
    public AppDTO createApp(@RequestBody AppCreateVO appCreateVO) {
        Long merchantId = SecurityUtil.getMerchantId();
        if (null == merchantId || null == appCreateVO) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        AppDTO appDTO = AppCreateCovert.INSTANCE.vo2dto(appCreateVO);
        appDTO = iAppService.createApp(merchantId, appDTO);
        return appDTO;
    }

    @ApiOperation(value = "查询当前商户的应用")
    @GetMapping(value = "/my/apps")
    public List<AppDTO> queryMyApps() {
        Long merchantId = SecurityUtil.getMerchantId();
        if (null == merchantId) {
            throw new BusinessException(CommonErrorCode.E_999912);
        }
        List<AppDTO> appDTOList = iAppService.queryApps(merchantId);
        return appDTOList;
    }

    @ApiOperation(value = "根据应用id查询应用的详细信息")
    @ApiImplicitParam(name = "appId", value = "应用id", required = true, dataType = "string", paramType = "path")
    @GetMapping(value = "/my/apps/{appId}")
    public AppDTO getApp(@PathVariable("appId") String appId) {
        AppDTO appDTO = iAppService.queryApp(appId);
        return appDTO;
    }

    @ApiOperation(value = "为应用绑定支付服务")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "appId", value = "应用id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "platformChannelCodes", value = "服务类型code", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping(value = "/my/apps/{appId}/platform-channels")
    public void bindPlatformForApp(@PathVariable("appId") String appId
            , @RequestParam(value = "platformChannelCodes") String platformChannelCodes) {
        iAppPlatformChannelService.bindPlatformChannelForApp(appId, platformChannelCodes);
    }


    @ApiOperation(value = "查询应用是否已经绑定某个支付服务")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "appId", value = "应用id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "platformChannelCodes", value = "服务类型code", required = true, dataType = "string", paramType = "query"),
    })
    @GetMapping(value = "/my/apps/{appId}/platform-channels")
    public int queryBindPlatformForApp(@PathVariable("appId") String appId
            , @RequestParam(value = "platformChannelCodes") String platformChannelCodes) {
        int flag = iAppPlatformChannelService.queryBindPlatformForApp(appId, platformChannelCodes);
        return flag;
    }
}
