package com.rui.hyperlink.filter;

import com.rui.hyperlink.security.SelfUserDetails;
import com.rui.hyperlink.security.SelfUserService;
import com.rui.hyperlink.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiaorui
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private SelfUserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (!httpServletRequest.getRequestURI().equals("/api/user/login") &&
                !httpServletRequest.getRequestURI().equals("/api/user/register")) {
            String token = httpServletRequest.getHeader("token");
            if (token != null && JwtUtil.isVerify(token)) {
                // 有token且token正确，创建这个对象
                Claims claims = JwtUtil.parseJWT(token);
                String username = (String) claims.get("username");
                String rtoken = stringRedisTemplate.opsForValue().get(username);
                if (rtoken != null && rtoken.equals(token)) {
                    SelfUserDetails userDetails = userService.loadUserByUsername(username);
                    if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
