package com.twelve.challengeapp.dto;

import java.time.LocalDateTime;

import com.twelve.challengeapp.entity.PostLike;

import lombok.Getter;

@Getter
public class PostLikeDto {

	private Long userId;
	private Long postId;
	private String username;
	private String title;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public PostLikeDto(PostLike postLike) {
		this.userId = postLike.getUser().getId();
		this.username = postLike.getUser().getUsername();

		this.postId = postLike.getPost().getId();
		this.title = postLike.getPost().getTitle();

		this.createdAt = postLike.getCreatedAt();
		this.updatedAt = postLike.getUpdatedAt();
	}
}
