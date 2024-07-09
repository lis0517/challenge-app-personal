package com.twelve.challengeapp.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.twelve.challengeapp.dto.UserResponseDto;
import com.twelve.challengeapp.entity.Comment;
import com.twelve.challengeapp.entity.CommentLike;
import com.twelve.challengeapp.entity.Post;
import com.twelve.challengeapp.entity.PostLike;
import com.twelve.challengeapp.entity.User;
import com.twelve.challengeapp.entity.UserRole;
import com.twelve.challengeapp.repository.impl.UserRepositoryCustomImpl;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryCustomTest {

	@Autowired
	private EntityManager em;

	@Autowired
	private UserRepositoryCustomImpl userRepositoryCustom;

	@Test
	void getUserProfile_ShouldReturnCorrectUserProfileWithLikeCounts() {
		// Given
		User user = new User(null, "testuser", "password", "Test User", "hi", "test@example.com", UserRole.USER);
		em.persist(user);

		Post post1 = Post.builder().title("Post 1").content("Content 1").build();
		post1.setUser(user);
		em.persist(post1);

		Post post2 = Post.builder().title("Post 2").content("Content 2").build();
		post2.setUser(user);
		em.persist(post2);

		// Create some comments
		Comment comment1 = Comment.builder().content("Comment 1").user(user).post(post1).build();
		Comment comment2 = Comment.builder().content("Comment 2").user(user).post(post2).build();
		em.persist(comment1);
		em.persist(comment2);

		// Create post likes
		PostLike postLike1 = PostLike.builder().user(user).post(post1).build();
		PostLike postLike2 = PostLike.builder().user(user).post(post2).build();
		em.persist(postLike1);
		em.persist(postLike2);

		// Create comment likes
		CommentLike commentLike1 = CommentLike.builder().user(user).comment(comment1).build();
		CommentLike commentLike2 = CommentLike.builder().user(user).comment(comment2).build();
		CommentLike commentLike3 = CommentLike.builder().user(user).comment(comment2).build();
		em.persist(commentLike1);
		em.persist(commentLike2);
		em.persist(commentLike3);

		em.flush();
		em.clear();

		// When
		UserResponseDto result = userRepositoryCustom.getUserProfile(user.getId());

		// Then
		assertNotNull(result);
		assertEquals("testuser", result.getUsername());
		assertEquals("Test User", result.getNickname());
		assertEquals("hi", result.getIntroduce());
		assertEquals("test@example.com", result.getEmail());
		assertEquals(2, result.getPostLike());
		assertEquals(3, result.getCommentLike());
	}
}