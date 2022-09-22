package com.jinsim.springboilerplate.account.repository;

import com.jinsim.springboilerplate.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);
}
