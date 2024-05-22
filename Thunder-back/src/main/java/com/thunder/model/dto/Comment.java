package com.thunder.model.dto;

import java.sql.Timestamp;

public class Comment {
	private int id;
	private int boardId;
	private String userId;
	private String content;
	private Timestamp regDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", boardId=" + boardId + ", userId=" + userId + ", content=" + content
				+ ", regDate=" + regDate + "]";
	}
}
