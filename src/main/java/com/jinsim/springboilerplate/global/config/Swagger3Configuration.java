package com.jinsim.springboilerplate.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boilerplate API",
                description = "Spring Boilerplate API 명세서입니다.",
                version = "v1",
                contact = @Contact(
                        name = "jinsim",
                        url = "https://github.com/jinsim",
                        email = "jinsim726@gmail.com"
                ),
                license = @License(name = "Apache 2.0", url = "https://springdoc.org/"))
)
@Configuration
@RequiredArgsConstructor
public class Swagger3Configuration {
}
