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

import com.twelve.challengeapp.dto.PostResponseDto;
import com.twelve.challengeapp.entity.Post;
import com.twelve.challengeapp.entity.PostLike;
import com.twelve.challengeapp.entity.User;
import com.twelve.challengeapp.entity.UserRole;
import com.twelve.challengeapp.repository.impl.PostRepositoryCustomImpl;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryCustomTest {

	@Autowired
	private EntityManager em;

	private PostRepositoryCustomImpl postRepositoryCustom;

	@BeforeEach
	void setUp() {
		postRepositoryCustom = new PostRepositoryCustomImpl(em);
	}

	@Test
	void findPostWithLikeCount_ShouldReturnPostWithCorrectLikeCount() {

		//Given
		User postOwner = new User(null, "postm", "password", "Post", "hi!", "test@example.com", UserRole.USER);
		em.persist(postOwner);
		User user1 = new User(null, "testUser", "password", "Test User", "hi", "test@example.com", UserRole.USER);
		em.persist(user1);
		User user2 = new User(null, "anotherUser", "password", "Test User", "hi", "test@example.com", UserRole.USER);
		em.persist(user2);

		Post post = Post.builder().title("test title").content("test content").build();
		postOwner.addPost(post);
		em.persist(post);

		PostLike postLike1 = PostLike.builder().user(user1).post(post).build();
		post.addPostLike(postLike1);
		user1.addPostLike(postLike1);
		em.persist(postLike1);

		PostLike postLike2 = PostLike.builder().user(user2).post(post).build();
		post.addPostLike(postLike2);
		user2.addPostLike(postLike2);
		em.persist(postLike2);

		em.flush();
		em.clear();

		// When
		Optional<PostResponseDto> result = postRepositoryCustom.findPostWithLikeCount(post.getId());

		// Then
		assertTrue(result.isPresent());
		PostResponseDto postResponseDto = result.get();
		assertEquals(post.getId(), postResponseDto.getId());
		assertEquals(postOwner.getUsername(), postResponseDto.getUsername());
		assertEquals(post.getTitle(), postResponseDto.getTitle());
		assertEquals(post.getContent(), postResponseDto.getContent());
		assertEquals(2, postResponseDto.getLikes());
	}

	@Test
	void findLikedPostsByUserId_ShouldReturnPagedLikedPosts() {

		//Given
		User mainUser = new User(null, "testUser", "password", "Test User", "hi", "test@example.com", UserRole.USER);
		em.persist(mainUser);

		List<User> users = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			User user = new User(null, "user" + i, "password", "User " + i, "hi!", "user" + i + "@example.com", UserRole.USER);
			em.persist(user);
			users.add(user);
		}

		for (int i = 0; i < 5; i++) {
			Post post = Post.builder().title("title " + i).content("content " + i).build();
			post.setUser(users.get(i));
			em.persist(post);
			PostLike postLike = PostLike.builder().user(mainUser).post(post).build();
			em.persist(postLike);
		}

		em.flush();
		em.clear();

		Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdAt"));

		// When
		Page<PostResponseDto> result = postRepositoryCustom.findLikedPostsByUserId(mainUser.getId(), pageable);

		//Then
		assertEquals(3, result.getContent().size());
		assertEquals(5, result.getTotalElements());
		assertEquals(2, result.getTotalPages());
	}

}
