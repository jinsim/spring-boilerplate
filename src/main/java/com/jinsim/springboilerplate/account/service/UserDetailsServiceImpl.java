package com.jinsim.springboilerplate.account.service;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    // username 대신 PK 값을 넘겨줘도 된다.
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        Account account = accountRepository.findById(Long.parseLong(accountId)).orElseThrow(
                () -> new UsernameNotFoundException("해당 아이디를 가진 계정이 없습니다. accountId = " + accountId));

        return User.builder()
                .username(accountId)
                .password(account.getPassword())
                .build();
    }
}
