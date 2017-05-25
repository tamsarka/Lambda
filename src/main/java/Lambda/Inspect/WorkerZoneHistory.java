package Lambda.Inspect;

import java.util.List;

public class WorkerZoneHistory {
	String workerName;
	List<StartTimeList> zoneHistory;
	
	
	public WorkerZoneHistory() {
		super();
	}
	public WorkerZoneHistory(String workerName, List<StartTimeList> zoneHistory) {
		super();
		this.workerName = workerName;
		this.zoneHistory = zoneHistory;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public List<StartTimeList> getZoneHistory() {
		return zoneHistory;
	}
	public void setZoneHistory(List<StartTimeList> zoneHistory) {
		this.zoneHistory = zoneHistory;
	}

	
}
