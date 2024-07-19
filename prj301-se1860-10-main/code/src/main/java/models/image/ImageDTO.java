package models.image;

import java.sql.Timestamp;
import java.sql.Date;

import org.jetbrains.annotations.NotNull;

public class ImageDTO {
	private final int imageId;
	private final int productId;
	private final String url;
	private final Date createdAt;

	public ImageDTO(int imageId, int productId, @NotNull String url, @NotNull Date createdAt) {
		this.imageId = imageId;
		this.productId = productId;
		this.url = url;
		this.createdAt = createdAt;
	}

	public int getImageId() {
		return imageId;
	}

	public int getProductId() {
		return productId;
	}

	public @NotNull String getUrl() {
		return url;
	}

	public @NotNull Date getCreatedAt() {
		return createdAt;
	}
	
	public String toString() {
		return url;
	}
}
