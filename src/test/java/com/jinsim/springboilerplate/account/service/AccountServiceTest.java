package com.jinsim.springboilerplate.account.service;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.account.dto.SignUpReqDto;
import com.jinsim.springboilerplate.domain.account.dto.UpdateAccountReqDto;
import com.jinsim.springboilerplate.domain.account.repository.AccountRepository;
import com.jinsim.springboilerplate.domain.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Spy // Mock하지 않은 메소드는 실제 메소드로 동작
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void 회원가입_성공() {
        // given
        SignUpReqDto requestDto = buildSignupReqDto();
        Account account = requestDto.toEntity(encodePassword(requestDto.getPassword()));
        Long accountId = 1L;
        ReflectionTestUtils.setField(account, "id", accountId);

        // mock
        given(accountRepository.save(any(Account.class))).willReturn(account);
        given(accountRepository.findById(accountId)).willReturn(Optional.ofNullable(account));

        // when
        Long findAccountId = accountService.signUp(requestDto);

        // then
        Account findAccount = accountRepository.findById(findAccountId).get();
        assertEquals(account.getEmail(), findAccount.getEmail());
        assertEquals(account.getName(), findAccount.getName());
        assertEquals(account.getEncodedPassword(), findAccount.getEncodedPassword());
    }

    @Test
    void 회원수정_성공() {
        // given
        UpdateAccountReqDto requestDto = buildUpdateAccountReqDto();
        Account newAccount = requestDto.toEntity();
        SignUpReqDto oldRequestDto = buildSignupReqDto();
        Account oldAccount = oldRequestDto.toEntity(encodePassword(oldRequestDto.getPassword()));
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
        SignUpReqDto oldRequestDto = buildSignupReqDto();
        Account oldAccount = oldRequestDto.toEntity(encodePassword(oldRequestDto.getPassword()));
        Long accountId = 1L;
        ReflectionTestUtils.setField(oldAccount, "id", accountId);

        // mock
        given(accountRepository.findById(accountId)).willReturn(Optional.ofNullable(oldAccount));

        // when
        accountService.delete(accountId);

        // then
        verify(accountRepository, atLeastOnce()).delete(any(Account.class));
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private SignUpReqDto buildSignupReqDto() {
        return SignUpReqDto.builder()
                .email("test@email.com")
                .name("회원가입테스트")
                .password("pass123!")
                .build();
    }

    private UpdateAccountReqDto buildUpdateAccountReqDto() {
        return UpdateAccountReqDto.builder()
                .email("newtest@email.com")
                .name("회원수정테스트")
                .build();
    }

}