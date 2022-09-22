package com.jinsim.springboilerplate.Account.dto;

import com.jinsim.springboilerplate.Account.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupReqDto {
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;

    @Builder
    public SignupReqDto(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public Account toEntity() {
        return Account.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }
}
