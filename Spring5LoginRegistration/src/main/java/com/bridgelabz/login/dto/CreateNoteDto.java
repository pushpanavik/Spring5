package com.bridgelabz.login.dto;

public class CreateNoteDto {

    private String title;
	
	private String description;
	
	private String color;
	
	private boolean archive;
	
	private boolean trash;
	
	private boolean pin;
	
	private String image;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public boolean isTrash() {
		return trash;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
	
}
