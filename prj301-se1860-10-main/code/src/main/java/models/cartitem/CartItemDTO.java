package models.cartitem;

import models.product.ProductDTO;

public class CartItemDTO {

	private ProductDTO product;
	private int amount;

	public CartItemDTO() {
		// TODO Auto-generated constructor stub
	}

	public CartItemDTO(ProductDTO product, int amount) {
		super();
		this.product = product;
		this.amount = amount;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
