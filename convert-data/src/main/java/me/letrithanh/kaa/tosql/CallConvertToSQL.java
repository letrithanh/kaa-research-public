package me.letrithanh.kaa.tosql;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class CallConvertToSQL {
	
	private static String URI_CONVERT_NEW_DATA = "http://18.188.50.242:8888/tosql/api/forecasts/new-data";

	public static void main(String args[]) throws ClientProtocolException, IOException, InterruptedException {
		
		while(true) {
			callConvertToSQL();
			Thread.sleep(5000);
		}
	    
	}
	
	private static void callConvertToSQL() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(URI_CONVERT_NEW_DATA);
		CloseableHttpResponse response = client.execute(httpGet);
		
		if(response.getStatusLine().getStatusCode() != 200) {
			System.out.println(response.toString());
		}
		
	    client.close();
	}
}
