package com.jinsim.springboilerplate.user.repository;

import com.jinsim.springboilerplate.user.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);
}
