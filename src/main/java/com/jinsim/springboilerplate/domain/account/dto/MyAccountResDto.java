package com.jinsim.springboilerplate.domain.account.dto;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Schema(name = "회원정보 응답 객체", description = "회원가입, 회원조회, 회원수정 응답에 사용되는 객체입니다.")
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
