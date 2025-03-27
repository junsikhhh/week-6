package com.example.community.controller;

import com.example.community.dto.request.UpdateUserRequestDto;
import com.example.community.dto.response.ApiResponse;
import com.example.community.dto.response.UserProfileResponseDto;
import com.example.community.security.CustomUserDetailsService;
import com.example.community.security.UserDetailsImpl;
import com.example.community.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponseDto>> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getMember().getId();
        UserProfileResponseDto response = userService.getUserInfo(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("fetch_success", response));
    }

    @PatchMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserProfileResponseDto>> updateUserInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart UpdateUserRequestDto request,
            @RequestPart(required = false) MultipartFile image) {
        Long userId = userDetails.getMember().getId();
        UserProfileResponseDto response = userService.updateUser(userId, request, image);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("update_success", response));
    }
}
