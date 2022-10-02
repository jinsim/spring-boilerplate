package com.jinsim.springboilerplate.account.service;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.account.dto.LoginReqDto;
import com.jinsim.springboilerplate.account.dto.LoginResDto;
import com.jinsim.springboilerplate.account.dto.SignupReqDto;
import com.jinsim.springboilerplate.account.dto.UpdateAccountReqDto;
import com.jinsim.springboilerplate.account.exception.EmailDuplicationException;
import com.jinsim.springboilerplate.account.repository.AccountRepository;
import com.jinsim.springboilerplate.config.jwt.JwtProvider;
import com.jinsim.springboilerplate.config.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final RedisService redisService;

    // 조회시에 readOnly를 켜주면 flush가 일어나지 않아서 성능의 이점을 가질 수 있다.
    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디를 가진 계정이 없습니다."));
    }

    @Transactional(readOnly = true)
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 메일을 가진 계정이 없습니다."));
    }

    @Transactional(readOnly = true)
    public boolean isExistedEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    public Long signup(SignupReqDto requestDto) {
        if (isExistedEmail(requestDto.getEmail())) {
            throw new EmailDuplicationException(requestDto.getEmail());
        }

        String encodedPassword = encodePassword(requestDto.getPassword());
        Account account = accountRepository.save(requestDto.toEntity(encodedPassword));
        // JPA에서 em.persist를 하면, 영속성 컨텍스트에 멤버 객체를 올린다.
        // 그때 영속성 컨텍스트에서는 id값이 key가 된다. (DB pk랑 매핑한 게 key가 됨)
        // @GeneratedValue를 세팅하면 id값이 항상 들어가있는 것이 보장이 된다. (em.persist 할 때)
        // 왜냐하면, 영속성 컨텍스트에 값을 넣어야 하는데, key value 구조를 채우기 위해서.
        // 따라서 아직 DB에 들어가지 않았는데도 id가 생성되어 있다.
        return account.getId();
    }

    // Account를 반환하게 되면, 변경하는 Command와 조회하는 Query가 분리되지 않는다.
    public void update(Long accountId, UpdateAccountReqDto requestDto) {
        Account account = accountRepository.findById(accountId).get();
        account.updateMyAccount(requestDto);
    }

    public void delete(Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        accountRepository.delete(account);
    }

    public LoginResDto login(LoginReqDto requestDto) {
        // 회원 정보가 존재하는지 확인
        Account account = findByEmail(requestDto.getEmail());

        // password가 일치하는지 확인
        checkPassword(requestDto.getPassword(), account.getPassword());

        String accessToken = jwtProvider.generateAccessToken(account.getId(), account.getEmail());
        String refreshToken = jwtProvider.generateRefreshToken();

        redisService.setData(String.valueOf(account.getId()), refreshToken, jwtProvider.getRefreshTokenValidationMs());
        return new LoginResDto(account.getEmail(), accessToken, refreshToken);
    }

    public void checkPassword(String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new RuntimeException("비밀번호가 다릅니다.");
        }
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}