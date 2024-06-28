package com.twelve.challengeapp.dto;

import java.time.LocalDateTime;

import com.twelve.challengeapp.entity.CommentLike;

import lombok.Getter;

@Getter
public class CommentLikeDto {

	private Long userId;
	private Long commentId;

	private String username;
	private String content;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public CommentLikeDto(CommentLike commentLike) {
		this.userId = commentLike.getUser().getId();
		this.username = commentLike.getUser().getUsername();

		this.commentId = commentLike.getComment().getId();
		this.content = commentLike.getComment().getContent();

		this.createdAt = commentLike.getCreatedAt();
		this.updatedAt = commentLike.getUpdatedAt();
	}
}
