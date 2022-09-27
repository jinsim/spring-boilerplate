package com.jinsim.springboilerplate.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResDto {
    private String accessToken;
    private String refreshToken;

    @Builder
    public LoginResDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
