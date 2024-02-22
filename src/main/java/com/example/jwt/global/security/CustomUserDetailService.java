package com.example.jwt.global.security;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jwt.auth.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final MemberRepository memberRepository;

	// 참고: key 값을 "#p0" 로 가능하지만 컴파일 옵션에 `-parameters` 을 추가해서 key 값을 "#파라미터명" 으로 사용하는 것이 좋다.
	// 		`-parameters` 옵션 없이 "#파라미터명" 으로 사용하면 "Null key returned for cache operation" 오류 발생.
	@Cacheable(value = "memberCacheStore", key = "#username")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Caching 된 username 이 아님!");
		return memberRepository.findByUsername(username)
			.map(UserDetails.class::cast)
			.orElseThrow(() -> new UsernameNotFoundException("not found error"));
	}
}
