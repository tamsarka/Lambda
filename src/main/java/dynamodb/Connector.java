package dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;;

public class Connector {

	public DynamoDB getClient(AmazonDynamoDBClient client){
		
		// Modify the client so that it accesses a different region.
		//pallav Ip -172.21.29.44
		client.withEndpoint("http://localhost:8000");
		return  new DynamoDB(client);
		
//		return new DynamoDB(AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(new EndpointConfiguration("http://localhost:8000", "us-east-1")).build());

		
	}
}