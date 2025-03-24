package com.example.community.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;  // 이메일 (로그인 ID 역할)

    @Column(nullable = false, length = 255)
    private String password;  // 비밀번호 (BCrypt 해싱)

    @Column(nullable = false, unique = true, length = 10)
    private String nickname;  // 닉네임 (최대 10자)

    @Column(name = "profile_image_url", length = 255, columnDefinition = "VARCHAR(255) DEFAULT 'default-image'")
    private String profileImageUrl;  // 프로필 이미지 (기본값 설정)
}
