package com.example.community.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24L;  // 24시간 유효(ms)

    // JWT 토큰 생성
    public String generateToken(String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(email)  // 이메일을 클레임으로 저장
                .setIssuedAt(now)  // 토큰 발급 시간
                .setExpiration(expiry)  // 만료 시간
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())  // HMAC SHA256으로 서명
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return !getClaims(token).getExpiration().before(new Date());
    }

    // JWT 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            return isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}
