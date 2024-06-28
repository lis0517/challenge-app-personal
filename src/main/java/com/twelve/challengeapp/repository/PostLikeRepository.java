package com.twelve.challengeapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twelve.challengeapp.entity.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

	Optional<PostLike> findByUserIdAndPostId(Long userId, Long postId);

}
