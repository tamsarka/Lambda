package Lambda.Inspect.model;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

public class Main {

	
	public static void main(String[] args) {
		AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
		DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
		
		
//		MyWorkerZoneModel row = mapper.load(MyWorkerZoneModel.class, "Anuj_TT_31", "2017-03-30", DynamoDBMapperConfig.ConsistentReads.EVENTUAL);

//		System.out.print(row.getTagId());
	}
}
