package com.twelve.challengeapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twelve.challengeapp.entity.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

	Optional<CommentLike> findByUserIdAndCommentId(Long userId, Long commentId);

}
