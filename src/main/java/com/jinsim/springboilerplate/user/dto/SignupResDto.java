package com.jinsim.springboilerplate.user.dto;

import com.jinsim.springboilerplate.user.domain.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupResDto {
    @NotBlank
    private String email;
    @NotBlank
    private String name;

    public SignupResDto(Account account) {
        this.email = account.getEmail();
        this.name = account.getName();
    }
}