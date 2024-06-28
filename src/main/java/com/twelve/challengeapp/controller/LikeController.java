package com.twelve.challengeapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twelve.challengeapp.jwt.UserDetailsImpl;
import com.twelve.challengeapp.service.LikeService;
import com.twelve.challengeapp.util.SuccessResponseFactory;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	@PostMapping("/posts/{postId}/likes")
	public ResponseEntity<?> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

		return SuccessResponseFactory.ok(likeService.likePost(postId, userDetails.getUserId()));
	}

	@PostMapping("/comments/{commentId}/likes")
	public ResponseEntity<?> likeComment(@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		return SuccessResponseFactory.ok(likeService.likeComment(commentId, userDetails.getUserId()));
	}

}
