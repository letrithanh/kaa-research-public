package me.letrithanh.mongodb.simpleforecast.data;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/forecasts")
public class ForecastResource {
	
	@EJB
	ForecastService forecastService;
	
	@GET
	@Path("/tosql")
	@Produces({MediaType.APPLICATION_JSON})
	public Response copyForecastDataFromMongoDBToSQL() {
		forecastService.copyDataFromMongoDBToSQL();
		
		return Response.status(Response.Status.OK).build();
	}

	@GET
	@Path("/new-data")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response copyNewDataFromMongoDBToSQL() {
		forecastService.copyNewDataFromMongoDBToSQL();

		return Response.status(Response.Status.OK).build();
	}

}
