package com.example.community.dto.response;

import com.example.community.model.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDto {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;

    public static CommentResponseDto fromEntity(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .author(comment.getUser().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
