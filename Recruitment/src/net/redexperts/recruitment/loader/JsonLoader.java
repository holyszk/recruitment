package net.redexperts.recruitment.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import net.redexperts.recruitment.data.Place;
import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonLoader extends AsyncTask<String, Void, Place> {

	@Override
	protected Place doInBackground(String... urls) {
		
		try {
			return getJson(urls[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private Place getJson(String jsonUrl) throws IOException {
		InputStream is = null;
		HttpsURLConnection connection = null;
		Place place = null;
		
		try {
			URL url = new URL(jsonUrl);
			connection = (HttpsURLConnection) url.openConnection();
			connection.connect();
			is = connection.getInputStream();
			
			ObjectMapper mapper = new ObjectMapper();
			place = mapper.readValue(is, Place.class);
		} 
		finally {
			if(is != null)
				is.close();
			if(connection != null)
				connection.disconnect();
		}
		
		return place;
	}
}
