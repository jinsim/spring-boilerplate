package com.jinsim.springboilerplate.account.dto;

import com.jinsim.springboilerplate.account.domain.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyAccountResDto {

    @NotBlank
    private String email;
    @NotBlank
    private String name;

    public MyAccountResDto(Account account) {
        this.email = account.getEmail();
        this.name = account.getName();
    }
}
