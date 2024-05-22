package com.thunder.model.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class ScheduleSearchCondtion {
	
	private String dateTime;
	private String category = "none";
	private String addressName = "none";
	
	private ScheduleSearchCondtion() {}
	
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	@Override
	public String toString() {
		return "ScheduleSearchCondtion [dateTime=" + dateTime + ", categoty=" + category + ", addressName=" + addressName
				+ "]";
	}
	
}
