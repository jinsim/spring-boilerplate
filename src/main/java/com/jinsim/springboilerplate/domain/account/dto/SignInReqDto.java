package com.jinsim.springboilerplate.domain.account.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInReqDto
{
    @NotBlank(message = "이메일은 필수로 입력되어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수로 입력되어야 합니다.")
    private String password;

    @Builder
    public SignInReqDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
