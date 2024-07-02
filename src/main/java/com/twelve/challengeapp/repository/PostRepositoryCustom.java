package com.twelve.challengeapp.repository;

import java.util.Optional;

import com.twelve.challengeapp.dto.PostResponseDto;

public interface PostRepositoryCustom {
	Optional<PostResponseDto> findPostWithLikeCount(Long postId);
}
