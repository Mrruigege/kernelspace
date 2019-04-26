package com.rui.hyperlink.security;

import com.alibaba.fastjson.JSON;
import com.rui.hyperlink.base.ApiResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理用户没有登录而且访问接口的拦截器
 * @author xiaorui
 */
@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (!httpServletRequest.getRequestURI().equals("/api/user/login")) {
            httpServletResponse.getWriter().write(JSON.toJSONString(ApiResponse.ofStatus(ApiResponse.Status.NOT_LOGIN)));
        }
    }
}
