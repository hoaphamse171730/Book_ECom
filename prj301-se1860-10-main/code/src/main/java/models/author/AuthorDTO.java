package models.author;

import org.jetbrains.annotations.NotNull;

public class AuthorDTO {

	private int id;
	private String name;

	public AuthorDTO() {
	}

	public AuthorDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public AuthorDTO(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public @NotNull String getName() {
		return name;
	}

	public void setName(@NotNull String name) {
		this.name = name;
	}

}
