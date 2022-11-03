package com.jinsim.springboilerplate.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "로그인 정보 객체", description = "Service 로그인 과정 중 생성되는 객체로, 응답 생성에 사용됩니다.")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInTokenDto {
    private String email;
    private String accessToken;
    private String refreshToken;
    private Long refreshTokenValidationMs;

    @Builder
    public SignInTokenDto(String email, String accessToken, String refreshToken, Long refreshTokenValidationMs) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenValidationMs = refreshTokenValidationMs;
    }

    public SignInResDto toSignInResDto() {
        return new SignInResDto(email, accessToken);
    }
}
