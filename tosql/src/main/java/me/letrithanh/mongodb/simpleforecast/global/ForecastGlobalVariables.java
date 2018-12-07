package me.letrithanh.mongodb.simpleforecast.global;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_simple_forecast_global_variable")
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
	@NamedQuery(name = "ForecastGlobalVariables.getByName", query = "SELECT v FROM ForecastGlobalVariables v WHERE v.name = :varName")
})
public @Data class ForecastGlobalVariables {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "name", unique = true)
	private String name;
	
	@Column(name = "value", nullable = false)
	private String value;

	public ForecastGlobalVariables(String name, String value) {
		this.name = name;
		this.value = value;
	}
}
