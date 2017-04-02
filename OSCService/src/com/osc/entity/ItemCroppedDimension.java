package com.osc.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "ItemCroppedDimension")
public class ItemCroppedDimension extends BaseEntity {
	private Integer xPosition;
	private Integer yPosition;
	private Integer croppedWidth;
	private Integer croppedHeight;
	private String name;
	private Long itemId;
	@Lob
	private byte[] croppedImage;

	public byte[] getCroppedImage() {
		return croppedImage;
	}

	public void setCroppedImage(byte[] croppedImage) {
		this.croppedImage = croppedImage;
	}

	public Integer getxPosition() {
		return xPosition;
	}

	public void setxPosition(Integer xPosition) {
		this.xPosition = xPosition;
	}

	public Integer getyPosition() {
		return yPosition;
	}

	public void setyPosition(Integer yPosition) {
		this.yPosition = yPosition;
	}

	public Integer getCroppedWidth() {
		return croppedWidth;
	}

	public void setCroppedWidth(Integer croppedWidth) {
		this.croppedWidth = croppedWidth;
	}

	public Integer getCroppedHeight() {
		return croppedHeight;
	}

	public void setCroppedHeight(Integer croppedHeight) {
		this.croppedHeight = croppedHeight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

}
