package models.notification;

import java.sql.Date;

import org.jetbrains.annotations.NotNull;

public class NotificationDTO {
	private int notificationId;
	private int userId;
	private String message;
	private String type;
	private boolean read;
	private Date createdAt;

	public NotificationDTO(int notificationId, int userId, @NotNull String message, @NotNull String type, boolean read,
			@NotNull Date createdAt) {
		this.notificationId = notificationId;
		this.userId = userId;
		this.message = message;
		this.type = type;
		this.read = read;
		this.createdAt = createdAt;
	}

	public NotificationDTO(int userId, @NotNull String message, @NotNull String type, boolean read) {
		this.userId = userId;
		this.message = message;
		this.type = type;
		this.read = read;
	}

	public int getNotificationId() {
		return notificationId;
	}

	public int getUserId() {
		return userId;
	}

	public @NotNull String getMessage() {
		return message;
	}

	public @NotNull String getType() {
		return type;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public @NotNull Date getCreatedAt() {
		return createdAt;
	}
}
