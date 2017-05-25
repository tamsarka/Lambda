package Lambda.Inspect;

public class StartTimeList {
	Long duration;
	String color;
	String task;
	int start;
	
	public StartTimeList(Long duration, String color, String task) {
		super();
		this.duration = duration;
		this.color = color;
		this.task = task;
	}
	
	public StartTimeList(Long duration, String color, String task, int start) {
		super();
		this.duration = duration;
		this.color = color;
		this.task = task;
		this.start = start;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
}
