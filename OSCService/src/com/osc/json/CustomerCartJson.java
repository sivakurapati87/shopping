package com.osc.json;

import java.util.List;

/**
 * 
 * @author siva kurapati
 *
 */
public class CustomerCartJson {
	private Long itemId;
	private Double total;
	private Float quantity;
	private Long customerId;
	private String divBlob;
	private List<ItemWithCustomerPhtoJson> custPhotoJsonList;

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

}
