package com.wissen.dta.dto;

public class PensinerDetails {

	private String ppoId;
	private String ppoName;
	private String pensionerType;
	private String inputPhoto;
	private String departmentPhoto;
	private String dgMatchStatus;
	private String photoMatchStatus;
	private String photoMatchScore;
	private String astoStatus;
	private String reMarried;
	private String reEmployed;
	private String otherPension;
	private String consent;

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

	public String getReMarried() {
		return reMarried;
	}

	public void setReMarried(String reMarried) {
		this.reMarried = reMarried;
	}

	public String getReEmployed() {
		return reEmployed;
	}

	public void setReEmployed(String reEmployed) {
		this.reEmployed = reEmployed;
	}

	public String getOtherPension() {
		return otherPension;
	}

	public void setOtherPension(String otherPension) {
		this.otherPension = otherPension;
	}

	public String getConsent() {
		return consent;
	}

	public void setConsent(String consent) {
		this.consent = consent;
	}

	@Override
	public String toString() {
		return "PensinerDetails [ppoId=" + ppoId + ", ppoName=" + ppoName + ", pensionerType=" + pensionerType
				+ ", inputPhoto=" + inputPhoto + ", departmentPhoto=" + departmentPhoto + ", dgMatchStatus="
				+ dgMatchStatus + ", photoMatchStatus=" + photoMatchStatus + ", photoMatchScore=" + photoMatchScore
				+ ", astoStatus=" + astoStatus + ", reMarried=" + reMarried + ", reEmployed=" + reEmployed
				+ ", otherPension=" + otherPension + ", consent=" + consent + "]";
	}

}
