package com.wissen.dta.dto;

public class PensinerDetails {

	String ppoId;
	String ppoName;
	String pensionerType;
	String inputPhoto;
	String departmentPhoto;
	String dgMatchStatus;
	String photoMatchStatus;
	String photoMatchScore;
	String astoStatus;

	public String getAstoStatus() {
		return astoStatus;
	}

	public void setAstoStatus(String astoStatus) {
		this.astoStatus = astoStatus;
	}

	public String getPpoId() {
		return ppoId;
	}

	public void setPpoId(String ppoId) {
		this.ppoId = ppoId;
	}

	public String getPpoName() {
		return ppoName;
	}

	public void setPpoName(String ppoName) {
		this.ppoName = ppoName;
	}

	public String getPensionerType() {
		return pensionerType;
	}

	public void setPensionerType(String pensionerType) {
		this.pensionerType = pensionerType;
	}

	public String getInputPhoto() {
		return inputPhoto;
	}

	public void setInputPhoto(String inputPhoto) {
		this.inputPhoto = inputPhoto;
	}

	public String getDepartmentPhoto() {
		return departmentPhoto;
	}

	public void setDepartmentPhoto(String departmentPhoto) {
		this.departmentPhoto = departmentPhoto;
	}

	public String getDgMatchStatus() {
		return dgMatchStatus;
	}

	public void setDgMatchStatus(String dgMatchStatus) {
		this.dgMatchStatus = dgMatchStatus;
	}

	public String getPhotoMatchStatus() {
		return photoMatchStatus;
	}

	public void setPhotoMatchStatus(String photoMatchStatus) {
		this.photoMatchStatus = photoMatchStatus;
	}

	public String getPhotoMatchScore() {
		return photoMatchScore;
	}

	public void setPhotoMatchScore(String photoMatchScore) {
		this.photoMatchScore = photoMatchScore;
	}

	public PensinerDetails() {

	}

	@Override
	public String toString() {
		return "PensinerDetails [ppoId=" + ppoId + ", ppoName=" + ppoName + ", pensionerType=" + pensionerType
				+ ", inputPhoto=" + inputPhoto + ", departmentPhoto=" + departmentPhoto + ", dgMatchStatus="
				+ dgMatchStatus + ", photoMatchStatus=" + photoMatchStatus + ", photoMatchScore=" + photoMatchScore
				+ "]";
	}

}
