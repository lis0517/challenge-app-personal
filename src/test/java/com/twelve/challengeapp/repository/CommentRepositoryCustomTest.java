package com.twelve.challengeapp.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.twelve.challengeapp.dto.CommentResponseDto;
import com.twelve.challengeapp.entity.Comment;
import com.twelve.challengeapp.entity.CommentLike;
import com.twelve.challengeapp.entity.Post;
import com.twelve.challengeapp.entity.User;
import com.twelve.challengeapp.entity.UserRole;
import com.twelve.challengeapp.repository.impl.CommentRepositoryCustomImpl;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryCustomTest {

	@Autowired
	private EntityManager em;

	private CommentRepositoryCustomImpl commentRepositoryCustom;

	@BeforeEach
	void setUp() {
		commentRepositoryCustom = new CommentRepositoryCustomImpl(em);
	}

	@Test
	void findCommentWithLikeCount_ShouldReturnCommentsWithLikeCount() {

		//Given
		User user = new User(null, "testUser1", "password", "test1", "hi", "test@example.com", UserRole.USER);
		em.persist(user);

		User user2 = new User(null, "testUser2", "password", "test2", "hi", "test@example.com", UserRole.USER);
		em.persist(user2);

		User user3 = new User(null, "testUser3", "password", "test3", "hi", "test@example.com", UserRole.USER);
		em.persist(user3);

		Post post = Post.builder().title("temp title").content("temp content").build();
		post.setUser(user);
		em.persist(post);

		Comment comment = Comment.builder().content("test content").user(user).post(post).build();
		em.persist(comment);

		CommentLike commentLike1 = CommentLike.builder().comment(comment).user(user2).build();
		comment.addCommentLike(commentLike1);
		em.persist(commentLike1);
		CommentLike commentLike2 = CommentLike.builder().comment(comment).user(user3).build();
		comment.addCommentLike(commentLike2);
		em.persist(commentLike2);

		em.flush();
		em.clear();

		// When
		Optional<CommentResponseDto> result = commentRepositoryCustom.findCommentWithLikeCount(comment.getId());

		// Then

		assertTrue(result.isPresent());
		CommentResponseDto commentResponseDto = result.get();
		assertEquals(comment.getId(), commentResponseDto.getId());
		assertEquals(user.getUsername(), commentResponseDto.getUsername());
		assertEquals(comment.getContent(), commentResponseDto.getContent());
		assertEquals(2, commentResponseDto.getLikes());
	}

	@Test
	void findLikedCommentsByUserId_ShouldReturnPagedLikedComments() {

		//Given
		User postOwner = new User(null, "testUser", "password", "Post User", "hi", "test@example.com", UserRole.USER);
		em.persist(postOwner);

		User likeOwner = new User(null, "testUser2", "password", "Like User", "hi", "test@example.com", UserRole.USER);
		em.persist(likeOwner);

		List<User> users = new ArrayList<>(); // 댓글 작성자들
		for (int i = 0; i < 5; i++) {
			User user = new User(null, "user" + i, "password", "User " + i, "hi!", "user" + i + "@example.com",
				UserRole.USER);
			em.persist(user);
			users.add(user);
		}

		Post post = Post.builder().title("title test").content("content test").build();
		post.setUser(postOwner);
		em.persist(post);

		for (int i = 0; i < 5; i++) {
			Comment comment = Comment.builder().content("content " + i).user(users.get(i)).post(post).build();

			em.persist(comment);
			CommentLike commentLike = CommentLike.builder().comment(comment).user(likeOwner).build();
			em.persist(commentLike);
		}

		em.flush();
		em.clear();

		Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdAt"));

		// When
		Page<CommentResponseDto> result = commentRepositoryCustom.findLikedCommentsByUserId(likeOwner.getId(),
			pageable);

		//Then
		assertEquals(3, result.getContent().size());
		assertEquals(5, result.getTotalElements());
		assertEquals(2, result.getTotalPages());
	}

}
