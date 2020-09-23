package cn.lx.payment.merchant.controller;

import cn.lx.payment.merchant.api.IMerchantService;
import cn.lx.payment.merchant.dto.MerchantDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2020-09-22
 */
@Slf4j
@RestController
@Api(value = "商户平台-商户相关", tags = "商户平台-商户相关", description="商户平台-商户相关")
public class MerchantController {

    @Reference
    private IMerchantService iMerchantService;

    @ApiOperation(value = "根据id查询企业所有人信息",httpMethod = "get")
    @ApiImplicitParam(value = "主键id",name = "id",required = true, paramType = "path",dataType = "String")
    @GetMapping(value = "/merchant/{id}")
    public MerchantDTO queryMerchantById(@PathVariable("id")String id){
        MerchantDTO merchantDTO = iMerchantService.queryMerchantById(id);
        return merchantDTO;
    }
}
