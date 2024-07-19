package models.product;

import java.sql.Date;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import models.author.AuthorDTO;
import models.feedback.FeedbackDTO;
import models.image.ImageDTO;

public class ProductDTO {

	private int productId;
	private int categoryId;
	private String name;
	private String shortDescription;
	private String description;
	private int price;
	private String manufacturer;
	private String publisher;
	private String size;
	private String format;
	private int pages;
	private int remain;
	private Date publishDay;
	private Date createdAt;
	private List<ImageDTO> images;
	private List<AuthorDTO> authors;
	private List<FeedbackDTO> feedbacks;
	
	private int selledAmount;

	public ProductDTO() {
	}

	public ProductDTO(int productId, int categoryId, String name, String shortDescription,
					  String description, int price, String manufacturer, String publisher,
					  String size, String format, int pages, int remain, @NotNull Date publishDay,
					  @NotNull Date createdAt) {
		this(productId, categoryId, name, shortDescription,
				description, price, manufacturer, publisher,
				size, format, pages, remain, publishDay, createdAt,
				null, null, null);
	}

	public ProductDTO(int productId, int categoryId, String name, String shortDescription,
					  String description, int price, String manufacturer, String publisher,
					  String size, String format, int pages, int remain, @NotNull Date publishDay,
					  @NotNull Date createdAt, @Nullable List<ImageDTO> images,
					  @Nullable List<AuthorDTO> authors, @Nullable List<FeedbackDTO> feedbacks) {
		this.productId = productId;
		this.categoryId = categoryId;
		this.name = name;
		this.shortDescription = shortDescription;
		this.description = description;
		this.price = price;
		this.manufacturer = manufacturer;
		this.publisher = publisher;
		this.size = size;
		this.format = format;
		this.pages = pages;
		this.remain = remain;
		this.publishDay = publishDay;
		this.createdAt = createdAt;
		this.images = images;
		this.authors = authors;
		this.feedbacks = feedbacks;
	}

	public int getProductId() {
		return productId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public @NotNull String getName() {
		return name;
	}

	public void setName(@NotNull String name) {
		this.name = name;
	}

	public @NotNull String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(@NotNull String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public @NotNull String getDescription() {
		return description;
	}

	public void setDescription(@NotNull String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public @NotNull String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(@NotNull String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public @NotNull String getPublisher() {
		return publisher;
	}

	public void setPublisher(@NotNull String publisher) {
		this.publisher = publisher;
	}

	public @NotNull String getSize() {
		return size;
	}

	public void setSize(@NotNull String size) {
		this.size = size;
	}

	public @NotNull String getFormat() {
		return format;
	}

	public void setFormat(@NotNull String format) {
		this.format = format;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getRemain() {
		return remain;
	}

	public void setRemain(int remain) {
		this.remain = remain;
	}

	public @NotNull Date getPublishDay() {
		return publishDay;
	}

	public void setPublishDay(@NotNull Date publishDay) {
		this.publishDay = publishDay;
	}

	public @NotNull Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(@NotNull Date createdAt) {
		this.createdAt = createdAt;
	}

	public @Nullable List<ImageDTO> getImages() {
		return images;
	}

	public @Nullable List<AuthorDTO> getAuthors() {
		return authors;
	}

	public @Nullable List<FeedbackDTO> getFeedbacks() {
		return feedbacks;
	}
	
	public int getSelledAmount() {
		return selledAmount;
	}
	
	public void setSelledAmount(int selledAmount) {
		this.selledAmount = selledAmount;
	}

}
