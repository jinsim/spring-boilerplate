package com.jinsim.springboilerplate.account.repository;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.global.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryTest extends RepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    private Account savedAccount;
    private String email;
    private String name;

    @BeforeEach
    public void setUp() throws Exception {
        email = "test@gmail.com";
        name = "테스트계정";
        String encodedPassword = "encodedPassword";
        Account account = Account.builder()
                .email(email)
                .name(name)
                .encodedPassword(encodedPassword)
                .build();
        savedAccount = accountRepository.save(account);
    }

    @Test
    public void findByEmailTest() {
        final Optional<Account> byEmail = accountRepository.findByEmail(email);
        final Account account = byEmail.get();
        assertThat(account.getEmail()).isEqualTo(email);
    }

    @Test
    public void existsByEmail_존재하는_경우() {
        final boolean existsByEmail = accountRepository.existsByEmail(email);
        assertThat(existsByEmail).isTrue();
    }

    @Test
    public void existsByEmail_존재하지않는_경우() {
        final boolean existsByEmail = accountRepository.existsByEmail("falseTest@gmail.com");
        assertThat(existsByEmail).isFalse();
    }
}