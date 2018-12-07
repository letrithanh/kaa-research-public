package me.letrithanh.mongodb.simpleforecast.global;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class GlobalVariablesServices {

	@PersistenceContext
	EntityManager em;
	
	public int getLengthOfCollection() {
		
		TypedQuery<ForecastGlobalVariables> query = em.createNamedQuery("ForecastGlobalVariables.getByName", ForecastGlobalVariables.class);
		query.setParameter("varName", GlobalVariables.LENGTH_OF_COLLECTION.getVar());
		
		ForecastGlobalVariables var = (ForecastGlobalVariables) query.getSingleResult();
		int lengthOfCollection = Integer.parseInt(var.getValue());
		
		return lengthOfCollection;
	}
	
	public void setLengthOfCollection(int value) {
		
		TypedQuery<ForecastGlobalVariables> query = em.createNamedQuery("ForecastGlobalVariables.getByName", ForecastGlobalVariables.class);
		query.setParameter("varName", GlobalVariables.LENGTH_OF_COLLECTION.getVar());
		
		ForecastGlobalVariables var = (ForecastGlobalVariables) query.getSingleResult();
		var.setValue("" + value);
		
		em.persist(var);
	}
}
