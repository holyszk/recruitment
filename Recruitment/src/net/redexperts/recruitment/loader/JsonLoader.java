package net.redexperts.recruitment.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.os.AsyncTask;

public class JsonLoader extends AsyncTask<String, Void, Void> {

	@Override
	protected Void doInBackground(String... urls) {
		
		try {
			return getJson(urls[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private Void getJson(String jsonUrl) throws IOException {
		InputStream is = null;
		HttpsURLConnection connection = null;
		
		try {
			URL url = new URL(jsonUrl);
			connection = (HttpsURLConnection) url.openConnection();
			connection.connect();
			is = connection.getInputStream();
			
			//TODO: parse json
		} 
		finally {
			if(is != null)
				is.close();
			if(connection != null)
				connection.disconnect();
		}
		
		return null;
	}	
}
