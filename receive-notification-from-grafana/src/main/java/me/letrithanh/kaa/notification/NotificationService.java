package me.letrithanh.kaa.notification;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.Stateless;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

@Stateless
public class NotificationService {
	
	private static final String URI_NOTIFICATION = "http://18.136.207.32:8080/kaaAdmin/rest/api/sendNotification";
	
	private static final String TEST_MESSAGE = "Someone is testing the alert notification within grafana.";
	private static final String TEMPERATURE_GREATER_THAN_28 = "nhiet do hon 28";

	public void show(String str) {
		System.out.println("Receive Json string");
		System.out.println(str);
	}
	
	public void send(File notification, File file) throws ClientProtocolException, IOException{
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(URI_NOTIFICATION);
	    
	    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	    builder.addBinaryBody("notification", notification,
	      ContentType.APPLICATION_JSON, "notification.json");
	    builder.addBinaryBody("file", file,
	  	      ContentType.APPLICATION_OCTET_STREAM, "file.json");
	    
	    HttpEntity multipart = builder.build();
	    httpPost.addHeader("Authorization", "Basic ZGV2dXNlcjpkZXZ1c2VyMTIz");
	    httpPost.setEntity(multipart);
	 
	    CloseableHttpResponse response = client.execute(httpPost);
	    System.out.println(response.getStatusLine().getStatusCode());
	    client.close();
	}
	
	public boolean isValidMessageWithCondition(String message) {
		if(message.equalsIgnoreCase(TEST_MESSAGE) ||
				message.equalsIgnoreCase(TEMPERATURE_GREATER_THAN_28)) {
			return true;
		}
		return false;
	}
	
}
