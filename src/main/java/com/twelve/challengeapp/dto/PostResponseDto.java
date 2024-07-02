package com.twelve.challengeapp.dto;

import java.time.LocalDateTime;

import com.twelve.challengeapp.entity.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
	private Long id;
	private String username;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private int likes;

	@Builder
	public PostResponseDto(Long id, String username, String title, String content, LocalDateTime createdAt,
		LocalDateTime updatedAt, int likes) {
		this.id = id;
		this.username = username;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.likes = likes;
	}

	public PostResponseDto(Post post) {
		this.id = post.getId();
		this.username = post.getUser().getUsername();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.createdAt = post.getCreatedAt();
		this.updatedAt = post.getUpdatedAt();
		this.likes = 0;
	}
}
