package com.twelve.challengeapp.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class CommentResponseDtoTest {

	@Test
	void testCommentResponseDto() {
		// given
		Long id = 1L;
		String content = "Test comment";
		String username = "testuser";
		LocalDateTime createdAt = LocalDateTime.now();
		LocalDateTime updatedAt = LocalDateTime.now();
		int like = 5;

		// when
		CommentResponseDto dto = CommentResponseDto.builder()
			.id(id)
			.content(content)
			.username(username)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.likes(like)
			.build();

		// then
		assertEquals(id, dto.getId());
		assertEquals(content, dto.getContent());
		assertEquals(username, dto.getUsername());
		assertEquals(createdAt, dto.getCreatedAt());
		assertEquals(updatedAt, dto.getUpdatedAt());
		assertEquals(like, dto.getLikes());
	}
}
