package com.jinsim.springboilerplate.domain.account.api;

import com.jinsim.springboilerplate.domain.account.dto.AccessTokenDto;
import com.jinsim.springboilerplate.domain.account.dto.SignInReqDto;
import com.jinsim.springboilerplate.domain.account.dto.SignInResDto;
import com.jinsim.springboilerplate.domain.account.dto.SignInTokenDto;
import com.jinsim.springboilerplate.domain.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Tag(name = "Auth API", description = "인증 API")
@Slf4j
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @Operation(summary = "로그인", description = "Access Token 과 Refresh Token 을 생성합니다.")
    @Parameters({
            @Parameter(name = "requestDto", description = "로그인 요청 객체"),
    })
    @PostMapping
    public ResponseEntity<SignInResDto> signIn(@RequestBody SignInReqDto requestDto) {
        SignInTokenDto tokenDto = accountService.signIn(requestDto);
        ResponseCookie responseCookie = generateRefreshTokenCookie(tokenDto);

        return ResponseEntity.ok()
                .header(SET_COOKIE, responseCookie.toString())
                .body(tokenDto.toSignInResDto());
    }

    @Operation(summary = "재인증", description = "Refresh Token 으로 Access Token 을 재발급합니다.")
    @Parameters({
            @Parameter(name = "requestDto", description = "Access Token 객체"),
            // @CookieValue 보고 자동으로 만들어준다.
//            @Parameter(name = "rtCookie", description = "Refresh Token 값", required = false, in = ParameterIn.COOKIE),
    })
    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenDto> refresh(@RequestBody AccessTokenDto requestDto,
                                                  @CookieValue(value = "refreshToken", required = false) Cookie rtCookie) {

        String refreshToken = rtCookie.getValue();

        AccessTokenDto resDto = accountService.refresh(requestDto, refreshToken);

        return ResponseEntity.ok()
                .body(resDto);
    }

    @Operation(summary = "로그아웃", description = "Refresh Token 을 BlackList 로 설정합니다.")
    @Parameters({
            @Parameter(name = "requestDto", description = "Access Token 객체"),
    })
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
