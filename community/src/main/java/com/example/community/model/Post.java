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
@Table(name = "posts")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;  // 작성자

    @Column(nullable = false, length = 26)
    private String title;  // 게시글 제목 (최대 26자)

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;  // 게시글 내용

    @Column(name = "image_url", length = 255, columnDefinition = "VARCHAR(255)")
    private String imageUrl;  // 첨부 이미지 URL

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0", insertable = false)
    private int likeCount;  // 좋아요 수

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0", insertable = false)
    private int viewCount;  // 조회수

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0", insertable = false)
    private int commentCount;  // 댓글 수
}
