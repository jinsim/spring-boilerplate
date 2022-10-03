package com.jinsim.springboilerplate.account.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginReqDto
{
    @NotBlank(message = "이메일은 필수로 입력되어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수로 입력되어야 합니다.")
    private String password;

    @Builder
    public LoginReqDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
