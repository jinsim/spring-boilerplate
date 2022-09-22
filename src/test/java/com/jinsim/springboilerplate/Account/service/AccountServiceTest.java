package com.jinsim.springboilerplate.Account.service;

import com.jinsim.springboilerplate.Account.domain.Account;
import com.jinsim.springboilerplate.Account.dto.SignupReqDto;
import com.jinsim.springboilerplate.Account.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void 회원가입_성공() {
        // given
        SignupReqDto requestDto = buildSignupReqDto();
        Account account = requestDto.toEntity();
        Long accountId = 1L;
        ReflectionTestUtils.setField(account, "id", accountId);

        // mock
        given(accountRepository.save(any(Account.class))).willReturn(account);
        given(accountRepository.findById(accountId)).willReturn(Optional.ofNullable(account));

        // when
        Long findAccountId = accountService.signup(requestDto);

        // then
        System.out.println("findAccountId = " + findAccountId);
        Account findAccount = accountRepository.findById(findAccountId).get();
        assertEquals(account.getEmail(), findAccount.getEmail());
        assertEquals(account.getName(), findAccount.getName());
    }

    private SignupReqDto buildSignupReqDto() {
        return SignupReqDto.builder()
                .email("test@email.com")
                .name("회원가입테스트")
                .password("pass123")
                .build();
    }

}