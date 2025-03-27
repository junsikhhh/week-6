package com.example.community.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMemberRequestDto {
    @NotBlank
    private String nickname;
    private String profileImageUrl; // Optinoal
}
