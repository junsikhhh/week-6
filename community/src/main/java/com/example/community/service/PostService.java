package com.example.community.service;

import com.example.community.common.util.UserGuardUtil;
import com.example.community.dto.request.PostRequestDto;
import com.example.community.dto.response.PostCreateResponseDto;
import com.example.community.dto.response.PostDetailResponseDto;
import com.example.community.dto.response.PostListResponseDto;
import com.example.community.exception.ForbiddenException;
import com.example.community.exception.ImageUploadException;
import com.example.community.exception.NotFoundException;
import com.example.community.global.enums.UploadType;
import com.example.community.model.Member;
import com.example.community.model.Post;
import com.example.community.respository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final FileService fileService;

    // 게시글 작성
    @Transactional
    public PostCreateResponseDto createPost(PostRequestDto request, MultipartFile image, Member member) {
        Member activeMember = UserGuardUtil.getActiveUser(member);

        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            try {
                imageUrl = fileService.saveImage(image, UploadType.POST);
            } catch (Exception e) {
                throw new ImageUploadException("이미지 저장 실패");
            }
        }

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(imageUrl)
                .member(activeMember)
                .build();
        postRepository.save(post);
        return PostCreateResponseDto.of(post);
    }

    // 단일 게시글 조회
    @Transactional
    public PostDetailResponseDto getPostDetail(Long postId, Member member) {
        Member activeMember = UserGuardUtil.getActiveUser(member);
        Post post = postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));

        boolean isOwner = post.getMember().getId().equals(activeMember.getId());
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);

        return PostDetailResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .isOwner(isOwner)
                .author(PostDetailResponseDto.AuthorDto.builder()
                        .nickname(post.getMember().getNickname())
                        .profileImageUrl(post.getMember().getProfileImageUrl())
                        .build())
                .build();
    }

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public PostListResponseDto getPostList(Pageable pageable) {
        Slice<Post> slice = postRepository.findAllByDeletedFalse(pageable);

        List<PostListResponseDto.PostSummary> posts = slice.getContent().stream()
                .map(post -> PostListResponseDto.PostSummary.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .likeCount(post.getLikeCount())
                        .viewCount(post.getViewCount())
                        .commentCount(post.getCommentCount())
                        .createdAt(post.getCreatedAt())
                        .author(PostListResponseDto.AuthorInfo.builder()
                                .nickname(post.getMember().getNickname())
                                .profileImageUrl(post.getMember().getProfileImageUrl())
                                .build())
                        .build())
                .toList();

        return PostListResponseDto.builder()
                .posts(posts)
                .hasNext(slice.hasNext())
                .build();
    }

    // 게시글 수정
    @Transactional
    public Long updatePost(Long postId, Member member, PostRequestDto request, MultipartFile image) {
        Member activeMember = UserGuardUtil.getActiveUser(member);

        Post post = postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));

        if (!post.getMember().getId().equals(activeMember.getId())) {
            throw new ForbiddenException("수정 권한이 없습니다.");
        }

        String imageUrl = post.getImageUrl(); // 기존 이미지 유지

        if (image != null && !image.isEmpty()) {
            fileService.deleteImage(imageUrl, UploadType.POST); // 기존 이미지 삭제
            try {
                imageUrl = fileService.saveImage(image, UploadType.POST); // 새 이미지 저장
            } catch (IOException e) {
                throw new ImageUploadException("이미지 저장 실패");
            }
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setImageUrl(imageUrl);
        postRepository.save(post);

        return post.getId();
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, Member member) {
        Member activeMember = UserGuardUtil.getActiveUser(member);
        Post post = postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));

        if (!post.getMember().getId().equals(activeMember.getId())) {
            throw new ForbiddenException("삭제 권한이 없습니다.");
        }
        post.softDelete();
        postRepository.save(post);
    }
}
