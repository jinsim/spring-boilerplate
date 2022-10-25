package com.jinsim.springboilerplate.domain.account.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenDto {

    @NotBlank(message = "Access Token 은 필수로 입력되어야 합니다.")
    private String accessToken;

    @Builder
    public AccessTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
