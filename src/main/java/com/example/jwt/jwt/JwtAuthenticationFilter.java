package com.example.jwt.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	private final CustomUserDetailService userDetailService;

	// TODO: 예외처리 로직 추가
	@Override
	protected void doFilterInternal(
		HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		// Authorization 헤더 검증
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			log.info("토큰 미보유");
			filterChain.doFilter(request, response);
			return;
		}

		// access token validate
		String accessToken = header.split(" ")[1];
		if (!jwtProvider.isValidToken(accessToken)) {
			log.info("유효하지 않은 토큰");
			filterChain.doFilter(request, response);
			return;
		}

		String username = jwtProvider.getUsername(accessToken);
		UserDetails userDetails = userDetailService.loadUserByUsername(username);

		// Spring Security 인증 토큰 생성 및 설정
		UsernamePasswordAuthenticationToken authToken =
			new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		// 세션에 인증 토큰 등록
		SecurityContextHolder.getContext().setAuthentication(authToken);

		filterChain.doFilter(request, response);
	}
}
