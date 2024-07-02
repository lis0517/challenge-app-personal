package com.twelve.challengeapp.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twelve.challengeapp.dto.CommentResponseDto;
import com.twelve.challengeapp.entity.QComment;
import com.twelve.challengeapp.entity.QCommentLike;
import com.twelve.challengeapp.repository.CommentRepositoryCustom;

import jakarta.persistence.EntityManager;

@Repository
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public CommentRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Optional<CommentResponseDto> findCommentWithLikeCount(Long commentId) {

		QComment comment = QComment.comment;
		QCommentLike commentLike = QCommentLike.commentLike;

		return Optional.ofNullable(queryFactory.select(
				Projections.constructor(CommentResponseDto.class, comment.id, comment.user.username, comment.content, comment.createdAt,
					comment.updatedAt, commentLike.id.count().intValue()))
			.from(comment)
			.leftJoin(commentLike)
			.on(commentLike.comment.id.eq(comment.id))
			.where(comment.id.eq(commentId))
			.groupBy(comment.id)
			.fetchOne());
	}
}
