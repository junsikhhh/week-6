package com.example.community.controller;

import com.example.community.dto.request.LoginRequestDto;
import com.example.community.dto.request.SignupRequestDto;
import com.example.community.dto.response.ApiResponse;
import com.example.community.dto.response.TokenResponseDto;
import com.example.community.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Map<String, Long>>> signup(
            @Valid @RequestPart("request") SignupRequestDto request,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        Long userId = authService.signup(request, image);
        Map<String, Long> data = Map.of("user_id", userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("register_success", data));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponseDto>> login(@RequestBody LoginRequestDto request) {
        TokenResponseDto token = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("login_success", token));
    }
}
