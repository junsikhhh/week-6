package com.example.community.dto.response;

import com.example.community.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponseDto {
    private Long id;
    private String nickname;
    private String profileImageUrl;

    public static UserProfileResponseDto fromEntity(User user) {
        return new UserProfileResponseDto(
                user.getId(),
                user.getNickname(),
                user.getProfileImageUrl()
        );
    }

}
