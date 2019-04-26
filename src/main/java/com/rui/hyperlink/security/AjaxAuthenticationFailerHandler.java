package com.rui.hyperlink.security;

import com.alibaba.fastjson.JSON;
import com.rui.hyperlink.base.ApiResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录失败的拦截器
 * @author xiaorui
 */
@Component
public class AjaxAuthenticationFailerHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String token = httpServletRequest.getHeader("token");
        if (token == null && !httpServletRequest.getRequestURI().equals("/api/user/login")) {
            httpServletResponse.getWriter().write(JSON.toJSONString(ApiResponse.ofStatus(ApiResponse.Status.NOT_LOGIN)));
        }
        // 这里可能是用户名错误，也可能是密码错误
        httpServletResponse.getWriter().write(JSON.toJSONString(ApiResponse.ofStatus(ApiResponse.Status.LOGIN_ERR)));
    }
}
