package com.example.community.service;

import com.example.community.common.util.UserGuardUtil;
import com.example.community.dto.request.CommentCreateRequestDto;
import com.example.community.dto.request.CommentUpdateRequestDto;
import com.example.community.dto.response.CommentListResponseDto;
import com.example.community.dto.response.CommentResponseDto;
import com.example.community.exception.ForbiddenException;
import com.example.community.exception.NotFoundException;
import com.example.community.model.Comment;
import com.example.community.model.Member;
import com.example.community.model.Post;
import com.example.community.respository.CommentRepository;
import com.example.community.respository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto createComment(CommentCreateRequestDto request, Member member) {
        Member activeMember = UserGuardUtil.getActiveUser(member);
        Post post = postRepository.findByIdAndDeletedFalse(request.getPostId())
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .post(post)
                .member(activeMember)
                .content(request.getContent())
                .build();

        commentRepository.save(comment);

        post.setCommentCount(post.getCommentCount() + 1); // 댓글 수 증가
        postRepository.save(post);

        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .author(CommentResponseDto.AuthorInfo.builder()
                        .nickname(member.getNickname())
                        .profileImageUrl(member.getProfileImageUrl())
                        .build())
                .build();
    }

    @Transactional(readOnly = true)
    public CommentListResponseDto getComments(Long postId, Member member, Pageable pageable) {
        Member activeMember = UserGuardUtil.getActiveUser(member);
        Slice<Comment> slice = commentRepository.findByPostIdAndDeletedFalse(postId, pageable);

        List<CommentListResponseDto.CommentDto> commentDtos = slice.getContent().stream()
                .map(comment -> CommentListResponseDto.CommentDto.builder()
                        .commentId(comment.getId())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .updatedAt(comment.getUpdatedAt())
                        .author(CommentListResponseDto.CommentDto.AuthorDto.builder()
                                .nickname(comment.getMember().getNickname())
                                .profileImageUrl(comment.getMember().getProfileImageUrl())
                                .build())
                        .owner(comment.getMember().getId().equals(activeMember.getId()))
                        .build())
                .toList();

        return CommentListResponseDto.builder()
                .comments(commentDtos)
                .hasNext(slice.hasNext())
                .build();
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto request, Member member) {
        Member activeMember = UserGuardUtil.getActiveUser(member);
        Comment comment = commentRepository.findByIdAndDeletedFalse(commentId)
                .orElseThrow(() -> new NotFoundException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getMember().getId().equals(activeMember.getId())) {
            throw new ForbiddenException("본인의 댓글만 수정할 수 있습니다.");
        }

        comment.setContent(request.getContent());
        commentRepository.save(comment);

        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .author(CommentResponseDto.AuthorInfo.builder()
                        .nickname(activeMember.getNickname())
                        .profileImageUrl(activeMember.getProfileImageUrl())
                        .build())
                .build();
    }

    @Transactional
    public void deleteComment(Long commentId, Member member) {
        Member activeMember = UserGuardUtil.getActiveUser(member);
        Comment comment = commentRepository.findByIdAndDeletedFalse(commentId)
                .orElseThrow(() -> new NotFoundException("댓글이 존재하지 않습니다."));

        if (!comment.getMember().getId().equals(activeMember.getId())) {
            throw new ForbiddenException("본인의 댓글만 삭제할 수 있습니다.");
        }

        comment.softDelete();

        Post post = comment.getPost();
        post.setCommentCount(Math.max(0, post.getCommentCount() - 1)); // 댓글 수 감소
    }
}
