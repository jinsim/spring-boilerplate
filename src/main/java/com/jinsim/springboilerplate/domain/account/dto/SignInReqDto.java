package com.jinsim.springboilerplate.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

@Schema(name = "로그인 요청 객체", description = "로그인 요청 객체입니다.")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInReqDto
{
    @Schema(description = "이메일", example = "test@gmail.com")
    @NotBlank(message = "이메일은 필수로 입력되어야 합니다.")
    private String email;

    @Schema(description = "비밀번호", example = "password123!")
    @NotBlank(message = "비밀번호는 필수로 입력되어야 합니다.")
    private String password;

    @Builder
    public SignInReqDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
