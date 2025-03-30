package com.example.community.controller;

import com.example.community.dto.request.CommentCreateRequestDto;
import com.example.community.dto.request.CommentUpdateRequestDto;
import com.example.community.dto.response.ApiResponse;
import com.example.community.dto.response.CommentResponseDto;
import com.example.community.model.Member;
import com.example.community.security.UserDetailsImpl;
import com.example.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Validated @RequestBody CommentCreateRequestDto request
    ) {
        Member member = userDetails.getMember();
        CommentResponseDto response = commentService.createComment(request, member);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("comment_created", response));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long commentId,
            @Validated @RequestBody CommentUpdateRequestDto request
    ) {
        Member member = userDetails.getMember();
        CommentResponseDto response = commentService.updateComment(commentId, request, member);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("comment_updated", response));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long commentId
    ) {
        Member member = userDetails.getMember();
        commentService.deleteComment(commentId, member);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("comment_deleted"));
    }
}
