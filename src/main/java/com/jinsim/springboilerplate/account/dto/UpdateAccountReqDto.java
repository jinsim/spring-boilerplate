package com.jinsim.springboilerplate.account.dto;

import com.jinsim.springboilerplate.account.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAccountReqDto {

    @NotBlank(message = "이메일은 필수로 입력되어야 합니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotBlank(message = "이름은 필수로 입력되어야 합니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{2,10}$", message = "닉네임은 2~10자의 한글, 영어, 숫자로 작성되어야 합니다.")
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
