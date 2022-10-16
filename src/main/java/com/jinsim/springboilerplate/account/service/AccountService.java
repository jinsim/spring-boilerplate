package com.jinsim.springboilerplate.account.service;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.account.dto.*;
import com.jinsim.springboilerplate.account.exception.AccountNotFoundException;
import com.jinsim.springboilerplate.account.exception.EmailDuplicationException;
import com.jinsim.springboilerplate.account.repository.AccountRepository;
import com.jinsim.springboilerplate.config.jwt.JwtProvider;
import com.jinsim.springboilerplate.config.redis.RedisService;
import com.jinsim.springboilerplate.config.jwt.exception.InvalidTokenException;
import com.jinsim.springboilerplate.config.redis.exception.RefreshTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final RedisService redisService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 조회시에 readOnly를 켜주면 flush가 일어나지 않아서 성능의 이점을 가질 수 있다.
    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("id", id));
    }

    @Transactional(readOnly = true)
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("email", email));
    }

    @Transactional(readOnly = true)
    public boolean isExistedEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    public Long signUp(SignUpReqDto requestDto) {
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
        account.updateMyAccount(requestDto.getEmail(), requestDto.getName());
    }

    public void delete(Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        accountRepository.delete(account);
    }

    public SignInTokenDto signIn(SignInReqDto requestDto) {
        // email과 password를 통해 UsernamePasswordAuthenticationToken를 생성한다.
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(
                requestDto.getEmail(), requestDto.getPassword());

        // AuthenticationManager를 구현한 ProviderManager를 생성한다.
        // ProviderManager는 데이터를 DaoAuthenticationProvider에 주입받아서 호출하고,
        // 해당 Provider 내부에 있는 authenticate에서 retrieveUser를 통해 DB에서의 User 비밀번호가 실제 비밀번호가 맞는지 비교한다.
        // 이때, DB에서 User를 가져오기 위해 UserDetailsServiceImpl에 있는 loadUserByUsername이 사용된다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String email = authentication.getName();

        String accessToken = jwtProvider.generateAccessToken(email);
        String refreshToken = jwtProvider.generateRefreshToken();
        Long refreshTokenValidationMs = jwtProvider.getRefreshTokenValidationMs();

        redisService.setData("RefreshToken:" + authentication.getName() , refreshToken, refreshTokenValidationMs);
        return new SignInTokenDto(requestDto.getEmail(), accessToken, refreshToken, refreshTokenValidationMs);
    }

    public AccessTokenDto refresh(AccessTokenDto requestDto, String refreshToken) {
        // 들어온 refreshToekn 검증
        if (!jwtProvider.validateToken(refreshToken)) {
            log.error("유효하지 않은 토큰입니다. {}", refreshToken);
            throw new InvalidTokenException("Refresh Token", refreshToken, "검증에 실패한 토큰입니다.");
        }

        // accessToken에서 Authentication 추출하기
        String accessToken = requestDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        // Redis의 RefreshToken을 가져오면서, 로그아웃된 사용자인 경우 예외 처리
        String findRefreshToken = redisService.getRefreshToken(authentication.getName())
                .orElseThrow(() -> new RefreshTokenNotFoundException(authentication.getName()));

        // 저장되어있던 refreshToken과 일치하는지 확인
        if (!refreshToken.equals(findRefreshToken)) {
            log.error("저장된 토큰과 일치하지 않습니다. {} {}", refreshToken, findRefreshToken);
            throw new InvalidTokenException("Refresh Token", refreshToken, "저장된 토큰과 일치하지 않습니다.");
        }

        // 토큰 생성을 위해 accessToken에서 Claims 추출
        String newAccessToken = jwtProvider.generateAccessToken(authentication.getName());

        return new AccessTokenDto(newAccessToken);
    }

    public AccessTokenDto signOut(AccessTokenDto requestDto) {
        // accessToken에서 Authentication 추출하기
        String accessToken = requestDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        // Redis의 RefreshToken을 가져오면서, 이미 로그아웃된 사용자인 경우 예외 처리
        String refreshToken = redisService.getRefreshToken(authentication.getName())
                .orElseThrow(() -> new RefreshTokenNotFoundException("RefreshToken:" + authentication.getName()));

        // AccessToken의 남은 시간 추출 후 BlackList에 저장
        Long remainingTime = jwtProvider.getRemainingTime(accessToken);
        redisService.setData("BlackList:" + accessToken, "signOut", remainingTime);
        redisService.deleteData("RefreshToken:" + authentication.getName());

        return new AccessTokenDto(accessToken);
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(String email, String password) {
//        Account account = accountRepository.findByEmail(email)
//                .orElseThrow(() -> new AccountNotFoundException("email", email));
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}