package cn.lx.payment.gateway.config;


import cn.lx.payment.domain.RestResponse;
import cn.lx.payment.gateway.common.util.HttpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAccessDeniedHandler  implements AccessDeniedHandler {

    /**
     * 自定义403响应内容
     * @param request
     * @param response
     * @param ex
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
        RestResponse restResponse = new RestResponse(HttpStatus.FORBIDDEN.value(),"没有权限");
        HttpUtil.writerError(restResponse,response);
    }
}
