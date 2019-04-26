package com.rui.hyperlink.security;

import com.alibaba.fastjson.JSON;
import com.rui.hyperlink.base.ApiResponse;
import com.rui.hyperlink.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录认证成功的拦截器
 * @author xiaorui
 */
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        SelfUserDetails user = (SelfUserDetails) authentication.getPrincipal();
        if (user == null) {
            httpServletResponse.getWriter().write(JSON.toJSONString(ApiResponse.ofStatus(ApiResponse.Status.LOGIN_ERR)));
        }
        String token = stringRedisTemplate.opsForValue().get(user.getName());
        System.out.println(token);
        // 这里应该返回token
        httpServletResponse.getWriter().write(JSON.toJSONString(ApiResponse.ofSuccess(token)));
    }
}
