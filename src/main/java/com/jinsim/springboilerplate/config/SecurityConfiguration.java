package com.jinsim.springboilerplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  // Spring Security 설정 활성화
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .csrf().disable() // Non-Browser Clients만을 위한 API 서버이므로, csrf 보호기능 해제
                .headers().frameOptions().sameOrigin() // h2-console 화면을 보기 위한 처리.
                .and()
                .authorizeRequests() // URL 별로 자원에 대한 접근 권한 관리
                .antMatchers("/", "/h2-console/**").permitAll()
                .anyRequest().authenticated(); // 나머지 요청은 권한 인증 사용자에게만 공개
        return http.build();
    }
}
