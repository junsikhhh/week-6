package com.example.community.controller;

import com.example.community.dto.request.PostRequestDto;
import com.example.community.dto.response.*;
import com.example.community.model.Member;
import com.example.community.security.UserDetailsImpl;
import com.example.community.service.CommentService;
import com.example.community.service.LikeService;
import com.example.community.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<PostCreateResponseDto>> createPost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestPart("request") PostRequestDto request,
            @RequestPart(value = "image", required = false) MultipartFile image
            ) {
        Member member = userDetails.getMember();
        PostCreateResponseDto response = postService.createPost(request, image, member);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("post_created", response));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponseDto>> getPostDetail(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long postId
    ) {
        Member member = userDetails.getMember();
        PostDetailResponseDto response = postService.getPostDetail(postId, member);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("post_detail", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PostListResponseDto>> getPostList(
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PostListResponseDto response = postService.getPostList(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("post_list", response));
    }

    @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Map<String, Long>>> updatePost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long postId,
            @Valid @RequestPart("request") PostRequestDto request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        Member member = userDetails.getMember();
        Long updatedId = postService.updatePost(postId, member, request, image);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("post_updated", Map.of("postId", updatedId)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long postId
    ) {
        Member member = userDetails.getMember();
        postService.deletePost(postId, member);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("post_deleted"));
    }

    // 게시글 댓글 목록 조회
    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<CommentListResponseDto>> getComments(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long postId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Member member = userDetails.getMember();
        CommentListResponseDto response = commentService.getComments(postId, member, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("comment_list", response));
    }

    // 게시글 좋아요
    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<Map<String, Object>>> toggleLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long postId
    ) {
        Member member = userDetails.getMember();
        Map<String, Object> result = likeService.toggleLike(postId, member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("like_toggled", result));
    }

}
