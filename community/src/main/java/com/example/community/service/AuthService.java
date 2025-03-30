package com.example.community.service;

import com.example.community.dto.request.LoginRequestDto;
import com.example.community.dto.request.SignupRequestDto;
import com.example.community.dto.response.LoginResponseDto;
import com.example.community.exception.ImageUploadException;
import com.example.community.global.enums.UploadType;
import com.example.community.jwt.JwtProvider;
import com.example.community.model.Member;
import com.example.community.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final FileService fileService;

    @Transactional
    public Long signup(SignupRequestDto request, MultipartFile image) {
        String profileImageUrl = "/images/profile/default-image.png"; // 기본 경로 (image가 null이면)

        if (memberRepository.existsByEmailAndDeletedFalse(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (memberRepository.existsByNicknameAndDeletedFalse(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        if (image != null && !image.isEmpty()) {
            try {
                profileImageUrl = fileService.saveImage(image, UploadType.PROFILE);

            } catch (Exception e) {
                throw new ImageUploadException("이미지 저장 실패");
            }
        }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .profileImageUrl(profileImageUrl)
                .build();

        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto request) {
        Member member = memberRepository.findByEmailAndDeletedFalse(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("계정을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.generateToken(member.getId());
        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .userInfo(LoginResponseDto.UserInfo.builder()
                        .userId(member.getId())
                        .profileImageUrl(member.getProfileImageUrl())
                        .build())
                .build();
    }
}
