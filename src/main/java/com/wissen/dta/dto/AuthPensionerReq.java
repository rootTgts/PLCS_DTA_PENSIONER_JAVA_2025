package com.wissen.dta.dto;

public class AuthPensionerReq {

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

	@Override
	public String toString() {
		return "AuthPensionerReq [ppo_id=" + ppo_id + ", acknowledgeNo=" + acknowledgeNo + ", authorizationDate="
				+ authorizationDate + "]";
	}

}
