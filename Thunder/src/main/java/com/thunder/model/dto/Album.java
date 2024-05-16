package com.thunder.model.dto;

public class Album {
	private int id;
	private String userId;
	private int scheduleId;
	private String image;
	private String orgImage;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getOrgImage() {
		return orgImage;
	}
	public void setOrgImage(String orgImage) {
		this.orgImage = orgImage;
	}
	@Override
	public String toString() {
		return "Album [id=" + id + ", userId=" + userId + ", scheduleId=" + scheduleId + ", image=" + image
				+ ", orgImage=" + orgImage + "]";
	}
	
}
