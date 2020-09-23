package cn.lx.payment.merchant.controller;


import cn.lx.payment.merchant.api.IStoreStaffService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2020-09-22
 */
@Slf4j
@Controller
@Api(value = "", tags = "", description="")
public class StoreStaffController {

    @Reference
    private IStoreStaffService iStoreStaffService;
}
