package com.example.community.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordChangeRequestDto {
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
}
