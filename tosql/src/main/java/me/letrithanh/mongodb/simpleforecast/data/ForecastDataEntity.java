package me.letrithanh.mongodb.simpleforecast.data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Le Tri Thanh
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_forecast")
public @Data class ForecastDataEntity {
	
	/**
	 * The identified for each forecast data entity
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	/**
	 * Time when server received the data from devices
	 */
	@Column(name = "time", nullable = false)
	private LocalDateTime measuredAt;
	
	/**
	 * Value of temperature surround the devices
	 */
	@Column(name = "temperature", nullable = false)
	private double temperature;
	
	/**
	 * Value of humidity surround the devices
	 */
	@Column(name = "humidity", nullable = false)
	private double humidity;
}
