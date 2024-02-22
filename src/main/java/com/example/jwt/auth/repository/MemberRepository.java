package com.example.jwt.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwt.auth.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUsername(String username);
}
