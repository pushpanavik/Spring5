package com.bridgelabz.login.dto;

import java.time.LocalDateTime;

public class NoteDto extends CreateNoteDto {

	private String noteid;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public String getId() {
		return noteid;
	}

	public void setId(String id) {
		this.noteid = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
