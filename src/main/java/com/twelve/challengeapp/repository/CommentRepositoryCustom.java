package com.twelve.challengeapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.twelve.challengeapp.dto.CommentResponseDto;

public interface CommentRepositoryCustom {

	Optional<CommentResponseDto> findCommentWithLikeCount(Long commentId);

	Page<CommentResponseDto> findLikedCommentsByUserId(Long userId, Pageable pageable);
}
