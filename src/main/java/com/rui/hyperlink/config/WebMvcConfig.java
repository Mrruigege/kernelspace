package com.rui.hyperlink.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaorui
 */
@Configuration
public class WebMvcConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
