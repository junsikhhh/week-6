package com.example.community.respository;

import com.example.community.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndDeletedFalse(Long id); // // 특정 사용자가 작성한 게시글 조회
    Slice<Post> findAllByDeletedFalse(Pageable pageable);
}