package com.osc.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "itemWithCustomerPhto")
public class ItemWithCustomerPhto extends BaseEntity {
	private Long customerId;
	private Long itemId;
	private Long croppedId;
	private String uploadedImagePath;
	private Boolean isUploadedFrame;
	@ManyToOne
	@JoinColumn(name = "customerId", insertable = false, updatable = false)
	private Customer customer;
	@ManyToOne
	@JoinColumn(name = "itemId", insertable = false, updatable = false)
	private Item item;
	@ManyToOne
	@JoinColumn(name = "croppedId", insertable = false, updatable = false)
	private ItemCroppedDimension itemCroppedDimension;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getCroppedId() {
		return croppedId;
	}

	public void setCroppedId(Long croppedId) {
		this.croppedId = croppedId;
	}

	public String getUploadedImagePath() {
		return uploadedImagePath;
	}

	public void setUploadedImagePath(String uploadedImagePath) {
		this.uploadedImagePath = uploadedImagePath;
	}

	public Boolean getIsUploadedFrame() {
		return isUploadedFrame;
	}

	public void setIsUploadedFrame(Boolean isUploadedFrame) {
		this.isUploadedFrame = isUploadedFrame;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ItemCroppedDimension getItemCroppedDimension() {
		return itemCroppedDimension;
	}

	public void setItemCroppedDimension(ItemCroppedDimension itemCroppedDimension) {
		this.itemCroppedDimension = itemCroppedDimension;
	}

}
