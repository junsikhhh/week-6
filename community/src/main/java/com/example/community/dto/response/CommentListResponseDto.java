package com.example.community.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CommentListResponseDto {
    private List<CommentDto> comments;
    private boolean hasNext;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CommentDto {
        private Long commentId;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private AuthorDto author;

        private boolean owner;

        @Getter
        @Builder
        @AllArgsConstructor
        public static class AuthorDto {
            private String nickname;

            private String profileImageUrl;
        }
    }
}
