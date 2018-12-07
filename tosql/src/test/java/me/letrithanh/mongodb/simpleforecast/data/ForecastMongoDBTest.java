package me.letrithanh.mongodb.simpleforecast.data;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class ForecastMongoDBTest {
	
	@Test
	public void whenGetAllForecastDataThenReturnListForecastData () {
		ForecastMongoDB forecastMongoDB = new ForecastMongoDB();
		List<ForecastData> listForecastData = forecastMongoDB.getAllForecastData();

		assertEquals(true, listForecastData.size() > 0);
	}
	
	@Test
	public void whenGetAllForecastDataAtCurrentDayThenReturnListForecastData () {
		ForecastMongoDB forecastMongoDB = new ForecastMongoDB();
		List<ForecastData> listForecastData = forecastMongoDB.getAllForecastDataAtCurrentDate();

		System.out.println("size: " + listForecastData.size());
		assertEquals(true, listForecastData.size() >= 0);
	}
}
