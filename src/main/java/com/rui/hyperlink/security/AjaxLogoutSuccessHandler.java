package com.rui.hyperlink.security;

import com.alibaba.fastjson.JSON;
import com.rui.hyperlink.base.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理用户成功退出的拦截器
 * @author xiaorui
 */
@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 由于此处登出成功，所有拿不到authentication.getPrincipal();
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 删除redis中保留的jwt
        String user = httpServletRequest.getHeader("user");
        if (user != null) {
            stringRedisTemplate.delete(user);
        }
        httpServletResponse.getWriter().write(JSON.toJSONString(ApiResponse.ofSuccess(null)));
    }
}
