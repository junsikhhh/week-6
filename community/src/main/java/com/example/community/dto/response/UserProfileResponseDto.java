package com.example.community.dto.response;

import com.example.community.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponseDto {
    private String email;
    private String nickname;
    private String profileImageUrl;

    public static UserProfileResponseDto of(Member member) {
        return UserProfileResponseDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }
}
