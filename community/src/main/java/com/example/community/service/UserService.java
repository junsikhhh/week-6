package com.example.community.service;

import com.example.community.common.util.UserGuardUtil;
import com.example.community.dto.request.UpdateUserRequestDto;
import com.example.community.dto.response.UserProfileResponseDto;
import com.example.community.exception.ImageUploadException;
import com.example.community.exception.NotFoundException;
import com.example.community.global.enums.UploadType;
import com.example.community.model.Member;
import com.example.community.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final MemberRepository memberRepository;
    private final UserGuardUtil userGuardUtil;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    // 회원 정보 조회
    @Transactional(readOnly = true)
    public UserProfileResponseDto getUserInfo(Long userId) {
        Member member = userGuardUtil.getActiveUser(userId);
        return UserProfileResponseDto.of(member);
    }

    // 회원 정보 수정 (닉네임, 프로필 사진)
    @Transactional
    public UserProfileResponseDto updateUser(Long userId, UpdateUserRequestDto request, MultipartFile newProfileImage) {
        Member member = userGuardUtil.getActiveUser(userId);

        // 닉네임 중복 검사(자기 자신 허용)
        if (!member.getNickname().equals(request.getNickname()) &&
        memberRepository.existsByNicknameAndDeletedFalse(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        String imageUrl = member.getProfileImageUrl();
        if (request.isImageDeleted()) {
            // 이미지 삭제 요청
            fileService.deleteImage(imageUrl, UploadType.PROFILE);
            imageUrl = "/images/profile/default-image.png";
        } else if (newProfileImage != null && !newProfileImage.isEmpty()) {
            // 새 이미지 업로드 요청
            fileService.deleteImage(imageUrl, UploadType.PROFILE); // 기존 이미지 삭제
            try {
                imageUrl = fileService.saveImage(newProfileImage, UploadType.PROFILE); // 새 이미지 저장
            } catch (IOException e) {
                throw new ImageUploadException("이미지 저장 실패");
            }
        }

        member.updateProfile(request.getNickname(), imageUrl);
        return UserProfileResponseDto.of(member);
    }

    // 비밀번호 변경
    @Transactional
    public void changePassword(Long userId, String newPassword) {
        Member member = userGuardUtil.getActiveUser(userId);

        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    // 회원 탈퇴
    @Transactional
    public void deleteAccount(Long userId) {
        Member member = userGuardUtil.getActiveUser(userId);
        member.softDelete();
        memberRepository.save(member);
    }
}
