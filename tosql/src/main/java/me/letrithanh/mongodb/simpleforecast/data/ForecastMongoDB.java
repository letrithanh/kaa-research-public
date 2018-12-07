package me.letrithanh.mongodb.simpleforecast.data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.bson.Document;

import me.letrithanh.mongodb.util.MongoDBUtil;

/**
 * 
 * @author Le Tri Thanh
 *
 */
public class ForecastMongoDB {
	
	private static final String SERVER_IP = "18.136.207.32";
	
	private static final int MONGODB_PORT = 27017; 
	
	private static final String DATABASE_NAME = "kaa";
	
	private static final String COLLECTION_NAME = "logs_52097501962211336729";
	
	public ArrayList<ForecastData> getAllForecastData() {
		List<Document> documents = MongoDBUtil.getAllDocuments(SERVER_IP, MONGODB_PORT,
																DATABASE_NAME, COLLECTION_NAME);
		
		List<ForecastData> listForecastData = new ArrayList<ForecastData>();
		
		for(Document document: documents) {
			Document dEvent = (Document) document.get("event");
			
			LocalDateTime measuredAt = getMeasureAt(document);
			double temperature = dEvent.getDouble("temperature");
			double humidity = dEvent.getDouble("humidity");
			
			ForecastData forecastData = new ForecastData(measuredAt, temperature, humidity);
			listForecastData.add(forecastData);
		}
		
		return (ArrayList<ForecastData>) listForecastData;
	}
	
	public ArrayList<ForecastData> getAllForecastDataAtCurrentDate() {
		List<Document> documents = MongoDBUtil.getAllDocumentsAtCurrentDay(SERVER_IP, MONGODB_PORT,
																DATABASE_NAME, COLLECTION_NAME);
		
		List<ForecastData> listForecastData = new ArrayList<ForecastData>();
		
		for(Document document: documents) {
			Document dEvent = (Document) document.get("event");
			
			LocalDateTime measuredAt = getMeasureAt(document);
			double temperature = dEvent.getDouble("temperature");
			double humidity = dEvent.getDouble("humidity");
			
			ForecastData forecastData = new ForecastData(measuredAt, temperature, humidity);
			listForecastData.add(forecastData);
		}
		
		return (ArrayList<ForecastData>) listForecastData;
	}
	
	/**
	 * Get Timestamp in document get from mongodb and parse to @see {LocalDateTime}
	 * @param document
	 * @return
	 */
	private LocalDateTime getMeasureAt(Document document) {
		Document dTimestamp = (Document) document.get("header");
		dTimestamp = (Document) dTimestamp.get("timestamp");
		long timestamp = dTimestamp.getLong("long");
		
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
	}
	
	public long getLengthOfCollection() {
		return MongoDBUtil.getLengthOfSpecificCollection(SERVER_IP, MONGODB_PORT,
															DATABASE_NAME, COLLECTION_NAME);
	}
}
