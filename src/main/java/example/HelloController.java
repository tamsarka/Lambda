package example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pojo.WorkReponse;
import pojo.WorkerData;
import pojo.WorkerDetails;

@RestController()
public class HelloController {

	final int MINUTES_PER_HOUR = 60;
	final int SECONDS_PER_MINUTE = 60;
	final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
	final String dateFormat = "yyyy-MM-dd";
	final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

	@RequestMapping(value = "/xyz")
	public String index() {
		return "Greetings from Spring !";
	}

	@RequestMapping(value = "/workerActualHours")
	public ResponseEntity<Collection<WorkReponse>> listOfActualHour(@RequestBody WorkerDetails workerDetails) {
		List<WorkerData> strarry = workerDetails.getData();
		System.out.println("workerDetails size -"+workerDetails.getData().size());
		System.out.println("workerDetails size -"+workerDetails.getData().get(0).getDocument());
		Map<String, TreeSet<WorkerData>> workMap = sortWorkerData(strarry);
		return new ResponseEntity<>(getWorkerSpecificData(workMap).values(), HttpStatus.OK);

	}

	private Map<String, WorkReponse> getWorkerSpecificData(Map<String, TreeSet<WorkerData>> workerData) {
		Map<String, WorkReponse> tempWorkResp = new HashMap<>();

		Iterator<Map.Entry<String, TreeSet<WorkerData>>> entries = workerData.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, TreeSet<WorkerData>> entry = entries.next();
			ArrayList<String> tempDateStrList = new ArrayList<>();
			ArrayList<String> tempActualHourStrList = new ArrayList<>();
			WorkReponse workResponse;
			LocalDateTime startDate = null;
			LocalDateTime endDate = null;
			long[] actualHor = new long[entry.getValue().size()];
			int incremental = 0;
			String workerName = entry.getKey().split("-")[0];
			if (tempWorkResp.get(workerName) == null) {
				workResponse = new WorkReponse(workerName);
			} else {
				workResponse = tempWorkResp.get(workerName);
			}
			for (WorkerData wd : entry.getValue()) {
				if (wd.getDocument().getEvent_flag().equals("Enter")) {
					startDate = LocalDateTime.parse(wd.getDocument().getEvent_time(),
							DateTimeFormatter.ofPattern(dateTimeFormat));
				} else if (wd.getDocument().getEvent_flag().equals("Exit")) {
					endDate = LocalDateTime.parse(wd.getDocument().getEvent_time(),
							DateTimeFormatter.ofPattern(dateTimeFormat));
					actualHor[incremental] = (startDate != null && endDate != null) ? getTime(startDate, endDate) : 0;
					incremental++;
				}
			}
			tempActualHourStrList.add(getTheTotalHourForADay(actualHor));
			tempDateStrList.add(startDate.format(DateTimeFormatter.ofPattern(dateFormat)));

			if (workResponse.getDate() == null && workResponse.getActualHour() == null) {
				workResponse.setDate(tempDateStrList);
				workResponse.setActualHour(tempActualHourStrList);
			} else {
				workResponse.getDate().addAll(tempDateStrList);
				workResponse.getActualHour().addAll(tempActualHourStrList);
			}
			tempWorkResp.put(workerName, workResponse);
		}
		return tempWorkResp;
	}

	private String getTheTotalHourForADay(long[] allTheHoursForADay) {
		long total = 0;
		for (long hour : allTheHoursForADay) {
			total += hour;
		}

		return Long.toString(total);
	}

	public String getTheDate(String strDate, String format) {
		LocalDateTime datetime = LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern(dateTimeFormat));
		String newstring = datetime.format(DateTimeFormatter.ofPattern(format));
		return newstring;
	}

	public long getTime(LocalDateTime dob, LocalDateTime now) {
		LocalDateTime today = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), dob.getHour(),
				dob.getMinute(), dob.getSecond());
		Duration duration = Duration.between(today, now);

		long seconds = duration.getSeconds();
		long hours = seconds / SECONDS_PER_HOUR;

		return hours;
	}

	private Map<String, TreeSet<WorkerData>> sortWorkerData(List<WorkerData> strarry) {
		if (!strarry.isEmpty()) {
			HashMap<String, TreeSet<WorkerData>> workMap = new HashMap<>();

			for (WorkerData wd : strarry) {
				String dateStr = getTheDate(wd.getDocument().getEvent_time(), dateFormat).toString();

				String key = wd.getDocument().getTag_id().trim() + "-" + dateStr.trim();
				TreeSet<WorkerData> workArray;
				if (workMap.get(key) == null) {
					workArray = new TreeSet<WorkerData>(
							(o1, o2) -> o1.getDocument().getEventDateTime().compareTo(o2.getDocument().getEventDateTime()));
				} else {
					workArray = workMap.get(key);
				}
				workArray.add(wd);
				workMap.put(key, workArray);
			}
			return workMap;
		}
		return null;
	}

	private void print(Map<String, TreeSet<WorkerData>> map) {
		Iterator<Map.Entry<String, TreeSet<WorkerData>>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, TreeSet<WorkerData>> entry = entries.next();
			System.out.println(" Key = " + entry.getKey() + " Values Size = " + entry.getValue().size());
			for (WorkerData wd : entry.getValue()) {
				System.out.println(" value =" + wd.getDocument().getEvent_time());
			}
		}

		System.out.println(" -----------------------///////////////////////////////------------------");
	}
}
