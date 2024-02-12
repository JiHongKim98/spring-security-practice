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

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

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
				auth
					.requestMatchers("/api/v1/health/**").permitAll()
					.anyRequest().authenticated()
			)

			// session 정보: (STATELESS)
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)

			// Form 로그인 방식 사용 x (JWT 사용)
			.formLogin(AbstractHttpConfigurer::disable);

		return http.build();
	}
}
