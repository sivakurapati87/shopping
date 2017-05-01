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
	private Float quantity;
	private Long customerId;
	private String divBlob;
	private String divBlobPath;
	private Double deliveryCharges;
	private Double subTotal;
	private String txnId;
	private String providedNames;
	private List<ItemWithCustomerPhtoJson> custPhotoJsonList;

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

}
