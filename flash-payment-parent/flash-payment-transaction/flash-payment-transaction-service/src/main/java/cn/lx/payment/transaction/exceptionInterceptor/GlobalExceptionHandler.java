package cn.lx.payment.transaction.exceptionInterceptor;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.domain.ErrorCode;
import cn.lx.payment.domain.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * cn.lx.payment.merchant.exceptionInterceptor
 *
 * @Author Administrator
 * @date 15:55
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse pocessException(Exception e){
        if (e instanceof BusinessException){
            //系统的自定义异常
            BusinessException businessException= (BusinessException) e;
            ErrorCode errorCode = businessException.getErrorCode();
            return new RestErrorResponse(String.valueOf(errorCode.getCode()),errorCode.getDesc());
        }
        //其他异常
        return new RestErrorResponse(String.valueOf(CommonErrorCode.UNKNOWN.getCode()),CommonErrorCode.UNKNOWN.getDesc());
    }
}
