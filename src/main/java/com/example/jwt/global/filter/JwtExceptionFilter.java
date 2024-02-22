package com.example.jwt.global.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

	/**
	 * Filter chain 실행 중, 발생할 수 있는 Exception 을 처리하기 위한 JwtExceptionFilter class
	 * 즉, JWT 인증 과정에서 발생하는 Exception 을 처리
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		log.info("JwtExceptionFilter :: Call");

		try {
			filterChain.doFilter(request, response);
		} catch (RuntimeException ex) {
			log.info("JwtExceptionFilter :: JWT Exception");

			filterChain.doFilter(request, response);
		}
	}
}
