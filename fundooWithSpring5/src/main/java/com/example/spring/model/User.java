package com.example.spring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User{
	
	@Id
	private String userId;
	private String username;
	private Long phoneNumber;
	private String email;
	private String password;
	private boolean activationstatus;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActivationstatus() {
		return activationstatus;
	}
	public void setActivationstatus(boolean activationstatus) {
		this.activationstatus = activationstatus;
	}
	public User(String username, long phoneNumber, String email, String password, boolean activationstatus) {
	
		this.username = username;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		this.activationstatus = activationstatus;
	}
	public User() {
	
	}
	
	
}
