package com.jinsim.springboilerplate.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "로그인 응답 객체", description = "로그인 응답 객체입니다.")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInResDto {
    private String email;
    private String accessToken;

    @Builder
    public SignInResDto(String email, String accessToken) {
        this.email = email;
        this.accessToken = accessToken;
    }
}
