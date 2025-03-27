package com.example.community.service;

import ch.qos.logback.classic.Logger;
import com.example.community.dto.request.UpdateUserRequestDto;
import com.example.community.dto.response.UserProfileResponseDto;
import com.example.community.exception.ImageUploadException;
import com.example.community.exception.UserNotFoundException;
import com.example.community.global.enums.UploadType;
import com.example.community.model.Member;
import com.example.community.respository.MemberRepository;
//import com.example.community.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final MemberRepository memberRepository;
    private final FileService fileService;

    // 회원 정보 조회
    public UserProfileResponseDto getUserInfo(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return UserProfileResponseDto.of(member);
    }

    // 회원 정보 수정 (닉네임, 프로필 사진)
    @Transactional
    public UserProfileResponseDto updateUser(Long userId, UpdateUserRequestDto request, MultipartFile newProfileImage) {
        Member member = memberRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

        // 닉네임 중복 검사(자기 자신 허용)
        if (!member.getNickname().equals(request.getNickname()) &&
        memberRepository.existsByNicknameAndDeletedFalse(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        String imageUrl = member.getProfileImageUrl();
        log.info("imageDeleted: {}", request.isImageDeleted());
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
