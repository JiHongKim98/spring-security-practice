package com.example.jwt.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

// 권한 테스트용 MemberController
@Slf4j
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

	// Member name 추출
	@GetMapping("/name")
	public String getUsername() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("username => {}", username);

		return username;
	}

	// Member 권한 추출
	@GetMapping("/role")
	public List<String> getRoles() {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext()
			.getAuthentication()
			.getAuthorities();

		List<String> memberAuthorities = authorities.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());
		log.info("memberAuthorities => {}", memberAuthorities);

		return memberAuthorities;
	}
}
