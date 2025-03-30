package com.example.community.respository;

import com.example.community.model.Like;
import com.example.community.model.Member;
import com.example.community.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberAndPost(Member member, Post post);  // 특정 유저가 특정 게시글에 좋아요를 눌렀는지 확인
    Long countByPost(Post post);
    boolean existsByMemberAndPost(Member member, Post post);
    void deleteByMemberAndPost(Member member, Post post);
}
