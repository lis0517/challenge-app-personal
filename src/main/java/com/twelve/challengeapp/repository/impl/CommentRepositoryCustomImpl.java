package com.twelve.challengeapp.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
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
				Projections.constructor(CommentResponseDto.class, comment.id, comment.user.username, comment.content,
					comment.createdAt, comment.updatedAt, commentLike.id.count().intValue()))
			.from(comment)
			.leftJoin(commentLike)
			.on(commentLike.comment.id.eq(comment.id))
			.where(comment.id.eq(commentId))
			.groupBy(comment.id)
			.fetchOne());
	}

	@Override
	public Page<CommentResponseDto> findLikedCommentsByUserId(Long userId, Pageable pageable) {

		QComment comment = QComment.comment;
		QCommentLike commentLike = QCommentLike.commentLike;

		List<CommentResponseDto> comments = queryFactory.select(
				Projections.constructor(CommentResponseDto.class, comment.id, comment.user.username, comment.content,
					comment.createdAt, comment.updatedAt, commentLike.id.count().intValue()))
			.from(commentLike)
			.join(commentLike.comment, comment)
			.where(commentLike.user.id.eq(userId))
			.groupBy(comment.id)
			.orderBy(comment.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory.select(comment.id.countDistinct())
			.from(commentLike)
			.join(commentLike.comment, comment)
			.where(commentLike.user.id.eq(userId))
			.groupBy(comment.id);

		return PageableExecutionUtils.getPage(comments, pageable, countQuery::fetchOne);
	}
}
