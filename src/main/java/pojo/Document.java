package pojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Document {

	String event_flag;
	String event_time;
	String zone_status;
	String tag_id;
	
	public String getEvent_flag() {
		return event_flag;
	}
	public void setEvent_flag(String event_flag) {
		this.event_flag = event_flag;
	}
	public String getEvent_time() {
		return event_time;
	}
	public void setEvent_time(String event_time) {
		this.event_time = event_time;
	}
	public String getStatus() {
		return zone_status;
	}
	public void setStatus(String status) {
		this.zone_status = status;
	}
	public String getTag_id() {
		return tag_id;
	}
	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}
	public LocalDateTime getEventDateTime(){
		return LocalDateTime.parse(event_time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
