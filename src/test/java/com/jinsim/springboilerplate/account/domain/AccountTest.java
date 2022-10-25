package com.jinsim.springboilerplate.account.domain;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class AccountTest {

    @Test
    void 계정_생성_테스트() {
        final String email = "test@gmail";
        final String name = "테스트계정";
        final String encodedPassword = "encodedPassword";

        Account account = Account.builder()
                .email(email)
                .name(name)
                .encodedPassword(encodedPassword)
                .build();

        assertThat(account.getEmail()).isEqualTo(email);
        assertThat(account.getName()).isEqualTo(name);
        assertThat(account.getEncodedPassword()).isEqualTo(encodedPassword);
    }

    @Test
    void 계정_업데이트_테스트() {
        String email = "test@gmail.com";
        String name = "테스트계정";
        String encodedPassword = "encodedPassword";

        Account account = Account.builder()
                .email(email)
                .name(name)
                .encodedPassword(encodedPassword)
                .build();

        String newEmail = "newTest@gmail.com";
        String newName = "새테스트계정";

        account.updateMyAccount(newEmail, newName);

        assertThat(account.getEmail()).isEqualTo(newEmail);
        assertThat(account.getName()).isEqualTo(newName);

    }


}