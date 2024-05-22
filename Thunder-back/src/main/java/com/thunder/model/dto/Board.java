package com.thunder.model.dto;

import java.sql.Timestamp;

public class Board {
	private int id;
	private String title;
	private String content;
	private String userId;
	private Timestamp regDate;
	private int scheduleId;
	private int commentCnt;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	public int getCommentCnt() {
		return commentCnt;
	}
	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}
	
	@Override
	public String toString() {
		return "Board [id=" + id + ", title=" + title + ", content=" + content + ", userId=" + userId + ", regDate="
				+ regDate + ", scheduleId=" + scheduleId + ", commentCnt=" + commentCnt + "]";
	}
}
