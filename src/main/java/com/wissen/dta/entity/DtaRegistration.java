package com.wissen.dta.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table
@Entity(name = "dta_registration")
public class DtaRegistration {
	@Id
	private String requestId;

	@Column(name = "ppo_id")
	private String ppoId;

	@Column(name = "ppo_no")
	private String ppoNo;

	@Column(name = "ppo_name")
	private String ppoName;

	@Column(name = "pension_type")
	private String pensionType;

	@Column(name = "spouse")
	private String spouse;

	@Column(name = "add1")
	private String add1;

	@Column(name = "add2")
	private String add2;

	@Column(name = "add3")
	private String add3;

	@Column(name = "phone")
	private String phone;

	@Column(name = "mail")
	private String mail;

	@Column(name = "aadharno")
	private String aadharNo;

	@Column(name = "panno")
	private String panNo;

	@Column(name = "sp_dob")
	private LocalDate spouseDob;

	@Column(name = "sp_dod")
	private LocalDate spouseDod;

	@Column(name = "fp_dob")
	private LocalDate familyPensionerDob;

	@Column(name = "bank_accno")
	private String bankAccountNo;

	@Column(name = "net_amt")
	private Double netAmount;

	@Column(name = "district")
	private String district;

	@Column(name = "sto")
	private String sto;

	@Column(name = "photo_match_status")
	private String photoMatchStatus;

	@Column(name = "dg_match_status")
	private String dgMatchStatus;

	@Column(name = "photo_match_percentage")
	private String photoMatchPercentage;

	@Column(name = "input_photo_UUID")
	private String inputPhotoUUID;

	@Column(name = "target_photo_UUID")
	private String targetPhotoUUID;

	@Column(name = "status")
	private String status;

	@Column(name = "stage")
	private String stage;

	@Column(name = "message")
	private String message;

	@Column(name = "application_number")
	private String applicationNumber;

	@Column(name = "isExistUserInApp")
	private String isExistUserInApp;

	@Column(name = "ifmis_status")
	private String ifmisStatus;

	@Column(name = "stocode")
	private String stocode;

	@Column(name = "asto_status")
	private String astoStatus;

	@Column(name = "sto_status")
	private String stoStatus;

	@Column(name = "re_mark")
	private String reMark;

	@CreationTimestamp
	@Column(name = "created_time", nullable = false, updatable = false)
	private LocalDateTime createdTime;

	@Column(name = "modified_time")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	public String getStoStatus() {
		return stoStatus;
	}

	public void setStoStatus(String stoStatus) {
		this.stoStatus = stoStatus;
	}

	public String getAstoStatus() {
		return astoStatus;
	}

	public void setAstoStatus(String astoStatus) {
		this.astoStatus = astoStatus;
	}

	public String getReMark() {
		return reMark;
	}

	public void setReMark(String reMark) {
		this.reMark = reMark;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getPpoId() {
		return ppoId;
	}

	public void setPpoId(String ppoId) {
		this.ppoId = ppoId;
	}

	public String getPpoNo() {
		return ppoNo;
	}

	public void setPpoNo(String ppoNo) {
		this.ppoNo = ppoNo;
	}

	public String getPpoName() {
		return ppoName;
	}

	public void setPpoName(String ppoName) {
		this.ppoName = ppoName;
	}

	public String getPensionType() {
		return pensionType;
	}

	public void setPensionType(String pensionType) {
		this.pensionType = pensionType;
	}

	public String getSpouse() {
		return spouse;
	}

	public void setSpouse(String spouse) {
		this.spouse = spouse;
	}

	public String getAdd1() {
		return add1;
	}

	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	public String getAdd2() {
		return add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	public String getAdd3() {
		return add3;
	}

	public void setAdd3(String add3) {
		this.add3 = add3;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public LocalDate getSpouseDob() {
		return spouseDob;
	}

	public void setSpouseDob(LocalDate spouseDob) {
		this.spouseDob = spouseDob;
	}

	public LocalDate getSpouseDod() {
		return spouseDod;
	}

	public void setSpouseDod(LocalDate spouseDod) {
		this.spouseDod = spouseDod;
	}

	public LocalDate getFamilyPensionerDob() {
		return familyPensionerDob;
	}

	public void setFamilyPensionerDob(LocalDate familyPensionerDob) {
		this.familyPensionerDob = familyPensionerDob;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getSto() {
		return sto;
	}

	public void setSto(String sto) {
		this.sto = sto;
	}

	public String getPhotoMatchStatus() {
		return photoMatchStatus;
	}

	public void setPhotoMatchStatus(String photoMatchStatus) {
		this.photoMatchStatus = photoMatchStatus;
	}

	public String getDgMatchStatus() {
		return dgMatchStatus;
	}

	public void setDgMatchStatus(String dgMatchStatus) {
		this.dgMatchStatus = dgMatchStatus;
	}

	public String getPhotoMatchPercentage() {
		return photoMatchPercentage;
	}

	public void setPhotoMatchPercentage(String photoMatchPercentage) {
		this.photoMatchPercentage = photoMatchPercentage;
	}

	public String getInputPhotoUUID() {
		return inputPhotoUUID;
	}

	public void setInputPhotoUUID(String inputPhotoUUID) {
		this.inputPhotoUUID = inputPhotoUUID;
	}

	public String getTargetPhotoUUID() {
		return targetPhotoUUID;
	}

	public void setTargetPhotoUUID(String targetPhotoUUID) {
		this.targetPhotoUUID = targetPhotoUUID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getIsExistUserInApp() {
		return isExistUserInApp;
	}

	public void setIsExistUserInApp(String isExistUserInApp) {
		this.isExistUserInApp = isExistUserInApp;
	}

	public String getIfmisStatus() {
		return ifmisStatus;
	}

	public void setIfmisStatus(String ifmisStatus) {
		this.ifmisStatus = ifmisStatus;
	}

	public String getStocode() {
		return stocode;
	}

	public void setStocode(String stocode) {
		this.stocode = stocode;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "DtaRegistration [requestId=" + requestId + ", ppoId=" + ppoId + ", ppoNo=" + ppoNo + ", ppoName="
				+ ppoName + ", pensionType=" + pensionType + ", spouse=" + spouse + ", add1=" + add1 + ", add2=" + add2
				+ ", add3=" + add3 + ", phone=" + phone + ", mail=" + mail + ", aadharNo=" + aadharNo + ", panNo="
				+ panNo + ", spouseDob=" + spouseDob + ", spouseDod=" + spouseDod + ", familyPensionerDob="
				+ familyPensionerDob + ", bankAccountNo=" + bankAccountNo + ", netAmount=" + netAmount + ", district="
				+ district + ", sto=" + sto + ", photoMatchStatus=" + photoMatchStatus + ", dgMatchStatus="
				+ dgMatchStatus + ", photoMatchPercentage=" + photoMatchPercentage + ", inputPhotoUUID="
				+ inputPhotoUUID + ", targetPhotoUUID=" + targetPhotoUUID + ", status=" + status + ", stage=" + stage
				+ ", message=" + message + ", applicationNumber=" + applicationNumber + ", isExistUserInApp="
				+ isExistUserInApp + ", ifmisStatus=" + ifmisStatus + ", stocode=" + stocode + ", astoStatus="
				+ astoStatus + ", reMark=" + reMark + ", createdTime=" + createdTime + ", modifiedDate=" + modifiedDate
				+ "]";
	}

}
