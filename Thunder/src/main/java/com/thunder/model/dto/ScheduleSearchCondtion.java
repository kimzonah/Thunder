package com.thunder.model.dto;

import java.sql.Timestamp;
import java.time.Instant;

public class ScheduleSearchCondtion {
	
	private Timestamp dateTime;
	private String categoty = "none";
	private String location = "none";
	
	private ScheduleSearchCondtion() {
		this.dateTime = Timestamp.from(Instant.now());
	}
	
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
	public String getCategoty() {
		return categoty;
	}
	public void setCategoty(String categoty) {
		this.categoty = categoty;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		return "ScheduleSearchCondtion [dateTime=" + dateTime + ", categoty=" + categoty + ", location=" + location
				+ "]";
	}
	
	
	
}
