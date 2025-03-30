package com.example.community.service;

import com.example.community.common.util.UserGuardUtil;
import com.example.community.exception.NotFoundException;
import com.example.community.model.Like;
import com.example.community.model.Member;
import com.example.community.model.Post;
import com.example.community.respository.LikeRepository;
import com.example.community.respository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public Map<String, Object> toggleLike(Long postId, Member member) {
        Member activeMember = UserGuardUtil.getActiveUser(member);
        Post post = postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));

        Optional<Like> existingLike = likeRepository.findByMemberAndPost(activeMember, post);
        boolean liked;

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            post.setLikeCount(Math.max(0, post.getLikeCount() - 1));
            liked = false; // 좋아요 취소됨
        } else {
            likeRepository.save(Like.builder()
                    .member(activeMember)
                    .post(post)
                    .build());
            post.setLikeCount(post.getLikeCount() + 1);
            liked = true; // 좋아요 추가됨
        }

        return Map.of(
                "liked", liked,
                "likeCount", post.getLikeCount()
        );
    }
}
