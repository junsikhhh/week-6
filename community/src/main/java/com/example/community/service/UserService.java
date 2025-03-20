package com.example.community.service;

import com.example.community.model.User;
import com.example.community.respository.UserRepository;
//import com.example.community.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
////    private final JwtUtil jwtUtil;
//
//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtUtil = jwtUtil;
//    }
//
//    // ✅ 회원가입
//    @Transactional
//    public User registerUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 해싱
//        return userRepository.save(user);
//    }
//
//    // ✅ 로그인 (JWT 발급)
//    public String login(String email, String password) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("이메일이 존재하지 않습니다."));
//
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
//        }
//
//        return jwtUtil.generateToken(user.getEmail()); // JWT 토큰 발급
//    }
//
//    // ✅ 회원 정보 조회
//    public Optional<User> getUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    // ✅ 회원 정보 수정 (닉네임, 프로필 사진)
//    @Transactional
//    public User updateUser(Long userId, String nickname, String profileImage) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
//        user.setNickname(nickname);
//        user.setProfileImage(profileImage);
//        return userRepository.save(user);
//    }
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
