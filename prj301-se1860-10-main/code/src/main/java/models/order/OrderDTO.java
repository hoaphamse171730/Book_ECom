package models.order;

import java.util.Date;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import models.orderdetail.OrderDetailDTO;

public class OrderDTO {

	private int orderId;
	private int userId;
	private int totalPrice;
	private Status status;
	private Date createdAt;
	private List<OrderDetailDTO> orderDetails;

	public OrderDTO() {
	}

	public OrderDTO(int userId, @NotNull Status status, @NotNull List<OrderDetailDTO> orderDetails) {
		this.userId = userId;
		this.status = status;
		this.orderDetails = orderDetails;
	}

	public OrderDTO(int orderId, int userId, int totalPrice, @NotNull Status status, Date createdAt,
			@NotNull List<OrderDetailDTO> orderDetails) {
		this.orderId = orderId;
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.status = status;
		this.createdAt = createdAt;
		this.orderDetails = orderDetails;
	}

	public int getOrderId() {
		return orderId;
	}

	public int getUserId() {
		return userId;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public @NotNull Status getStatus() {
		return status;
	}

	public void setStatus(@NotNull Status status) {
		this.status = status;
	}

	public @NotNull Date getCreatedAt() {
		return createdAt;
	}

	public @NotNull List<OrderDetailDTO> getOrderDetails() {
		return orderDetails;
	}
}
