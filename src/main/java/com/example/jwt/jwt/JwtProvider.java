package com.example.jwt.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private final Key SECRET_KEY;

	@Value("${jwt.access.exp}")
	private Long ACCESS_EXPIRE_TIME;

	public JwtProvider(@Value("${jwt.secret-key}") String SECRET_KEY) {
		// HASH MAC 알고리즘을 위한 KEY 생성
		byte[] keyByte = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
		this.SECRET_KEY = Keys.hmacShaKeyFor(keyByte);
	}

	/**
	 * JWT Claim
	 * "username": "abc"
	 * "role": "MEMBER"
	 * "iat": 1234567890
	 * "exp": 1234567890
	 */
	public String generateAccessToken(String username, String role) {
		Claims claims = Jwts.claims();
		claims.put("username", username);
		claims.put("role", role);

		return generateToken(claims, ACCESS_EXPIRE_TIME);
	}

	public Boolean isValidToken(String token) {
		return parseClaims(token)
			.getExpiration()
			.after(new Date());
	}

	public String getUsername(String token) {
		return parseClaims(token)
			.get("username", String.class);
	}

	public String getRole(String token) {
		return parseClaims(token)
			.get("role", String.class);
	}

	/**
	 * JWT 를 파싱하여 Claim 을 추출하기 위한 메소드
	 */
	private Claims parseClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(SECRET_KEY)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	/**
	 * JWT 생성
	 */
	private String generateToken(Claims claims, Long expired) {
		long now = System.currentTimeMillis();

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(now))
			.setExpiration(new Date(now + expired))
			.signWith(SECRET_KEY, SignatureAlgorithm.HS256)
			.compact();
	}
}
