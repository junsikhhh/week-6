package com.example.community.respository;

import com.example.community.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);  // 특정 게시글의 댓글 조회
    Slice<Comment> findByPostIdAndDeletedFalse(Long postId, Pageable pageable);
    Optional<Comment> findByIdAndDeletedFalse(Long id);
}
