package com.example.community.dto.response;

import com.example.community.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostCreateResponseDto {
    private Long postId;

    public static PostCreateResponseDto of(Post post) {
        return PostCreateResponseDto.builder()
                .postId(post.getId())
                .build();
    }
}
