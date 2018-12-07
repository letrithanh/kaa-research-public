package me.letrithanh.mongodb.simpleforecast.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import me.letrithanh.mongodb.simpleforecast.global.GlobalVariablesServices;

/**
 * 
 * @author Le Tri Thanh
 *
 */
@Stateless
public class ForecastService {
	
	@PersistenceContext
	EntityManager em;
	
	@EJB
	private GlobalVariablesServices globalVariablesServices;
	
	private ForecastMongoDB forecastMongoDB;
	
	/**
	 * Get all data from forecast collection in MongoDB @see{ForecastMongoDB} and insert to SQL Database
	 */
	public void copyDataFromMongoDBToSQL() {
		
		forecastMongoDB = new ForecastMongoDB();
		List<ForecastData> listForecastData = forecastMongoDB.getAllForecastData();
		
		for(ForecastData forecastData: listForecastData) {
			ForecastDataEntity forecastDataEntity = forecastData.toEntity();
			this.em.persist(forecastDataEntity);
		}
		
	}
	
	public void copyNewDataFromMongoDBToSQL() {
		
		long previousConvertLength = globalVariablesServices.getLengthOfCollection();
		
		forecastMongoDB = new ForecastMongoDB();
		long currentConvertLength = forecastMongoDB.getLengthOfCollection();
		
		if(previousConvertLength < currentConvertLength) {
			List<ForecastData> listForecastData = forecastMongoDB.getAllForecastData();
			for(long i = previousConvertLength; i < currentConvertLength; i++) {
				ForecastData forecastData = listForecastData.get((int) i);
				ForecastDataEntity forecastDataEntity = forecastData.toEntity();
				this.em.persist(forecastDataEntity);
			}
			globalVariablesServices.setLengthOfCollection((int) currentConvertLength);
		}
		
	}
	
}
