package com.example.community.service;

import com.example.community.dto.request.UpdateMemberRequestDto;
import com.example.community.model.Member;
import com.example.community.model.User;
import com.example.community.respository.MemberRepository;
import com.example.community.respository.UserRepository;
//import com.example.community.security.JwtUtil;
import com.example.community.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MemberRepository memberRepository;
//
//    // ✅ 회원 정보 조회
//    public Optional<User> getUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
    // 회원 정보 수정 (닉네임, 프로필 사진)
    @Transactional
    public void updateProfile(String email, UpdateMemberRequestDto request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저가 존재하지 않습니다."));

    }
//
//    // ✅ 비밀번호 변경
//    @Transactional
//    public void changePassword(Long userId, String oldPassword, String newPassword) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
//
//        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
//            throw new IllegalArgumentException("기존 비밀번호가 틀렸습니다.");
//        }
//
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//    }
//
//    // ✅ 회원 탈퇴
//    @Transactional
//    public void deleteUser(Long userId) {
//        userRepository.deleteById(userId);
//    }
}
