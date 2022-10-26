package com.jinsim.springboilerplate.domain.account.dto;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyAccountResDto {

    private String email;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public MyAccountResDto(Account account) {
        this.email = account.getEmail();
        this.name = account.getName();
        this.createDate = account.getCreateDate();
        this.modifiedDate = account.getModifiedDate();
    }
}
