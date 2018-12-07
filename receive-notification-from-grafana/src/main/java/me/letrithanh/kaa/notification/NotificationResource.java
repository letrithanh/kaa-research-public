package me.letrithanh.kaa.notification;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Stateless
@Path("/receive-notification")
public class NotificationResource {

	@EJB
	NotificationService notificationService;
	
	@Context
	ServletContext servletContext;
	
	@POST
	@Path("/receive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receive(String content) throws ClientProtocolException, IOException {
		System.out.println("=======================================");
		System.out.println("Get notification from grafana");
		notificationService.show(content);
		System.out.println("=======================================");
		
		JsonObject jsonObject = new JsonParser().parse(content).getAsJsonObject();
		if(notificationService.isValidMessageWithCondition(jsonObject.get("message").getAsString())) {			
			File file = new File(servletContext.getResource("/file.json").getPath());
			File notification = new File(servletContext.getResource("/notification.json").getPath());
			
			notificationService.send(notification, file);
		}
		
		System.out.println("=======================================");
		return Response.status(Status.OK).build();
	}
	
}
