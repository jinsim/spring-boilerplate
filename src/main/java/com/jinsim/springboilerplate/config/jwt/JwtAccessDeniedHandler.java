package com.jinsim.springboilerplate.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 자격정보는 있으나 접근 권한이 없는 경우 403 에러 반환
        // response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden Error");
        // body에 에러 설명 메시지를 추가하기 위해 JSONObject와 writer 사용
        log.error("Forbidden Error : {}", accessDeniedException.getMessage());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(new JSONObject()
                .put("message", accessDeniedException.getMessage()).toString());
    }
}
