package me.letrithanh.mongodb.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.bson.BsonInt64;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * @author Le Tri Thanh
 *
 */
public class MongoDBUtil {
	
	/**
	 * Get all documents from MongoDB by specific collection name
	 * @param serverIP  IP address where host MongoDB
	 * @param port  port to connect to MongoDB
	 * @param databaseName  name of which database want to connect
	 * @param collectionName  name of which collection want to get data
	 * @return ArrayList contain all documents of collection which want to get data
	 */
	public static ArrayList<Document> getAllDocuments(String serverIP, int port, String databaseName, String collectionName) {
		MongoClient mongoClient = new MongoClient(serverIP, port);
		MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
		MongoCollection<Document> collections = mongoDatabase.getCollection(collectionName);
		
		final List<Document> documents = new ArrayList<Document>();
		
		Block<Document> blockDocuments = new Block<Document>() {

			public void apply(Document document) {
				documents.add(document);				
			}
			
		};
		
		collections.find().forEach(blockDocuments);
		
		mongoClient.close();
		
		return (ArrayList<Document>) documents;
	}
	
	public static ArrayList<Document> getAllDocumentsAtCurrentDay(String serverIP, int port, String databaseName, String collectionName) {
		MongoClient mongoClient = new MongoClient(serverIP, port);
		MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
		MongoCollection<Document> collections = mongoDatabase.getCollection(collectionName);
		
		final List<Document> documents = new ArrayList<Document>();
		
		Block<Document> blockDocuments = new Block<Document>() {

			public void apply(Document document) {
				documents.add(document);				
			}
			
		};
		
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime currentDate = localDateTime.of(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(),
				0, 0, 0);
		long currentDateInEpochMilli = currentDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		
		BasicDBObject filter = new BasicDBObject("header.timestamp.long",
								new BasicDBObject("$gt", new BsonInt64(currentDateInEpochMilli)));
		
		collections.find(filter).forEach(blockDocuments);
		
		mongoClient.close();
		
		return (ArrayList<Document>) documents;
	}
	
	public static long getLengthOfSpecificCollection(String serverIP, int port, String databaseName, String collectionName) {
		MongoClient mongoClient = new MongoClient(serverIP, port);
		MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
		MongoCollection<Document> collections = mongoDatabase.getCollection(collectionName);
		
		long lengthOfCollection = collections.countDocuments();
		
		mongoClient.close();
		
		return lengthOfCollection;
	}
}
