package com.rui.hyperlink.security;

import com.rui.hyperlink.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 自定义认证
 * @author xiaorui
 */
@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SelfUserService selfUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = (String) authentication.getCredentials();
        SelfUserDetails user = selfUserService.loadUserByUsername(name);
        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("not find user");
        }
        // 由于密码加密，所有此处用此类方法来判断
        if (bCryptPasswordEncoder.matches(password,user.getPassword())) {
            String token = JwtUtil.createJwt(500 * 1000, user);
            System.out.println(1);
            stringRedisTemplate.opsForValue().set(user.getName(), token, 500, TimeUnit.SECONDS);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        throw new BadCredentialsException("password error");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
