package com.example.community.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // 댓글이 달린 게시글

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 댓글 작성자

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 댓글 내용

    @Column(nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;  // 작성일
}
