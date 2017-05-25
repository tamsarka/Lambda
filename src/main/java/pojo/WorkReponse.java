package pojo;

import java.util.List;
import java.util.Objects;

public class WorkReponse {
	
	public WorkReponse(String name) {
		super();
		this.name = name;
	}
	String name;
	List<String> date;
	List<String> actualHour;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getDate() {
		return date;
	}
	public void setDate(List<String> date) {
		this.date = date;
	}
	public List<String> getActualHour() {
		return actualHour;
	}
	public void setActualHour(List<String> actualHour) {
		this.actualHour = actualHour;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "name ="+name + ", date ="+date.toArray()[0];
	}
}
