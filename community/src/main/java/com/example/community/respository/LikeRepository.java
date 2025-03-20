package com.example.community.respository;

import com.example.community.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);  // 특정 유저가 특정 게시글에 좋아요를 눌렀는지 확인
}
