package com.example.community.service;

import com.example.community.exception.ImageUploadException;
import com.example.community.global.enums.UploadType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${file.upload-dir.profile}")
    private String profileUploadDir;

    @Value("${file.upload-dir.post}")
    private String postUploadDir;

    public String saveImage(MultipartFile file, UploadType type) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("빈 파일입니다.");
        }

        // 원본 파일명에서 공백/한글/특수문자 제거
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // UUID로 파일명 중복 방지
        String fileName = UUID.randomUUID() + extension;

        String uploadPath;
        String urlPrefix;

        switch (type) {
            case PROFILE -> {
                uploadPath = profileUploadDir;
                urlPrefix = "/images/profile/";
            }
            case POST -> {
                uploadPath = postUploadDir;
                urlPrefix = "/images/post/";
            }
            default -> throw new IllegalArgumentException("지원하지 않는 업로드 유형입니다.");
        }

        // 저장 경로 준비
        Path rootPath = Paths.get("").toAbsolutePath();
        Path savePath = rootPath.resolve(uploadPath).resolve(fileName);

        Files.createDirectories(savePath.getParent()); // 디렉토리 없으면 생성
        file.transferTo(savePath.toFile()); // 이미지 저장

        return urlPrefix + fileName;
    }

    public void deleteImage(String imageUrl, UploadType type) {
        if (imageUrl == null || imageUrl.isBlank() || imageUrl.contains("default-image")) return;

        String filename = Paths.get(imageUrl).getFileName().toString();

        String uploadPath;
        switch (type) {
            case PROFILE -> uploadPath = profileUploadDir;
            case POST -> uploadPath = postUploadDir;
            default -> throw new IllegalArgumentException("지원하지 않는 업로드 유형입니다.");
        }

        Path rootPath = Paths.get("").toAbsolutePath();
        Path filePath = rootPath.resolve(uploadPath).resolve(filename);

        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.warn("이미지 삭제 실패: {}", filePath);
        }
    }
}
