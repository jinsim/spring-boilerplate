package com.jinsim.springboilerplate.user.service;

import com.jinsim.springboilerplate.user.domain.Account;
import com.jinsim.springboilerplate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;

    public Account findById(Long id) {
        return userRepository.findById(id).get();
    }

    public Long signup(Account user) {
        userRepository.save(user);
        // JPA에서 em.persist를 하면, 영속성 컨텍스트에 멤버 객체를 올린다.
        // 그때 영속성 컨텍스트에서는 id값이 key가 된다. (DB pk랑 매핑한 게 key가 됨)
        // @GeneratedValue를 세팅하면 id값이 항상 들어가있는 것이 보장이 된다. (em.persist 할 때)
        // 왜냐하면, 영속성 컨텍스트에 값을 넣어야 하는데, key value 구조를 채우기 위해서.
        // 따라서 아직 DB에 들어가지 않았는데도 id가 생성되어 있다.
        return user.getId();
    }
}
