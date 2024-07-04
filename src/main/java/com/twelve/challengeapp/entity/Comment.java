package com.twelve.challengeapp.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "comment")
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CommentLike> commentLikes = new ArrayList<>();

	@Builder
	public Comment(Long id, String content, User user, Post post, List<CommentLike> commentLikes) {
		this.id = id;
		this.content = content;
		this.user = user;
		this.post = post;
		this.commentLikes = commentLikes != null ? commentLikes : new ArrayList<>();

		this.user.addComment(this);
		this.post.addComment(this);
	}

	public void update(String content) {
		this.content = content;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public void addCommentLike(CommentLike commentLike) {
		commentLikes.add(commentLike);
	}
}
