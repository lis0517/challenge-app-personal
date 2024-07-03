package com.twelve.challengeapp.dto;

import com.twelve.challengeapp.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
	private String username;
	private String nickname;
	private String introduce;
	private String email;
	private Long postLike;
	private Long commentLike;

	@Builder
	public UserResponseDto(String username, String nickname, String introduce, String email) {
		this.username = username;
		this.nickname = nickname;
		this.introduce = introduce;
		this.email = email;
		this.postLike = 0L;
		this.commentLike = 0L;
	}

	public UserResponseDto(User user) {
		this.username = user.getUsername();
		this.nickname = user.getNickname();
		this.introduce = user.getIntroduce();
		this.email = user.getEmail();
		this.postLike = 0L;
		this.commentLike = 0L;
	}

	public void setLikedCounts(Long postLike, Long commentLike) {
		this.postLike = postLike;
		this.commentLike = commentLike;
	}
}
