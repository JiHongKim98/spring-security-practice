package com.example.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.jwt.jwt.CustomUserDetailService;
import com.example.jwt.jwt.JwtAuthenticationFilter;
import com.example.jwt.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtProvider jwtProvider;

	private final CustomUserDetailService userDetailService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// csrf 설정 (OFF)
			.csrf(AbstractHttpConfigurer::disable)

			// cors 설정 (OFF)
			.cors(AbstractHttpConfigurer::disable)

			// 접근 권한 설정
			.authorizeHttpRequests(auth ->
				auth.anyRequest().permitAll()
			)

			// session 정보: (STATELESS)
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)

			// Form 로그인 방식 사용 x (JWT 사용)
			.formLogin(AbstractHttpConfigurer::disable);

		// JWT 필터 등록 (request 시 filter 를 먼저 거쳐 인증 정보 및 권한 확인)
		http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider, userDetailService),
			UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
