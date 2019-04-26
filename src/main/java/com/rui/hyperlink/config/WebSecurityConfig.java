package com.rui.hyperlink.config;

import com.rui.hyperlink.filter.JwtAuthenticationTokenFilter;
import com.rui.hyperlink.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 请求拦截类
 * @author xiaorui
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private AjaxAuthenticationSuccessHandler successHandler;

    @Autowired
    private AjaxAuthenticationFailerHandler failerHandler;

    @Autowired
    private AjaxLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AjaxAuthenticationEntryPoint entryPoint;

    @Autowired
    private AjaxAccessDeniedHandler deniedHandler;

    @Autowired
    private SelfUserService selfUserService;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthProvider authProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll() // 主页允许所有人访问
                .antMatchers("/statics/**").permitAll()  // 对静态文件允许所有人访问
                .antMatchers("/api/user/login").permitAll()// 对用户登录接口允许所有人访问
                .antMatchers("/api/user/register").permitAll() //用户注册接口允许所有人访问
                .antMatchers("/api/user/logout").permitAll() // 对用户登出接口允许所有人访问
                .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN") // 对用户私有接口需要权限
                .antMatchers("/api/admin/**").hasRole("ADMIN") // 对管理员接口需要管理员权限
                .and()
                .formLogin() //开启登录
                .loginProcessingUrl("/api/user/login")
                .successHandler(successHandler) //登录成功的拦截器
                .failureHandler(failerHandler) //登录失败的拦截器
                .and()
                .logout()
                .logoutUrl("/api/user/logout")
                .logoutSuccessHandler(logoutSuccessHandler) //登出成功的拦截器
                .invalidateHttpSession(true); // 登出删除session

        // 关闭session
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling().accessDeniedHandler(deniedHandler)
                .authenticationEntryPoint(entryPoint);
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 设置自定义认证策略
     * @param auth
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // 通过bCryptPasswordEncoder密码加密认证，注册是要使用这个类去加密存入数据库
        auth.userDetailsService(selfUserService).passwordEncoder(bCryptPasswordEncoder());
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
