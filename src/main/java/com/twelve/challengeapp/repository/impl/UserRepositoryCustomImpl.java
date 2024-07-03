package com.twelve.challengeapp.repository.impl;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twelve.challengeapp.dto.UserResponseDto;
import com.twelve.challengeapp.entity.QCommentLike;
import com.twelve.challengeapp.entity.QPostLike;
import com.twelve.challengeapp.entity.QUser;
import com.twelve.challengeapp.repository.UserRepositoryCustom;

import jakarta.persistence.EntityManager;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public UserRepositoryCustomImpl(EntityManager em) {
		queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public UserResponseDto getUserProfile(Long userId) {

		QUser user = QUser.user;
		QPostLike postLike = QPostLike.postLike;
		QCommentLike commentLike = QCommentLike.commentLike;

		UserResponseDto userResponseDto = queryFactory.select(
				Projections.constructor(UserResponseDto.class, user.username, user.nickname, user.introduce, user.email))
			.from(user)
			.where(user.id.eq(userId))
			.fetchOne();

		Long postLikeCount = queryFactory.select(postLike.count())
			.from(postLike)
			.where(postLike.user.id.eq(userId))
			.fetchOne();

		Long commentLikeCount = queryFactory.select(commentLike.count())
			.from(commentLike)
			.where(commentLike.user.id.eq(userId))
			.fetchOne();

		if (userResponseDto != null)
			userResponseDto.setLikedCounts(postLikeCount, commentLikeCount);

		return userResponseDto;
	}
}
