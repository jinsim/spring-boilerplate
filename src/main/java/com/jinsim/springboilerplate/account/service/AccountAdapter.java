package com.jinsim.springboilerplate.account.service;

import com.jinsim.springboilerplate.account.domain.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AccountAdapter extends User {

    private Account account;

    public AccountAdapter(Account account) {
        // Account에 Role 추가 후에 변경할 것
        super(account.getEmail(), account.getEncodedPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }

}