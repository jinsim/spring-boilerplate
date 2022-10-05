package com.jinsim.springboilerplate.account.service;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Spring Security에서 유저의 정보를 가져오는 인터페이스
// loginProcess.do 가 찾아오는 클래스다.
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    // username 대신 PK 값을 넘겨줘도 된다.
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        Account account = accountRepository.findById(Long.parseLong(accountId)).orElseThrow(
                () -> new UsernameNotFoundException("해당 아이디를 가진 계정이 없습니다. accountId = " + accountId));

        // UserDetails를 반환한다. User는 UserDetails를 상속하는 클래스이다.
        return User.builder()
                .username(accountId)
                .password(account.getEncodedPassword())
                .roles("USER")
                .build();
    }
}
