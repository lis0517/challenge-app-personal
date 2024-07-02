package com.twelve.challengeapp.repository;

import java.util.Optional;

import com.twelve.challengeapp.dto.CommentResponseDto;

public interface CommentRepositoryCustom {

	Optional<CommentResponseDto> findCommentWithLikeCount(Long commentId);

}
