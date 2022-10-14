package com.jinsim.springboilerplate.config.jwt;

import com.jinsim.springboilerplate.account.service.UserDetailsServiceImpl;
import com.jinsim.springboilerplate.config.jwt.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private final Long accessTokenValidationMs = 30 * 60 * 1000L;

    private final Long refreshTokenValidationMs = 15 * 24 * 60 * 60 * 1000L;

    public Long getRefreshTokenValidationMs() { // Redis에 저장 시 사용
        return refreshTokenValidationMs;
    }

    // AccessToken 생성
    public String generateAccessToken(String accountId, String email) {

        // Registered claim. 토큰에 대한 정보들이 담겨있는 클레임. 이미 이름이 등록되어있다.
        Claims claims = Jwts.claims()
                // 토큰 제목(sub). 고유 식별자를 넣는다.
                .setSubject(accountId)
                // 발급 시간(iat)
                .setIssuedAt(new Date())
                // 만료 시간(exp)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidationMs));

        // Private claim. 서버-클라이언트간의 협의하에 사용되는 클레임.
        claims.put("email", email);

        return Jwts.builder()
                // 헤더의 타입(typ)을 jwt로 설정
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                // 생성된 Claims 등록
                .setClaims(claims)
                // 서명에 사용할 키와 해싱 알고리즘(alt) 설정
                .signWith(getSignKey(secretKey), SignatureAlgorithm.HS256)
                // JWT 생성
                .compact();
    }

    // RefreshToken 생성
    public String generateRefreshToken() {

        Claims claims = Jwts.claims()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidationMs));

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSignKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    // String인 secretKey를 byte[]로 변환 후 반환한다.
    private Key getSignKey(String secretKey) {
        // Signature에 Hmac Sha 256 알고리즘이 사용된다.
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey(secretKey))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다. {}", e.toString());
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("잘못된 형식의 토큰입니다. {}", e.toString());
            return false;
        } catch (MalformedJwtException e) {
            log.error("잘못된 구조의 토큰입니다. {}", e.toString());
            return false;
        } catch (SignatureException e) {
            log.error("잘못 서명된 토큰입니다. {}", e.toString());
            return false;
        } catch (IllegalArgumentException e) {
            log.error("잘못 생성된 토큰입니다. {}", e.toString());
            return false;
        }
    }

    // JWT payload를 복호화해서 반환(Public 해도 되나?)
    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder() // JwtParserBuilder 인스턴스 생성
                    .setSigningKey(getSignKey(secretKey)) // JWT Signature 검증을 위한 SecretKey 설정
                    .build() // Thread-Safe한 JwtParser를 반환하기 위해 build 호출
                    .parseClaimsJws(token) // Claim(Payload) 파싱
                    .getBody();
        } catch (ExpiredJwtException e) {
            // 만료된 토큰이어도 refresh token 검증 후 재발급할 수 있또록 claims 반환
            return e.getClaims();
        } catch (Exception e) {
            // 다른 예외인 경우 throw
            log.error("유효하지 않은 토큰입니다. {}", e.toString());
            throw new InvalidTokenException("Access Token", token, e.toString());
        }
    }

    // JWT Claims으로 User 객체를 생성하여 Authentication 객체를 반환
    public Authentication getAuthentication(String token) {

        // JWT에서 Claims 가져오기
        Claims claims = getClaims(token);
        String email = (String) claims.get("email");
        String userPk = claims.getSubject();

        if (userPk == null || email == null) {
            log.error("권한 정보가 없는 토큰입니다. {}", token);
            throw new InvalidTokenException("Access Token", token, "권한 정보가 없는 토큰입니다.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Long getRemainingTime(String token) {
        Date expiration = getClaims(token).getExpiration();
        Date now = new Date();
        return expiration.getTime() - now.getTime();
    }

}