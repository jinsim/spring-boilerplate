package com.jinsim.springboilerplate.account.api;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.account.dto.*;
import com.jinsim.springboilerplate.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Slf4j
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;


    @PostMapping
    public ResponseEntity<SignInResDto> signIn(@RequestBody SignInReqDto requestDto) {
        SignInTokenDto tokenDto = accountService.signIn(requestDto);
        ResponseCookie responseCookie = generateRefreshTokenCookie(tokenDto);

        return ResponseEntity.ok()
                .header(SET_COOKIE, responseCookie.toString())
                .body(tokenDto.toSignInResDto());
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenDto> refresh(
            @RequestBody AccessTokenDto requestDto,
            @CookieValue(value = "refreshToken", required = false) Cookie rtCookie) {

        String refreshToken = rtCookie.getValue();

        AccessTokenDto resDto = accountService.refresh(requestDto, refreshToken);

        return ResponseEntity.ok()
                .body(resDto);
    }

    @PostMapping("/blacklist")
    public ResponseEntity<AccessTokenDto> signOut(@RequestBody AccessTokenDto requestDto) {
        AccessTokenDto resDto = accountService.signOut(requestDto);

        return ResponseEntity.ok()
                .body(resDto);
    }

    public ResponseCookie generateRefreshTokenCookie(SignInTokenDto tokenDto) {
        return ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
                .path("/") // 해당 경로 하위의 페이지에서만 쿠키 접근 허용. 모든 경로에서 접근 허용한다.
                .maxAge(TimeUnit.MILLISECONDS.toSeconds(tokenDto.getRefreshTokenValidationMs())) // 쿠키 만료 시기(초). 없으면 브라우저 닫힐 때 제거
                .secure(true) // HTTPS로 통신할 때만 쿠키가 전송된다.
                .httpOnly(true) // JS를 통한 쿠키 접근을 막아, XSS 공격 등을 방어하기 위한 옵션이다.
                .build();
    }
}
