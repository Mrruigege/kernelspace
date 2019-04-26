package com.rui.hyperlink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xiaorui
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 页面访问地址为：http://127.0.0.1:8080/swagger-ui.html#/swagger-controller
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.rui.hyperlink.web.controller"))
                .paths(PathSelectors.any()).build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("HyperLink接口参考").description("关于接口以及描述")
                .termsOfServiceUrl("http://www.xiaoruisss.top")
                // .contact(contact)
                .version("2.0").build();
    }
}
