package com.example.community.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 좋아요를 누른 회원

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // 좋아요가 달린 게시글
}
