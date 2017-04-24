package com.osc.json;


public class ItemWithCustomerPhtoJson extends BaseJson {
	private Long customerId;
	private Long itemId;
	private Long croppedId;
	private String uploadedImagePath;
	private Boolean isUploadedFrame;
	private String imageBlob;

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

	public String getImageBlob() {
		return imageBlob;
	}

	public void setImageBlob(String imageBlob) {
		this.imageBlob = imageBlob;
	}
}
