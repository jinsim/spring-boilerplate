package com.jinsim.springboilerplate.user.dto;

import com.jinsim.springboilerplate.user.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAccountReqDto {
    @NotBlank
    private String email;
    @NotBlank
    private String name;

    @Builder
    public UpdateAccountReqDto(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public Account toEntity() {
        return Account.builder()
                .email(email)
                .name(name)
                .build();
    }
}
