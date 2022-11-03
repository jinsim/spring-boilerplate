package com.jinsim.springboilerplate.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Schema(name = "Access Token 객체", description = "Access Token 값이 들어있는 객체")
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
