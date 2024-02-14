package com.example.jwt.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jwt.dto.response.LoginResponseDto;
import com.example.jwt.entity.Member;
import com.example.jwt.entity.Role;
import com.example.jwt.jwt.JwtProvider;
import com.example.jwt.repository.MemberRepository;

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
			throw new IllegalStateException("이미 존재하는 username 입니다."); // TODO: 예외 처리 보강
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

	// TODO: redis 추가하여 member 캐싱 추가
	public LoginResponseDto login(String username, String password) {
		Member member = memberRepository.findByUsername(username).orElseThrow(() ->
			new IllegalArgumentException("username 을 확인 해주세요.")
		);

		boolean result = passwordEncoder.matches(password, member.getPassword());
		if (!result) {
			log.info("로그인 오류 - 일치하지 않는 비밀번호");
			throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
		}

		String accessToken = jwtProvider.generateAccessToken(member.getUsername(), member.getRole().name());

		return LoginResponseDto.from(accessToken);
	}
}
