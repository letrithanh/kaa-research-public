package me.letrithanh.mongodb.simpleforecast.data;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import me.letrithanh.mongodb.simpleforecast.global.ForecastGlobalVariables;
import me.letrithanh.mongodb.simpleforecast.global.GlobalVariables;

@Startup
@Singleton
public class ForecastStartup {

	@PersistenceContext
	private EntityManager em;
	
	@PostConstruct
	public void init() {
//		ForecastGlobalVariables lengthOfCollection = new ForecastGlobalVariables();
//		lengthOfCollection.setName(GlobalVariables.LENGTH_OF_COLLECTION.getVar());
//		lengthOfCollection.setValue("0");
//		
//		this.em.persist(lengthOfCollection);
	}
	
}
