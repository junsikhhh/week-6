package com.example.community.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comments")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // 댓글이 달린 게시글

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;  // 댓글 작성자

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 댓글 내용
}
