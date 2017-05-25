package crud;

import java.util.Iterator;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import dynamodb.Connector;

public class CRUDOperator {

	public void createTable(DynamoDB db) {
//		db.createTable(req)
	}
	
	public static void main(String[] args) {
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		DynamoDB myDB = new  Connector().getClient(client);
//		 
//	System.out.println (" It should not be null " +myDB.getTable("IIOT"));
		
		ToCreateTable tCT = new ToCreateTable();
//		tCT.deleteTable(myDB, "Users");
//		tCT.createTable(myDB, "Users", 10L, 5L,  "UserId", "S");
//		
//		tCT.deleteTable(myDB, "IIOT");
//		tCT.deleteTable(myDB, "MyTable");
//		System.out.println(" It should not be null " +myDB.getTable("worker"));
//		tCT.updateTable(myDB, "WorkerData");
//		System.out.println(" It should not be null " +myDB.getTable("worker"));
		printData(client, "Users");
	}
	
	 public static void printData(AmazonDynamoDBClient client, String tbl) {
	        ScanRequest scanRequest = new ScanRequest()
					   .withTableName(tbl);
					
			ScanResult result = client.scan(scanRequest);
			for(Map<String, AttributeValue> item : result.getItems()) {
				Iterator itert = item.entrySet().iterator();
				System.out.println(" ITem date ==== >>>> "+item.get("document")/*.getM().get("worker_id").getS()*/);
				while(itert.hasNext()){
					Map.Entry pair = (Map.Entry)itert.next();
			        
			        if(pair.getKey().equals("document"))
			        {
			        	System.out.println(" !!!!!!!!!  " +pair.getKey() + " <<= == > " + pair.getValue());
			        }
				}
			}
	 }
	       
}
