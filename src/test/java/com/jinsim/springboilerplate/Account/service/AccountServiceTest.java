package com.jinsim.springboilerplate.Account.service;

import com.jinsim.springboilerplate.Account.domain.Account;
import com.jinsim.springboilerplate.Account.dto.SignupReqDto;
import com.jinsim.springboilerplate.Account.dto.UpdateAccountReqDto;
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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void 회원가입_성공() {
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
        Account findAccount = accountRepository.findById(findAccountId).get();
        assertEquals(account.getEmail(), findAccount.getEmail());
        assertEquals(account.getName(), findAccount.getName());
    }

    @Test
    void 회원수정_성공() {
        // given
        UpdateAccountReqDto requestDto = buildUpdateAccountReqDto();
        Account newAccount = requestDto.toEntity();
        Account oldAccount = buildSignupReqDto().toEntity();
        Long accountId = 1L;
        ReflectionTestUtils.setField(oldAccount, "id", accountId);

        // mock
        given(accountRepository.findById(accountId)).willReturn(Optional.ofNullable(oldAccount));

        // when
        accountService.update(accountId, requestDto);

        // then
        Account findAccount = accountRepository.findById(accountId).get();
        assertEquals(newAccount.getEmail(), findAccount.getEmail());
        assertEquals(newAccount.getName(), findAccount.getName());
    }

    @Test
    void 회원삭제_성공() {
        // given
        Account oldAccount = buildSignupReqDto().toEntity();
        Long accountId = 1L;
        ReflectionTestUtils.setField(oldAccount, "id", accountId);

        // mock
        given(accountRepository.findById(accountId)).willReturn(Optional.ofNullable(oldAccount));

        // when
        accountService.delete(accountId);

        // then
        verify(accountRepository, atLeastOnce()).delete(any(Account.class));
    }

    private SignupReqDto buildSignupReqDto() {
        return SignupReqDto.builder()
                .email("test@email.com")
                .name("회원가입테스트")
                .password("pass123")
                .build();
    }

    private UpdateAccountReqDto buildUpdateAccountReqDto() {
        return UpdateAccountReqDto.builder()
                .email("newtest@email.com")
                .name("회원수정테스트")
                .build();
    }

}