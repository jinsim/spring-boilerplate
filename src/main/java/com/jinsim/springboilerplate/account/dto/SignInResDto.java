package com.jinsim.springboilerplate.account.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
