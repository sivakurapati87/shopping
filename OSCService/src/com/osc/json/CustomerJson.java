package com.osc.json;

import java.util.Date;

public class CustomerJson extends BaseJson {
	private String firstName;
	private String lastName;
	private String emailId;
	private String userName;
	private String password;
	private Long phoneNumber;
	private String gender;
	private Date dateOfBirth;
	private Long pincode;
	private String address;
	private String city;
	private String state;
	private Double totalPurchase;
	private String strTotalPurchase;
	private String hashCodeId;
	private String txnId;
	
	

	public String getHashCodeId() {
		return hashCodeId;
	}

	public void setHashCodeId(String hashCodeId) {
		this.hashCodeId = hashCodeId;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Double getTotalPurchase() {
		return totalPurchase;
	}

	public void setTotalPurchase(Double totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

	public String getStrTotalPurchase() {
		return strTotalPurchase;
	}

	public void setStrTotalPurchase(String strTotalPurchase) {
		this.strTotalPurchase = strTotalPurchase;
	}

}
