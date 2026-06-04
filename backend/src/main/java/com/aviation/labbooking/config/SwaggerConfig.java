package com.aviation.labbooking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Aviation Laboratory Equipment Booking System API")
                        .version("1.0.0")
                        .description("航空实验室设备调度平台接口文档")
                        .contact(new Contact().name("Aviation Lab Team")));
    }
}
