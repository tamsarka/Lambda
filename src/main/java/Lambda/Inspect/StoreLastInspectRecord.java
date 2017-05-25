package Lambda.Inspect;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;

import Lambda.Inspect.model.MyWorkerZoneModel;
import dynamodb.Connector;

public class StoreLastInspectRecord {
	String storeTableName = "WorkerZoneDetails";
    String getTableName = "Tracker";
	public static void main(String[] args) {

		StoreLastInspectRecord inspectLastDayRecord = new StoreLastInspectRecord();

		inspectLastDayRecord.handleRequest(null);

	}

	public void scan(){
		AmazonDynamoDBClient client = new AmazonDynamoDBClient().withRegion(Regions.US_EAST_1);
		DynamoDB db = new DynamoDB(client);
		
		Map<String, AttributeValue> expressionAttributeValues = new HashMap<String, AttributeValue>();
		expressionAttributeValues.put(":v_Zone_id", new AttributeValue().withS("Z2"));
		
		ScanRequest scanRequest = new ScanRequest().withTableName("Zone")
				.withFilterExpression("ZoneId = :v_Zone_id")
				.withExpressionAttributeValues(expressionAttributeValues);

		ScanResult result = client.scan(scanRequest);
		
		for (Map<String, AttributeValue> item : result.getItems()) {

//			System.out.println("item:" + item.toString());

			item.get("document").getM().get("Corners");
		}
		
		
	}
	public void handleRequest(Context arg1) {
		scan();
		/*AmazonDynamoDBClient client = new AmazonDynamoDBClient().withRegion(Regions.US_EAST_1);

		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String toDate = ft.format(cal.getTime());
		cal.add(Calendar.DATE, -1);
		String fromDate = ft.format(cal.getTime());
//		System.out.println("currentDate:" + fromDate + "lastDate:" + toDate);
		Map<String, WorkerZoneHistory> usersDurationList = new HashMap<String, WorkerZoneHistory>();
		
		HashMap<String, String> usersEnterTime = new HashMap<String, String>();

		try {
			Map<String, AttributeValue> expressionAttributeValues = new HashMap<String, AttributeValue>();
			expressionAttributeValues.put(":v_start_dt", new AttributeValue().withS(fromDate));
			expressionAttributeValues.put(":v_end_dt", new AttributeValue().withS(toDate));
			
			ScanRequest scanRequest = new ScanRequest().withTableName(getTableName)
					.withFilterExpression("event_time between :v_start_dt and :v_end_dt")
					.withExpressionAttributeValues(expressionAttributeValues);

			ScanResult result = client.scan(scanRequest);

			for (Map<String, AttributeValue> item : result.getItems()) {

//				System.out.println("item:" + item.toString());

				String durationSeconds = item.get("document").getM().get("event_time").getS();
				String userName = item.get("document").getM().get("worker_name").getS();
				String taskLocation = item.get("document").getM().get("zone_name").getS();
				String tagId = item.get("document").getM().get("tag_id").getS();
				LocalDateTime fromdate = LocalDateTime.parse(item.get("document").getM().get("event_time").getS(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				String date = fromdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				
				String keyAsUserName = tagId + "--" +date;
				

				if (item.get("document").getM().get("event_flag").getS().equals("Enter")) {
					usersEnterTime.put(userName, durationSeconds);

				} else if (item.get("document").getM().get("event_flag").getS().equals("Exit")) {
					String durationSecondsbefore = usersEnterTime.get(userName);

					if (durationSecondsbefore != null) {
						WorkerZoneHistory workerZoneHistory = usersDurationList.get(keyAsUserName) == null ? new WorkerZoneHistory() : usersDurationList.get(keyAsUserName);
						List<StartTimeList> timeList = workerZoneHistory.getZoneHistory();

						Long duration = timeDiiference(durationSecondsbefore, durationSeconds);

						String colorZone = null;

						if (taskLocation.equals("Zone 1")) {
							colorZone = "#46615e";
						} else if (taskLocation.equals("Zone 2")) {
							colorZone = "#727d6f";
						} else if (taskLocation.equals("Zone 3")) {
							colorZone = "#8dc49f";
						} else {
							colorZone = "#51b46d";
						}

						if (timeList == null) {
							timeList = new ArrayList<StartTimeList>();
							int startTime = getHours(durationSecondsbefore);
							StartTimeList data = new StartTimeList(duration, colorZone, taskLocation, startTime);

							timeList.add(data);
							workerZoneHistory.setWorkerName(userName);
							workerZoneHistory.setZoneHistory(timeList);
							usersDurationList.put(keyAsUserName, workerZoneHistory);
						} else {
							StartTimeList data = new StartTimeList(duration, colorZone, taskLocation);
							timeList.add(data);
						}
					}
				}
			}

		} catch (Exception e) {

			System.err.println("unable to get the last day Data: " + e.getMessage());
			e.printStackTrace();
		}
		insertInspectRecord(usersDurationList);*/
	}
	
	private void insertInspectRecord( Map<String, WorkerZoneHistory>  usersDurationList){
		/*AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		DynamoDB myDB = new  Connector().getClient(client);
		Table table = myDB.getTable("WorkerZoneDetails");*/

		try{
			ObjectMapper objectMapper = new ObjectMapper();
					
			for (Map.Entry<String, WorkerZoneHistory> entry : usersDurationList.entrySet()) {
				String arrayToJson ="";
				WorkerZoneHistory workerZoneHistory = entry.getValue();
				String key = entry.getKey();
				String[] strValues = key.split("--");
				arrayToJson = "{\""
								 +workerZoneHistory.getWorkerName() 
								 + "\":"
								 +objectMapper.writeValueAsString(workerZoneHistory.getZoneHistory())
								 +"}";
				
				System.out.println("Key = " + key + ", Value = " + arrayToJson);
				/*Item item = new Item()
		        		.withPrimaryKey("tag_id", strValues[0],"date",strValues[1])
		                .withJSON("document", arrayToJson);
				
				table.putItem(item);*/
				AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(new ProfileCredentialsProvider());

				DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
				
				
				MyWorkerZoneModel item = new MyWorkerZoneModel();
				
				item.setDate(strValues[1]);
				item.setTagId(strValues[0]+ "d");
				item.setDocs(arrayToJson);
				
				mapper.save(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long timeDiiference(String prevTime, String currentTime) {
		long x = 0;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = formatter.parse(currentTime);
			Date date2 = formatter.parse(prevTime);
			double diff = date1.getTime() - date2.getTime();
			x = Math.round(diff / (1000 * 60));
//			System.out.println("minut sec " + x);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return x;
	}

	public int getHours(String time) {
		int x = 0;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = formatter.parse(time);
			x = date1.getHours();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}

}