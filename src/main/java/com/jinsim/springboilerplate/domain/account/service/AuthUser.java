package com.jinsim.springboilerplate.domain.account.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
// 로그인한 사용자의 정보를 파라미터로 받고 싶을때 사용한다.
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")
public @interface AuthUser {
}
