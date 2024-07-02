package com.twelve.challengeapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.twelve.challengeapp.dto.PostResponseDto;

public interface PostRepositoryCustom {
	Optional<PostResponseDto> findPostWithLikeCount(Long postId);

	Page<PostResponseDto> findLikedPostsByUserId(Long userId, Pageable pageable);
}
