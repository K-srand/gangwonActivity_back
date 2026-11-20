package com.multicampus.gangwonActivity.handler;

import com.multicampus.gangwonActivity.entity.CustomOAuth2User;
import com.multicampus.gangwonActivity.provider.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request
            , HttpServletResponse response
            , Authentication authentication
    ) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String userId = oAuth2User.getName();
        String token = jwtProvider.create(userId);
        long expirationTime = 3600;  // 토큰 만료 시간 (초 단위)

        log.info("OAuth2 로그인 성공: userId={}", userId); // 토큰은 로그에 남기지 않음

        response.sendRedirect("https://gangwonactivity.site/auth/oauth-response/" + token + "/" + expirationTime);
    }

}
