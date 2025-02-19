package com.wissen.dta.dto;

public class AuthPensionerRes {

	private String status;
	private String message;
	private String ppo_id;
	private String acknowledgeNo;
	private String authorizationDate;

	public String getPpo_id() {
		return ppo_id;
	}

	public void setPpo_id(String ppo_id) {
		this.ppo_id = ppo_id;
	}

	public String getAcknowledgeNo() {
		return acknowledgeNo;
	}

	public void setAcknowledgeNo(String acknowledgeNo) {
		this.acknowledgeNo = acknowledgeNo;
	}

	public String getAuthorizationDate() {
		return authorizationDate;
	}

	public void setAuthorizationDate(String authorizationDate) {
		this.authorizationDate = authorizationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "AuthPensionerRes [status=" + status + ", message=" + message + "]";
	}
}
