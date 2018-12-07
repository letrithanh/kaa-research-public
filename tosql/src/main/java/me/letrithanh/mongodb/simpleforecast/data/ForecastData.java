package me.letrithanh.mongodb.simpleforecast.data;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Le Tri Thanh
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public @Data class ForecastData {
	
	/**
	 * Time when server received the data from devices
	 */
	private LocalDateTime measuredAt;
	
	/**
	 * Value of temperature surround the devices
	 */
	private double temperature;
	
	/**
	 * Value of humidity surround the devices
	 */
	private double humidity;
	
	public ForecastDataEntity toEntity() {
		ForecastDataEntity forecastDataEntity = new ForecastDataEntity();
		forecastDataEntity.setTemperature(this.getTemperature());
		forecastDataEntity.setHumidity(this.getHumidity());
		forecastDataEntity.setMeasuredAt(this.getMeasuredAt());
		
		return forecastDataEntity;
	}
}
