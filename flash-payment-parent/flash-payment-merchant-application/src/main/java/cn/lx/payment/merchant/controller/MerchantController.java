package cn.lx.payment.merchant.controller;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.merchant.api.IMerchantService;
import cn.lx.payment.merchant.common.SecurityUtil;
import cn.lx.payment.merchant.covert.MerchantApplayCovert;
import cn.lx.payment.merchant.covert.MerchantRegisterCovert;
import cn.lx.payment.merchant.dto.MerchantDTO;
import cn.lx.payment.merchant.service.IFileService;
import cn.lx.payment.merchant.service.ISmsService;
import cn.lx.payment.merchant.vo.MerchantApplayVO;
import cn.lx.payment.merchant.vo.MerchantRegisterVO;
import cn.lx.payment.util.PhoneUtil;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@Api(description = "商户平台-商户相关")
public class MerchantController {

    @Reference
    private IMerchantService iMerchantService;

    @Autowired
    private ISmsService iSmsService;

    @Autowired
    private IFileService iFileService;

    @ApiOperation(value = "根据id查询商户信息")
    @ApiImplicitParam(value = "主键id", name = "id", required = true, paramType = "path", dataType = "String")
    @GetMapping(value = "/merchant/{id}")
    public MerchantDTO queryMerchantById(@PathVariable("id") Long id) {
        MerchantDTO merchantDTO = iMerchantService.queryMerchantById(id);
        return merchantDTO;
    }

    @ApiOperation(value = "获取验证码")
    @ApiImplicitParam(name = "phone", value = "手机号", dataType = "string", paramType = "query", required = true)
    @GetMapping("/sms")
    public String getSMSCode(@RequestParam String phone) {
        log.info("获取验证码");
        String sms = iSmsService.getSMSCode(phone);
        return sms;
    }


    @ApiOperation(value = "注册商户")
    @ApiImplicitParam(name = "merchantRegisterVO", value = "商户注册信息", required = true, dataType = "MerchantRegisterVO", paramType = "body")
    @PostMapping("/merchants/register")
    public MerchantDTO registerMerchant(@RequestBody MerchantRegisterVO merchantRegisterVO) {
        log.info("开始注册");
        //验证是否传入参数
        if (null == merchantRegisterVO) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        //验证是否传入手机号
        if (Strings.isNullOrEmpty(merchantRegisterVO.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_100112);
        }
        //手机号合法性校验
        if (!PhoneUtil.isMatches(merchantRegisterVO.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_100109);
        }
        //验证是否传入用户名
        if (Strings.isNullOrEmpty(merchantRegisterVO.getUsername())) {
            throw new BusinessException(CommonErrorCode.E_100110);
        }
        //验证是否传入密码
        if (Strings.isNullOrEmpty(merchantRegisterVO.getPassword())) {
            throw new BusinessException(CommonErrorCode.E_100111);
        }
        //校验验证码
        iSmsService.checkCode(merchantRegisterVO.getVerifiyCode(), merchantRegisterVO.getVerifiykey());

        MerchantDTO merchantDTO = MerchantRegisterCovert.instance.vo2dto(merchantRegisterVO);
        merchantDTO = iMerchantService.registerMerchant(merchantDTO);
        return merchantDTO;
    }


    @ApiOperation(value = "证件照上传")
    @PostMapping("/upload")
    public String upload(@ApiParam(value = "上传的文件", required = true) @RequestParam(value = "file") MultipartFile file) {
        String filename = iFileService.upload(file);
        return filename;
    }


    @ApiOperation(value = "商户资质申请")
    @ApiImplicitParam(name = "merchantApplayVO", value = "资质申请的参数", dataType = "MerchantApplayVO", paramType = "body")
    @PostMapping("/my/merchants/save")
    public MerchantDTO saveMerchant(@RequestBody MerchantApplayVO merchantApplayVO) {
        //解析token得到商户id
        Long merchantId = SecurityUtil.getMerchantId();
        if (merchantId == null || merchantApplayVO == null) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        MerchantDTO merchantDTO = MerchantApplayCovert.INSTANCE.vo2dto(merchantApplayVO);
        //商户资质申请
        merchantDTO = iMerchantService.applayMerchant(merchantId, merchantDTO);
        return merchantDTO;
    }

    @ApiOperation(value = "查询登录用户的商户信息")
    @GetMapping("/my/merchants")
    public MerchantDTO queryMerchantInfo() {
        Long merchantId = SecurityUtil.getMerchantId();
        MerchantDTO merchantDTO = iMerchantService.queryMerchantById(merchantId);
        return merchantDTO;
    }
}
