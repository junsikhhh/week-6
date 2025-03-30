package com.example.community.dto.response;

import com.example.community.model.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostDetailResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private int likeCount;
    private int viewCount;
    private int commentCount;
    private LocalDateTime createdAt;

    private AuthorDto author;

    private boolean isOwner;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class AuthorDto {
        private String nickname;
        private String profileImageUrl;
    }
}
