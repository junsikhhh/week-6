package com.example.community.dto.response;

import com.example.community.model.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime createdAt;
    private int likeCount;
    private int viewCount;
    private int commentCount;

    public static PostListResponseDto fromEntity(Post post) {
        return PostListResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .build();
    }
}
