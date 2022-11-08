package com.jinsim.springboilerplate.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
        license = @License(name = "Apache 2.0", url = "https://springdoc.org/")),
        security = {
                @SecurityRequirement(name = "JWT Authentication")
        }
)
@SecurityScheme(
        name = "JWT Authentication",
        type = SecuritySchemeType.HTTP,
        description = "JWT 인증을 위한 헤더. Bearer Authentication",
        paramName = "Authorization",
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
@RequiredArgsConstructor
public class Swagger3Configuration {
}
