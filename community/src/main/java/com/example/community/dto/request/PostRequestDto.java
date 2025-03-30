package com.example.community.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
