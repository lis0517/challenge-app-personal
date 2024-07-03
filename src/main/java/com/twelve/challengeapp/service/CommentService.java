package com.twelve.challengeapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.twelve.challengeapp.dto.CommentResponseDto;
import com.twelve.challengeapp.entity.User;

public interface CommentService {
	CommentResponseDto createComment(Long postId, String content, Long userId);

	CommentResponseDto updateComment(Long commentId, String content, User user);

	void deleteComment(Long commentId, Long userId);

	List<CommentResponseDto> getCommentsByPostId(Long postId);

	CommentResponseDto getComment(Long commentId);

	Page<CommentResponseDto> getLikedComments(Long userId, Pageable pageable);
}
