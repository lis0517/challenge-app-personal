package com.twelve.challengeapp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.twelve.challengeapp.dto.CommentLikeDto;
import com.twelve.challengeapp.dto.PostLikeDto;
import com.twelve.challengeapp.entity.Comment;
import com.twelve.challengeapp.entity.CommentLike;
import com.twelve.challengeapp.entity.Post;
import com.twelve.challengeapp.entity.PostLike;
import com.twelve.challengeapp.entity.User;
import com.twelve.challengeapp.exception.CommentNotFoundException;
import com.twelve.challengeapp.exception.PostNotFoundException;
import com.twelve.challengeapp.exception.SelfLikeException;
import com.twelve.challengeapp.exception.UserNotFoundException;
import com.twelve.challengeapp.repository.CommentLikeRepository;
import com.twelve.challengeapp.repository.CommentRepository;
import com.twelve.challengeapp.repository.PostLikeRepository;
import com.twelve.challengeapp.repository.PostRepository;
import com.twelve.challengeapp.repository.UserRepository;

@Service
public class LikeServiceImpl implements LikeService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;

	private final CommentLikeRepository commentLikeRepository;
	private final PostLikeRepository postLikeRepository;

	public LikeServiceImpl(CommentLikeRepository commentLikeRepository, PostLikeRepository postLikeRepository,
		PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
		this.commentLikeRepository = commentLikeRepository;
		this.postLikeRepository = postLikeRepository;
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}

	@Override
	public PostLikeDto likePost(Long postId, Long userId) {

		Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post not found"));

		if (post.getUser().getId().equals(userId)) {
			throw new SelfLikeException("You cannot like your own post");
		}

		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
		Optional<PostLike> optionalPostLike = postLikeRepository.findByUserIdAndPostId(userId, postId);
		if (optionalPostLike.isPresent()) {
			PostLike postLike = optionalPostLike.get();
			postLikeRepository.delete(postLike);

			return new PostLikeDto(postLike);
		} else {
			PostLike postLike = PostLike.builder().user(user).post(post).build();

			PostLike savedPostLike = postLikeRepository.save(postLike);

			return new PostLikeDto(savedPostLike);
		}
	}

	@Override
	public CommentLikeDto likeComment(Long commentId, Long userId) {

		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentNotFoundException("Comment not found"));

		if (comment.getUser().getId().equals(userId)) {
			throw new SelfLikeException("You cannot like your own comment");
		}

		User user = userRepository.findById(userId).orElseThrow(() -> new CommentNotFoundException("User not found"));

		Optional<CommentLike> optionalCommentLike = commentLikeRepository.findByUserIdAndCommentId(userId, commentId);

		if (optionalCommentLike.isPresent()) {
			CommentLike commentLike = optionalCommentLike.get();
			commentLikeRepository.delete(commentLike);

			return new CommentLikeDto(commentLike);
		} else {
			CommentLike commentLike = CommentLike.builder().user(user).comment(comment).build();

			CommentLike savedCommentLike = commentLikeRepository.save(commentLike);

			return new CommentLikeDto(savedCommentLike);
		}
	}
}
