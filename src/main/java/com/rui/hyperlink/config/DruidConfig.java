package com.rui.hyperlink.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * 配置druid数据源
 * @author xiaorui
 */
@Configuration
public class DruidConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean StatViewServletBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("loginUsername", "xiaorui");
        parameters.put("loginPassword", "aa123456");
        // 允许所有ip访问
        parameters.put("allow", "");
        bean.setInitParameters(parameters);
        return bean;
    }

    @Bean
    public FilterRegistrationBean WebStatFilterBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("exclusions", "*.css,*.js,*.jpg,/druid/*,*.mp4");
        bean.setInitParameters(parameters);
        return bean;
    }
}
