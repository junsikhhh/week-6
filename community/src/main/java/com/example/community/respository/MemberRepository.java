package com.example.community.respository;

import com.example.community.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); // 중복가입 방지
    Optional<Member> findByNickname(String nickname); // 중복 닉네임 방지
}
