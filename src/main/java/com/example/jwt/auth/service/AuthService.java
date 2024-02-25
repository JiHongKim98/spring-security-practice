package com.example.jwt.auth.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jwt.auth.dto.response.LoginResponseDto;
import com.example.jwt.auth.dto.response.MemberInfoResponseDto;
import com.example.jwt.auth.entity.Member;
import com.example.jwt.auth.entity.Role;
import com.example.jwt.auth.repository.MemberRepository;
import com.example.jwt.auth.util.JwtProvider;
import com.example.jwt.global.exception.DuplicateException;
import com.example.jwt.global.exception.InvalidPasswordException;
import com.example.jwt.global.exception.NotFoundMemberException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	@Transactional
	public void signIn(String username, String password) {
		memberRepository.findByUsername(username).ifPresent(v -> {
			throw new DuplicateException("이미 존재하는 username 입니다.");
		});

		// 비밀번호를 해싱하여 저장
		String encodedPassword = passwordEncoder.encode(password);
		Member member = Member.builder()
			.username(username)
			.password(encodedPassword)
			.role(Role.MEMBER)
			.build();

		memberRepository.save(member);
	}

	public LoginResponseDto login(String username, String password) {
		Member member = memberRepository.findByUsername(username).orElseThrow(() ->
			new NotFoundMemberException("존재하지 않는 사용자 입니다.")
		);

		boolean result = passwordEncoder.matches(password, member.getPassword());
		if (!result) {
			log.info("로그인 오류 - 일치하지 않는 비밀번호");
			throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
		}

		String accessToken = jwtProvider.generateAccessToken(member.getUsername(), member.getRole().name());

		return LoginResponseDto.from(accessToken);
	}

	public MemberInfoResponseDto me() {
		SecurityContext getContext = SecurityContextHolder.getContext();

		String username = getContext
			.getAuthentication()
			.getName();
		List<String> memberAuthorities = getContext
			.getAuthentication()
			.getAuthorities()
			.stream()
			.map(GrantedAuthority::getAuthority)
			.toList();

		return MemberInfoResponseDto.from(username, memberAuthorities);
	}

}
