package models.category;

import org.jetbrains.annotations.NotNull;

public class CategoryDTO {
	private int id;
	private String name;

	public CategoryDTO() {
	}

	public CategoryDTO(int id, @NotNull String name) {
		this.id = id;
		this.name = name;
	}

	public CategoryDTO(@NotNull String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public @NotNull String getName() {
		return name;
	}

	public void setName(@NotNull String name) {
		this.name = name;
	}
}
