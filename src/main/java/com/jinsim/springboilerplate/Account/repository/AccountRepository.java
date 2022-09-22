package com.jinsim.springboilerplate.Account.repository;

import com.jinsim.springboilerplate.Account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);
}
