package com.jinsim.springboilerplate.account.dto;

import com.jinsim.springboilerplate.account.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupReqDto {

    @NotBlank(message = "이메일은 필수로 입력되어야 합니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotBlank(message = "이름은 필수로 입력되어야 합니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{2,10}$", message = "닉네임은 2~10자의 한글, 영어, 숫자로 작성되어야 합니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수로 입력되어야 합니다.")
    @Pattern(message = "비밀번호는 4~16자에 영어, 숫자, 특수문자가 포함된 형태로 작성되어야 합니다.",
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,16}")
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
                .password(encodePassword(password))
                .name(name)
                .build();
    }

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
