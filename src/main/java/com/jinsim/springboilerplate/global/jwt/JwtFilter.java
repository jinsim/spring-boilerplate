package com.jinsim.springboilerplate.global.jwt;

import com.jinsim.springboilerplate.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


// Request 이전에 한번만 실행되는 필터
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final RedisService redisService;

    // 실제 필터링 로직을 수행하며, JWT의 인증 정보를 현재 쓰레드의 Security Context에 저장한다.
    // 단, 실제 DB를 조회하는 것이 아니라 Token 내에 저장된 id를 가져오는 과정이므로 탈퇴 등으로 인한 상황은 Service에서 추가적으로 고려해야한다.
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // HTTP Request Header에서 Token 값 가져오기
        String jwt = resolveToken(request);

        // 유효성 검사 후, 정상적인 토큰인 경우 Security Context에 저장한다.
        if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {

            // BlackList에 존재하는 토큰으로 요청이 온 경우.
            Optional<String> isBlackList = redisService.getBlackList(jwt);
            isBlackList.ifPresent(t -> {
                throw new RuntimeException("이미 로그아웃된 토큰입니다.");
            });

            Authentication authentication = jwtProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    // HTTP Request Header에서 Token 값 가져오기
    public String resolveToken(HttpServletRequest request) {
        // Header의 Authorization에 JWT이 담겨 올 것이다.
        String authorization = request.getHeader("Authorization");
        // Authorization에 들어있는 문자열이 공백이 아니고 Bearer로 시작하는지 검증한다.
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            // Token 값만 추출한다.
            return authorization.substring(7);
        }
        return null;
    }
}
