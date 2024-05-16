package com.thunder.model.dto;

import java.sql.Timestamp;

public class Schedule {
	private int id;
	private String managerId;
	private String title;
	private String category;
	private int scale;
	private String image;
	private String description;
	private Timestamp dateTime;
	private String addressName;
	private String longitude;
	private String latitude;
	private String chatUrl;
	private String orgImage;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getChatUrl() {
		return chatUrl;
	}
	public void setChatUrl(String chatUrl) {
		this.chatUrl = chatUrl;
	}
	public String getOrgImage() {
		return orgImage;
	}
	public void setOrgImage(String orgImage) {
		this.orgImage = orgImage;
	}
	
	@Override
	public String toString() {
		return "Schedule [id=" + id + ", managerId=" + managerId + ", title=" + title + ", category=" + category
				+ ", scale=" + scale + ", image=" + image + ", description=" + description + ", dateTime=" + dateTime
				+ ", addressName=" + addressName + ", longitude=" + longitude + ", latitude=" + latitude + ", chatUrl="
				+ chatUrl + ", orgImage=" + orgImage + "]";
	}
}
