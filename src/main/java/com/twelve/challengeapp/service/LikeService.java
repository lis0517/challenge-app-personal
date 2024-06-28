package com.twelve.challengeapp.service;

import com.twelve.challengeapp.dto.CommentLikeDto;
import com.twelve.challengeapp.dto.PostLikeDto;

public interface LikeService {

	PostLikeDto likePost(Long postId, Long userId);

	CommentLikeDto likeComment(Long commentId, Long userId);
}
