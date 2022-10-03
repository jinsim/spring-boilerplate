package com.jinsim.springboilerplate.account.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
