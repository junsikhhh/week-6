package com.example.community.dto.response;

import com.example.community.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private AuthorInfo author;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class AuthorInfo {
        private String nickname;
        private String profileImageUrl;
    }
}
