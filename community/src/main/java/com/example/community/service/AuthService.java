package com.example.community.service;

import com.example.community.dto.request.LoginRequestDto;
import com.example.community.dto.request.SignupRequestDto;
import com.example.community.dto.response.TokenResponseDto;
import com.example.community.jwt.JwtProvider;
import com.example.community.model.Member;
import com.example.community.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public Long signup(SignupRequestDto request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if (memberRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setNickname(request.getNickname());
        member.setProfileImageUrl(request.getProfileImageUrl());

        return memberRepository.save(member).getId();
    }

    public TokenResponseDto login(LoginRequestDto request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("계정을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtProvider.generateToken(member.getEmail());
        return new TokenResponseDto(token);
    }
}
