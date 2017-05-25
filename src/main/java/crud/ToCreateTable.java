package crud;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;


import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ToCreateTable {
	static SimpleDateFormat dateFormatter = new SimpleDateFormat(
		    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	final String documentValue = "{"
		    +"\"actual_hours\": 10,"
		    +"\"assigned_zones\": ["
		    +"{"
		    +" \"corners\": {"
		    +" \"lefttopx\": \"0\","
		    +" \"lefttopy\": \"0\","
		    +"\"rightbottomx\": \"950\","
		    +"   \"rightbottomy\": \"250\""
		    +"   },"
		    +"   \"zone_id\": \"SZ1\","
		    +"    \"zone_name\": \"Zone 1\""
		    +" }"
		    +"],"
		    +"\"availability\": \"true\","
		    +"\"breach_count\": 24,"
		    +"\"breadth\": 0,"
		    +" \"expected_hours\": \"8\","
		    +" \"last_seen_location\": \"Zone 3\","
		    +" \"last_seen_lo.cation_id\": \"SZ1\","
		    +"\"last_seen_status\": \"In Zone\","
		    +"\"last_seen_time\": \"2017-03-02 10:42:38\","
		    +"\"length\": 0,"
		    +"\"present_at_site\": \"Yes\","
		    +"\"supervisor_emailid\": \"prashant.sinha@lntinfotech.com\","
		    +" \"tag_id\": \"Anuj_TT_33\","
		    +"\"tag_name\": \"Anuj_TT_33\","
		    +"\"type\": \"Worker\","
		    +"\"worker_email_id\": \"anujKumar.tripathi@lntinfotech.com\","
		    +"\"worker_id\": \"10612178\","
		    +"\"worker_image_name\": \"anujDP.jpg\","
		    +"\"worker_name\": \"Anuj Tripathi\","
		    +"\"worker_role\": \"Technician\","
		    +"\"worker_supervisor\": \"Prashant\""
		  +"}";
	
	
			/*"{"
			    +"\"actual_hours\": 7,"
			    +"\"assigned_zones\": ["
			      +"{"
			        +"\"Corners\": {"
			          +"\"lefttopx\": \"0\","
			          +"\"lefttopy\": \"250\","
			          +"\"rightbottomx\": \"466\","
			          +"\"rightbottomy\": \"445\""
			          +"},"
			          +"\"zone_id\": \"SZ3\","
			        +"\"zone_name\": \"Zone 3\""
			        +"}"
			        +"],"
			    +"\"availability\": \"true\","
			    +"\"breach_count\": 37,"
			    +"\"breadth\": 0,"
			    +"\"expected_hours\": \"8\","
			   +"\"last_seen_location\": \"Zone 2\","
			    +"\"last_seen_location_id\": \"SZ2\","
			    +"\"last_seen_status\": \"breach\","
			    +"\"last_seen_time\": \"2017-03-15 02:00:39\","
			    +"\"length\": 0,"
			    +"\"present_at_site\": \"Yes\","
			    +"\"supervisor_emailid\": \"NRK@lntinfotech.com\","
			    +"\"tag_id\": \"Prasenjit_PT_C7\","
			    +"\"tag_name\": \"Prasenjit_PT_C7\","
			    +"\"type\": \"Worker\","
			    +"\"worker_email_id\": \"prasenjit.pandit@lntinfotech.com\","
			    +"\"worker_id\": \"10618926\","
			    +"\"worker_image_name\": \"prasenjitDP.jpg\","
			    +"\"worker_name\": \"Prasenjit Pandit\","
			    +"\"worker_role\": \"Technician\","
			    +"\"worker_supervisor\": \"NRK\""
			    +"}";*/
			
			/*"{"
			    +"\"actual_hours\": 7,"
			    +" \"assigned_zones\": ["
			    +"{"
			       +" \"corners\": {"
			        +"  \"lefttopx\": \"150\","
			       +"   \"lefttopy\": \"105\","
			        +"  \"rightbottomx\": \"850\","
			         +" \"rightbottomy\": \"270\""
			         +"},"
			         +"\"zone_id\": \"SZ1\","
			        +"\"zone_name\": \"Zone 1\""
			        +"},"
			        +"{"
			        +"\"corners\": {"
			          +"\"lefttopx\": \"150\","
			          +"\"lefttopy\": \"325\","
			          +"\"rightbottomx\": \"470\","
			          +"\"rightbottomy\": \"480\""
			          +"},"
			          +"\"zone_id\": \"SZ2\","
			        +"\"zone_name\": \"Zone 2\""
			        +"}"
			        +"],"
			    +"\"availability\": \"true\","
			    +"\"breach_count\": 38,"
			    +"\"breadth\": 0,"
			    +"\"expected_hours\": \"8\","
			    +"\"last_seen_location\": \"Zone 1\","
			    +"\"last_seen_location_id\": \"sz1\","
			    +"\"last_seen_status\": \"breach\","
			    +"\"last_seen_time\": \"2017-03-16 14:00:08\","
			    +"\"length\": 0,"
			    +"\"present_at_site\": \"Yes\","
			    +"\"supervisor_emailid\": \"NRK@lntinfotech.com\","
			    +"\"tag_id\": \"Prashant_PT_7E\","
			    +"\"tag_name\": \"Prashant_PT_7E\","
			    +"\"type\": \"Worker\","
			    +"\"worker_email_id\": \"prasanth.sinha@lntinfotech.com\","
			    +"\"worker_id\": \"10672433\","
			    +"\"worker_image_name\": \"prashantDP.jpg\","
			    +"\"worker_name\": \"Prashant Sinha\","
			    +"\"worker_role\": \"Technician\","
			    +"\"worker_supervisor\": \"NRK\""
			    +"}";*/
			
			
			
			
			
			/*"{"
			    +"\"actual_hours\": 10,"
			    +"\"assigned_zones\": ["
			    +"{"
			    +" \"corners\": {"
			    +" \"lefttopx\": \"0\","
			    +" \"lefttopy\": \"0\","
			    +"\"rightbottomx\": \"950\","
			    +"   \"rightbottomy\": \"250\""
			    +"   },"
			    +"   \"zone_id\": \"SZ1\","
			    +"    \"zone_name\": \"Zone 1\""
			    +" }"
			    +"],"
			    +"\"availability\": \"true\","
			    +"\"breach_count\": 24,"
			    +"\"breadth\": 0,"
			    +" \"expected_hours\": \"8\","
			    +" \"last_seen_location\": \"Zone 3\","
			    +" \"last_seen_lo.cation_id\": \"SZ1\","
			    +"\"last_seen_status\": \"In Zone\","
			    +"\"last_seen_time\": \"2017-03-02 10:42:38\","
			    +"\"length\": 0,"
			    +"\"present_at_site\": \"Yes\","
			    +"\"supervisor_emailid\": \"prashant.sinha@lntinfotech.com\","
			    +" \"tag_id\": \"Anuj_TT_33\","
			    +"\"tag_name\": \"Anuj_TT_33\","
			    +"\"type\": \"Worker\","
			    +"\"worker_email_id\": \"anujKumar.tripathi@lntinfotech.com\","
			    +"\"worker_id\": \"10612178\","
			    +"\"worker_image_name\": \"anujDP.jpg\","
			    +"\"worker_name\": \"Anuj Tripathi\","
			    +"\"worker_role\": \"Technician\","
			    +"\"worker_supervisor\": \"Prashant\""
			  +"}";*/
			
			
			
		/*static String productCatalogTableName = "ProductCatalog";
		static String forumTableName = "Forum";
		static String threadTableName = "Thread";
		static String replyTableName = "Reply";*/
	
	
		public void createTable(DynamoDB myDB,
		    String tableName, long readCapacityUnits, long writeCapacityUnits,
		    String partitionKeyName, String partitionKeyType
		    ) {

		    try {

		        ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
		        keySchema.add(new KeySchemaElement()
		                  .withAttributeName(partitionKeyName)
		                  .withKeyType(KeyType.HASH)); //Partition key

		        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		        attributeDefinitions.add(new AttributeDefinition()
		                     .withAttributeName(partitionKeyName)
		                     .withAttributeType(partitionKeyType));

		        /*if (sortKeyName != null) {
		            keySchema.add(new KeySchemaElement()
		                      .withAttributeName(sortKeyName)
		                      .withKeyType(KeyType.RANGE)); //Sort key
		            
		            attributeDefinitions.add(new AttributeDefinition()
		                         .withAttributeName(sortKeyName)
		                         .withAttributeType(sortKeyType));
		        }*/

		        CreateTableRequest request = new CreateTableRequest()
		                         .withTableName(tableName)
		                         .withKeySchema(keySchema)
		                         .withProvisionedThroughput( new ProvisionedThroughput()
		                                     .withReadCapacityUnits(readCapacityUnits)
		                                     .withWriteCapacityUnits(writeCapacityUnits));

		        // If this is the Reply table, define a local secondary index
		       /* if (replyTableName.equals(tableName)) {

		            attributeDefinitions.add(new AttributeDefinition()
		                         .withAttributeName("PostedBy")
		                         .withAttributeType("S"));

		            ArrayList<LocalSecondaryIndex> localSecondaryIndexes = new ArrayList<LocalSecondaryIndex>();
		            localSecondaryIndexes.add(new LocalSecondaryIndex()
		                          .withIndexName("PostedBy-Index")
		                          .withKeySchema(
		                              new KeySchemaElement().withAttributeName(partitionKeyName).withKeyType(KeyType.HASH), //Partition key
		                              new KeySchemaElement().withAttributeName("PostedBy").withKeyType(KeyType.RANGE)) //Sort key
		                          .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY)));

		            request.setLocalSecondaryIndexes(localSecondaryIndexes);
		        }*/

		        request.setAttributeDefinitions(attributeDefinitions);

		        
		        Table table = myDB.createTable(request);
		        table.waitForActive();

		    } catch (Exception e) {
		    	System.out.println("Issue in CreateTable request " + tableName);
		        System.err.println("CreateTable request failed for " + tableName);
		        System.err.println(e.getMessage());
		    }
		}
		public void createTableWithShortKey(DynamoDB myDB,
			    String tableName, long readCapacityUnits, long writeCapacityUnits,
			    String partitionKeyName, String partitionKeyType,String sortKeyName, String sortKeyType
			    ) {

			    try {

			        ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			        keySchema.add(new KeySchemaElement()
			                  .withAttributeName(partitionKeyName)
			                  .withKeyType(KeyType.HASH)); //Partition key

			        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			        attributeDefinitions.add(new AttributeDefinition()
			                     .withAttributeName(partitionKeyName)
			                     .withAttributeType(partitionKeyType));

			        if (sortKeyName != null) {
			            keySchema.add(new KeySchemaElement()
			                      .withAttributeName(sortKeyName)
			                      .withKeyType(KeyType.RANGE)); //Sort key
			            
			            attributeDefinitions.add(new AttributeDefinition()
			                         .withAttributeName(sortKeyName)
			                         .withAttributeType(sortKeyType));
			        }

			        CreateTableRequest request = new CreateTableRequest()
			                         .withTableName(tableName)
			                         .withKeySchema(keySchema)
			                         .withProvisionedThroughput( new ProvisionedThroughput()
			                                     .withReadCapacityUnits(readCapacityUnits)
			                                     .withWriteCapacityUnits(writeCapacityUnits));

			        // If this is the Reply table, define a local secondary index
			       /* if (replyTableName.equals(tableName)) {

			            attributeDefinitions.add(new AttributeDefinition()
			                         .withAttributeName("PostedBy")
			                         .withAttributeType("S"));

			            ArrayList<LocalSecondaryIndex> localSecondaryIndexes = new ArrayList<LocalSecondaryIndex>();
			            localSecondaryIndexes.add(new LocalSecondaryIndex()
			                          .withIndexName("PostedBy-Index")
			                          .withKeySchema(
			                              new KeySchemaElement().withAttributeName(partitionKeyName).withKeyType(KeyType.HASH), //Partition key
			                              new KeySchemaElement().withAttributeName("PostedBy").withKeyType(KeyType.RANGE)) //Sort key
			                          .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY)));

			            request.setLocalSecondaryIndexes(localSecondaryIndexes);
			        }*/

			        request.setAttributeDefinitions(attributeDefinitions);

			        
			        Table table = myDB.createTable(request);
			        table.waitForActive();

			    } catch (Exception e) {
			    	System.out.println("Issue in CreateTable request " + tableName);
			        System.err.println("CreateTable request failed for " + tableName);
			        System.err.println(e.getMessage());
			    }
			}
		
		public void deleteTable(DynamoDB db, String tableName) {
		    Table table = db.getTable(tableName);
		    try {
		        System.out.println("Issuing DeleteTable request for " + tableName);
		        table.delete();
		        System.out.println("Waiting for " + tableName
		                   + " to be deleted...this may take a while...");
		        table.waitForDelete();

		    } catch (Exception e) {
		        System.err.println("DeleteTable request failed for " + tableName);
		        System.err.println(e.getMessage());
		    }
		}
		
		public void updateTable(DynamoDB db, String tableName) {
		    try {
		        long time1 = (new Date()).getTime() - (7 * 24 * 60 * 60 * 1000); // 7
		        // days
		        // ago
		        long time2 = (new Date()).getTime() - (14 * 24 * 60 * 60 * 1000); // 14
		        // days
		        // ago
		        long time3 = (new Date()).getTime() - (21 * 24 * 60 * 60 * 1000); // 21
		        // days
		        // ago

		        Date date1 = new Date();
		        date1.setTime(time1);

		        Date date2 = new Date();
		        date2.setTime(time2);

		        Date date3 = new Date();
		        date3.setTime(time3);

		        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

		        Table table = db.getTable(tableName);

		        System.out.println("Adding data to " + tableName);
		        
		        JsonParser parser = new JsonFactory()
	            .createParser(new File("worker.json"));
		        
		        
		       /* Item item = new Item()
		                .withPrimaryKey("ForumName", "Amazon DB- ")
		                .withString("Subject", "DynamoDB Thread 1")
		                .withString("Message", "DynamoDB thread 1 message")
		                .withString("LastPostedBy", "User A")
		                .withString("LastPostedDateTime", dateFormatter.format(date2))
		                .withNumber("Views", 0)
		                .withNumber("Replies", 0)
		                .withNumber("Answered", 0);*/
		        
		        Item item = new Item()
//		                .withPrimaryKey("tag_id", "Prashant_PT_7E")
		        		.withPrimaryKey("tag_id", "Anuj_TT_31")
		                .withJSON("document", documentValue);
		        
		        table.putItem(item);
		        

		    } catch (Exception e) {
		        System.out.println("Failed to create item in " + tableName);
		        System.err.println(e.getMessage());
		    }
		    
		}
		private String jsonPath(JsonParser parser, String node) throws JsonProcessingException, IOException  {
	    	


	        /*JsonParser parser = new JsonFactory()
	            .createParser(new File("moviedata.json"));*/
	    	String nodePath = "";
	        JsonNode rootNode = new ObjectMapper().readTree(parser);
	        Iterator<JsonNode> iter = rootNode.iterator();

	        ObjectNode currentNode;

	        while (iter.hasNext()) {
	            currentNode = (ObjectNode) iter.next();
//	            nodePath = currentNode.path(node).toString();
	            return currentNode.toString();
	    }
	        return nodePath;
	}
		
}
