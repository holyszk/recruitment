package net.redexperts.recruitment.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public abstract class BasicLoader<DataType> extends AsyncTask<String, Void, DataType> {

	@Override
	protected DataType doInBackground(String... urls) {
		try {
			return getData(urls[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private DataType getData(String dataUrl) throws IOException {
		InputStream is = null;
		HttpURLConnection connection = null;
		DataType data = null;
		
		try {
			URL url = new URL(dataUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			is = connection.getInputStream();
			
			data = parseData(is);
		}
		finally {
			if(is != null)
				is.close();
			if(connection != null)
				connection.disconnect();
		}
		
		return data;
	}
	
	protected abstract DataType parseData(InputStream is);

}
