package com.osc.json;

import java.util.List;

/**
 * 
 * @author siva kurapati
 *
 */
public class CustomerCartJson extends BaseJson {
	private Long itemId;
	private Double total;
	private String itemName;
	private Float quantity;
	private Long customerId;
	private String divBlob;
	private String divBlobPath;
	private Double deliveryCharges;
	private Double subTotal;
	private String txnId;
	private String providedNames;
	private String status;

	private Boolean isReduceAmount;
	private Double promoCodeReducedAmount;
	private String fullName;
	private String emailId;
	private Long phoneNumber;
	private Long pincode;
	private String address;
	private String city;
	private String state;

	private Long promoCodeId;

	private List<ItemWithCustomerPhtoJson> custPhotoJsonList;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public Double getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(Double deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<ItemWithCustomerPhtoJson> getCustPhotoJsonList() {
		return custPhotoJsonList;
	}

	public void setCustPhotoJsonList(List<ItemWithCustomerPhtoJson> custPhotoJsonList) {
		this.custPhotoJsonList = custPhotoJsonList;
	}

	public String getDivBlob() {
		return divBlob;
	}

	public void setDivBlob(String divBlob) {
		this.divBlob = divBlob;
	}

	public String getDivBlobPath() {
		return divBlobPath;
	}

	public void setDivBlobPath(String divBlobPath) {
		this.divBlobPath = divBlobPath;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getProvidedNames() {
		return providedNames;
	}

	public void setProvidedNames(String providedNames) {
		this.providedNames = providedNames;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getPromoCodeId() {
		return promoCodeId;
	}

	public void setPromoCodeId(Long promoCodeId) {
		this.promoCodeId = promoCodeId;
	}

	public Boolean getIsReduceAmount() {
		return isReduceAmount != null ? isReduceAmount : false;
	}

	public void setIsReduceAmount(Boolean isReduceAmount) {
		this.isReduceAmount = isReduceAmount;
	}

	public Double getPromoCodeReducedAmount() {
		return promoCodeReducedAmount;
	}

	public void setPromoCodeReducedAmount(Double promoCodeReducedAmount) {
		this.promoCodeReducedAmount = promoCodeReducedAmount;
	}

}
