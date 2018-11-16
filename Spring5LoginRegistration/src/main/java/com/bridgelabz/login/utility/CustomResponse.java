package com.bridgelabz.login.utility;

import java.util.List;

public class CustomResponse {

	private int status;
	
	private String message;
	
	private String headers;

	public int getStatus() {
		return status;
	}

	public void setStatus(int i) {
		this.status = i;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public void setHeaders(List<String> header) {
		this.headers=headers;
		
	}
	
}