package com.jinsim.springboilerplate.config;

import com.jinsim.springboilerplate.account.service.UserDetailsServiceImpl;
import com.jinsim.springboilerplate.config.jwt.JwtAccessDeniedHandler;
import com.jinsim.springboilerplate.config.jwt.JwtEntryPoint;
import com.jinsim.springboilerplate.config.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  // Spring Security 설정 활성화
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtEntryPoint jwtEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtFilter jwtFilter;
    private final UserDetailsServiceImpl userDetailsService;

    // Spring Security에서 제공하는 비밀번호 암호화 클래스.
    // Service에서 사용할 수 있도록 Bean으로 등록한다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Non-Browser Clients만을 위한 API 서버이므로, csrf 보호기능 해제
                .headers().frameOptions().sameOrigin() // h2-console 화면을 보기 위한 처리.

                .and()
                .authorizeRequests() // URL 별로 자원에 대한 접근 권한 관리
                .antMatchers("/", "/h2-console/**", "/auth/**").permitAll()
                .anyRequest().authenticated() // 나머지 요청은 권한 인증 사용자에게만 공개

                .and()
                .exceptionHandling() // 예외 처리 기능 작동
                .authenticationEntryPoint(jwtEntryPoint) // 인증 실패시 처리
                .accessDeniedHandler(jwtAccessDeniedHandler) // 인가 실패시 처리


                .and() // 시큐리티는 기본적으로 세션을 사용하지만, 우리는 세션을 사용하지 않기 때문에 Stateless로 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                // JwtFilter를 UsernamePasswordAuthenticationFilter 전에 추가한다.
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
