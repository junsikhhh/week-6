package com.example.community.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "mySecretKey";  // JWT 서명용 비밀키
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;  // 24시간 유효(ms)

    // JWT 토큰 생성
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)  // 이메일을 클레임으로 저장
                .setIssuedAt(new Date())  // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // 만료 시간
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // HMAC SHA256으로 서명
                .compact();
    }

    // 토큰에서 클레임(Claims) 추출
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 토큰에서 이메일(사용자 정보) 추출
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // JWT 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            return !getClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
