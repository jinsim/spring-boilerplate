package com.jinsim.springboilerplate.account.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshReqDto {

    @NotBlank(message = "이메일은 필수로 입력되어야 합니다.")
    private String email;

    @NotBlank(message = "AccessToken은 필수로 입력되어야 합니다.")
    private String accessToken;

    @Builder
    public RefreshReqDto(String email, String accessToken) {
        this.email = email;
        this.accessToken = accessToken;
    }
}
