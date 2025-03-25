package com.example.community.controller;

import com.example.community.dto.request.LoginRequestDto;
import com.example.community.dto.request.SignupRequestDto;
import com.example.community.dto.response.ApiResponse;
import com.example.community.dto.response.TokenResponseDto;
import com.example.community.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Map<String, Long>>> signup(@RequestBody SignupRequestDto request) {
        Long userId = authService.signup(request);
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
