package com.twelve.challengeapp.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twelve.challengeapp.dto.PostResponseDto;
import com.twelve.challengeapp.entity.QPost;
import com.twelve.challengeapp.entity.QPostLike;
import com.twelve.challengeapp.repository.PostRepositoryCustom;

import jakarta.persistence.EntityManager;

@Repository
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public PostRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Optional<PostResponseDto> findPostWithLikeCount(Long postId) {
		QPost post = QPost.post;
		QPostLike postLike = QPostLike.postLike;

		return Optional.ofNullable(queryFactory.select(
				Projections.constructor(PostResponseDto.class, post.id, post.user.username, post.title, post.content,
					post.createdAt, post.updatedAt, postLike.id.count().intValue()))
			.from(post)
			.leftJoin(postLike)
			.on(postLike.post.id.eq(post.id))
			.where(post.id.eq(postId))
			.groupBy(post.id)
			.fetchOne());
	}
}
