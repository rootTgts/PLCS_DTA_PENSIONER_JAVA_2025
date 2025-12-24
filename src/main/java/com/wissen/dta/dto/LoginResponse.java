package com.wissen.dta.dto;

public class LoginResponse {

	private String code;
	private boolean authenticate;
	private String role;
	private String token;
	private String passwordChanged;

	public LoginResponse() {

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean getAuthenticate() {
		return authenticate;
	}

	public void setAuthenticate(boolean b) {
		this.authenticate = b;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(String passwordChanged) {
		this.passwordChanged = passwordChanged;
	}

	public LoginResponse(String code, boolean authenticate, String role, String token, String passwordChanged) {
		super();
		this.code = code;
		this.authenticate = authenticate;
		this.role = role;
		this.token = token;
		this.passwordChanged = passwordChanged;
	}

	

}
