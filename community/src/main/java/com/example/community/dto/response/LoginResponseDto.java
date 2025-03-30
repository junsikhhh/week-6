package com.example.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private String accessToken;
    private UserInfo userInfo;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserInfo {
        private Long userId;
        private String profileImageUrl;
    }
}