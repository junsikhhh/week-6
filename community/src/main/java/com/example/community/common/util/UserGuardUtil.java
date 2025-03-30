package com.example.community.common.util;

import com.example.community.model.Member;
import com.example.community.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGuardUtil {
    private final MemberRepository memberRepository;

    public Member getActiveUser(Long userId) {
        return memberRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new IllegalArgumentException("탈퇴했거나 존재하지 않는 사용자입니다."));
    }
}
