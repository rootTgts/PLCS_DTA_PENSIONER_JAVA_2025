package com.wissen.dta.dto;

public class UpdateDetails {
	private String status;
	private String reMark;
	private String ppoId;
	private String code;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReMark() {
		return reMark;
	}

	public void setReMark(String reMark) {
		this.reMark = reMark;
	}

	public String getPpoId() {
		return ppoId;
	}

	public void setPpoId(String ppoId) {
		this.ppoId = ppoId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public UpdateDetails() {

	}

	@Override
	public String toString() {
		return "UpdateDetails [status=" + status + ", reMark=" + reMark + ", ppoId=" + ppoId + ", code=" + code + "]";
	}

}
