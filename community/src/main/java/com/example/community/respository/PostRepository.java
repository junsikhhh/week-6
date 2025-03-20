package com.example.community.respository;

import com.example.community.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);  // 특정 사용자가 작성한 게시글 조회
}