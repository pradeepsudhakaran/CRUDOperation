package com.auth.enums;

public enum LoginEnum {
	ACCOUNT_LOCKED("Account locked"),
	USER_NOT_EXISTS("User does not exist"),
	AUTHENTICATED("User Authenticated"),
	INCORRECT_PASSWORD("Password is incorrect");
	
	private String message;
	private LoginEnum(String message) {
		this.setMessage(message);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
