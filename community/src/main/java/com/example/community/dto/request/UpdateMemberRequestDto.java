package com.example.community.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateMemberRequestDto {
    @NotBlank
    private String nickname;

    private String profileImageUrl; // Optinoal
}
