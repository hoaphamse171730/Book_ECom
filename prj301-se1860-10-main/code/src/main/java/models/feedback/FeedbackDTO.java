package models.feedback;

import java.sql.Date;

import org.jetbrains.annotations.NotNull;

import models.user.UserDTO;

public class FeedbackDTO {
	private int feedbackId;
	private int userId;
	private int productId;
	private int rating;
	private String content;
	private Date createdAt;
	private UserDTO user;

	public FeedbackDTO() {
	}

	public FeedbackDTO(int feedbackId, int userId, int productId, int rating, @NotNull String content,
			@NotNull Date createdAt) {
		this.feedbackId = feedbackId;
		this.userId = userId;
		this.productId = productId;
		this.rating = rating;
		this.content = content;
		this.createdAt = createdAt;
	}

	public FeedbackDTO(int feedbackId, int userId, int productId, int rating, @NotNull String content,
			@NotNull Date createdAt, UserDTO user) {
		this.feedbackId = feedbackId;
		this.userId = userId;
		this.productId = productId;
		this.rating = rating;
		this.content = content;
		this.createdAt = createdAt;
		this.user = user;
	}

	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public @NotNull String getContent() {
		return content;
	}

	public void setContent(@NotNull String content) {
		this.content = content;
	}

	public @NotNull Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(@NotNull Date createdAt) {
		this.createdAt = createdAt;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}