package com.thunder.model.dto;

public class Friend {
	private int id;
	private int status;
	private String toUserId;
	private String fromUserId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	
	@Override
	public String toString() {
		return "Friend [id=" + id + ", status=" + status + ", toUserId=" + toUserId + ", fromUserId=" + fromUserId
				+ "]";
	}
}
