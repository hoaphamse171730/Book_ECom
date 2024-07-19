package models.orderdetail;

import models.product.ProductDTO;

public class OrderDetailDTO {

	private int orderId;
	private int productId;
	private int price;
	private int amount;
	private ProductDTO product;

	public OrderDetailDTO() {
	}

	public OrderDetailDTO(int productId, int price, int amount) {
		this.productId = productId;
		this.price = price;
		this.amount = amount;
	}

	public OrderDetailDTO(int orderId, int productId, int price, int amount, ProductDTO product) {
		this.orderId = orderId;
		this.productId = productId;
		this.price = price;
		this.amount = amount;
		this.product = product;
	}

	public int getOrderId() {
		return orderId;
	}

	public int getProductId() {
		return productId;
	}

	public int getPrice() {
		return price;
	}

	public int getAmount() {
		return amount;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

}
