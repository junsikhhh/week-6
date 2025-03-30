package com.example.community.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostListResponseDto {
    private List<PostSummary> posts;
    private boolean hasNext;

    @Getter
    @Builder
    public static class PostSummary {
        private Long postId;
        private String title;
        private int likeCount;
        private int viewCount;
        private int commentCount;
        private LocalDateTime createdAt;
        private AuthorInfo author;
    }

    @Getter
    @Builder
    public static class AuthorInfo {
        private String nickname;
        private String profileImageUrl;
    }
}
