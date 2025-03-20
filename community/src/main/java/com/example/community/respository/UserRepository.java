package com.example.community.respository;

import com.example.community.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일을 기준으로 회원 찾기
    Optional<User> findByEmail(String email);
}
