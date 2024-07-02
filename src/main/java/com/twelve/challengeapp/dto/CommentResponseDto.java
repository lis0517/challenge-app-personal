package com.twelve.challengeapp.dto;

import java.time.LocalDateTime;

import com.twelve.challengeapp.entity.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
	private Long id;
	private String content;
	private String username;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private int likes;

	@Builder
	public CommentResponseDto(Long id, String username, String content, LocalDateTime createdAt,
		LocalDateTime updatedAt, int likes) {
		this.id = id;
		this.username = username;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.likes = likes;
	}

	public CommentResponseDto(Comment comment) {
		this.id = comment.getId();
		this.content = comment.getContent();
		this.username = comment.getUser().getUsername();
		this.createdAt = comment.getCreatedAt();
		this.updatedAt = comment.getUpdatedAt();
		this.likes = 0;
	}
}
