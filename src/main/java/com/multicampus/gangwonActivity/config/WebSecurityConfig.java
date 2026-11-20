package com.multicampus.gangwonActivity.config;


import com.multicampus.gangwonActivity.filter.JwtAuthenticationFilter;
import com.multicampus.gangwonActivity.handler.OAuth2SuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;

@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final DefaultOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                )
                .csrf(CsrfConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true))
                .authorizeHttpRequests(request -> request
                        // ---------- 공개: 로그인 없이 사용하는 화면 ----------
                        .requestMatchers(
                                "/",
                                "/error",                   // 공개 API에서 난 에러 응답이 403으로 바뀌지 않도록
                                "/resources/**",            // gangwondo.json 등 정적 리소스
                                "/oauth2/**",               // 카카오 로그인 콜백
                                "/api/v1/auth/**"           // 회원가입, 로그인, 이메일 인증, 아이디·비밀번호 찾기
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/getjson/getplace", // 메인 장소 목록
                                "/api/v1/getmap",           // 네이버 지도 스크립트 주소
                                "/api/v1/weather/data",     // 메인 날씨 달력
                                "/api/v1/recommend/*",      // 메인 추천 코스 상세
                                "/api/v1/board/",           // 커뮤니티 목록 (읽기 전용)
                                "/api/v1/board/best",
                                "/api/v1/board/notice"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/getjson/getplacetitle",
                                "/api/v1/getjson/getplacecat",
                                "/api/v1/recommend"         // 메인 추천 코스
                        ).permitAll()

                        // ---------- 관리자 전용 ----------
                        .requestMatchers(
                                "/api/v1/admin/**",
                                "/api/v1/getjson/save",     // 스케줄러 작업 (수동 실행은 관리자만)
                                "/api/v1/getjson/rating",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/report/",          // 신고 목록
                                "/api/v1/weather"           // 스케줄러 작업 (수동 실행은 관리자만)
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/report/delete/**").hasRole("ADMIN")

                        // ---------- 나머지는 로그인 필요 ----------
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint -> endpoint.baseUri("/api/v1/auth/oauth2"))
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                        .userInfoEndpoint(enpoint -> enpoint.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new FailedAuthenticationEntryPoint())
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

    //CORS 정책허용
    @Bean
    protected CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("https://gangwonactivity.site", "https://13.209.112.179")); // 요청을 보낼 출처 설정
        corsConfiguration.setAllowedMethods(Arrays.asList("HEAD", "PUT", "POST", "GET", "OPTIONS", "DELETE", "PATCH")); // 허용할 HTTP 메서드 설정
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // 허용할 요청 헤더 설정
        corsConfiguration.setAllowCredentials(true); // 자격 증명 허용 설정
        corsConfiguration.setExposedHeaders(Arrays.asList("Authorization", "Authorization-refresh")); // 노출할 응답 헤더 설정
        corsConfiguration.setMaxAge(3600L); // pre-flight 요청 캐시 시간 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        //다른 정책 만들고 싶으면 위에 corsConfiguration하나 더 생성하고
//        source.registerCorsConfiguration("api/v2/**", corsConfiguration);
        return source;
    }

    static class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"code\":\"NP\", \"message\": \"Do not have permission.\"}");

        }
    }

}
