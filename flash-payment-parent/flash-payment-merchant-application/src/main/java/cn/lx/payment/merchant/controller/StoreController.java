package cn.lx.payment.merchant.controller;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.domain.PageVO;
import cn.lx.payment.merchant.api.IAppService;
import cn.lx.payment.merchant.api.IStoreService;
import cn.lx.payment.merchant.common.SecurityUtil;
import cn.lx.payment.merchant.dto.StoreDTO;
import cn.lx.payment.transaction.api.IPayOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;


/**
 * cn.lx.payment.merchant.controller
 *
 * @Author Administrator
 * @date 16:38
 */
@RestController
@Api(value = "商户-门店相关", tags = "商户-门店相关", description = "商户-门店相关")
public class StoreController {

    @Reference
    private IStoreService iStoreService;

    @Reference
    private IAppService iAppService;

    @Reference
    private IPayOrderService iPayOrderService;


    @ApiOperation("分页查询商户下门店列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "pageNo", name = "页码", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(value = "pageSize", name = "每页的记录数", required = true, dataType = "integer", paramType = "query")
    })
    @PostMapping("/my/stores/merchants/page")
    public PageVO<StoreDTO> queryStoreByPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        Long merchantId = SecurityUtil.getMerchantId();
        PageVO<StoreDTO> storeDTOS = iStoreService.queryStoreByPage(merchantId, pageNo, pageSize);
        return storeDTOS;
    }

    @ApiOperation("生成对应应用门店的二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "appId",name = "应用id",required = true,dataType = "string",paramType = "path"),
            @ApiImplicitParam(value = "storeId",name = "门店id",required = true,dataType = "Long",paramType = "path"),
    })
    @GetMapping(value = "/my/apps/{appId}/stores/{storeId}/app-store-qrcode")
    public String createCScanBStoreQRCode(@PathVariable("appId") String appId,@PathVariable("storeId") Long storeId) throws BusinessException {
        Long merchantId = SecurityUtil.getMerchantId();
        if (merchantId==null||appId==null||storeId==null){
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        //校验应用是否属于当前商户
        Boolean flag1=iAppService.queryAppInMerchant(merchantId,appId);
        if (!flag1){
            throw new BusinessException(CommonErrorCode.E_200005);
        }
        //校验门店是否属于当前商户
        Boolean flag2=iStoreService.queryStoreInMerchant(merchantId,storeId);
        if (!flag2){
            throw new BusinessException(CommonErrorCode.E_200006);
        }
        //调用交易服务生成闪聚服务的订单和二维码
        return iPayOrderService.createCScanBStoreQRCode(merchantId,appId,storeId);
    }
}
