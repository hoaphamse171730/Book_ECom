package models.favorite;

import java.sql.Date;

import org.jetbrains.annotations.NotNull;

import models.product.ProductDTO;

public class FavoriteDTO {
    private int productId;
    private int userId;
    private Date createdAt;
    private ProductDTO product;

    public FavoriteDTO() {
    }

    public FavoriteDTO(int productId, int userId, @NotNull Date createdAt) {
        this.productId = productId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public @NotNull Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull Date createdAt) {
        this.createdAt = createdAt;
    }

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}
    
    
}
