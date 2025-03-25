package com.example.community.controller;

import com.example.community.dto.request.UpdateMemberRequestDto;
import com.example.community.dto.response.ApiResponse;
import com.example.community.service.AuthService;
import com.example.community.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final AuthService authService;

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<Void>> updateProfile(@RequestBody UpdateMemberRequestDto request,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
    }
}
