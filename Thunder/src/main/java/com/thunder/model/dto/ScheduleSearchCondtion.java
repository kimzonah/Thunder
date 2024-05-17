package com.thunder.model.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class ScheduleSearchCondtion {
	
	private String dateTime;
	private String categoty = "none";
	private String addressName = "none";
	
	// 현재 시간을 가져오기
    Date now = new Date();

    // 원하는 형식의 문자열로 포맷팅하기
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedDate = dateFormat.format(now);
	
	
	private ScheduleSearchCondtion() {
		this.dateTime = formattedDate;
	}
	
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getCategoty() {
		return categoty;
	}
	public void setCategoty(String categoty) {
		this.categoty = categoty;
	}
	
	
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	@Override
	public String toString() {
		return "ScheduleSearchCondtion [dateTime=" + dateTime + ", categoty=" + categoty + ", addressName=" + addressName
				+ "]";
	}
	
	
	
}
