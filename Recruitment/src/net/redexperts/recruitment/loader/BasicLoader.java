package net.redexperts.recruitment.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

public abstract class BasicLoader<DataType> extends AsyncTask<String, Void, DataType> {
	
	private List<Callbacks<DataType>> listeners;
	
	public interface Callbacks<T> {
		public void onPostExecute(T data);
	}
	
	public BasicLoader() {
		listeners = new ArrayList<Callbacks<DataType>>();
	}
	
	public void addListener(Callbacks<DataType> listener) {
		listeners.add(listener);
	}
	
	public void removeListener(Callbacks<DataType> listener) {
		listeners.remove(listener);
	}

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
	
	@Override
	protected void onPostExecute(DataType result) {
		for(Callbacks<DataType> listener : listeners) 
			listener.onPostExecute(result);
	}
	
	protected abstract DataType parseData(InputStream is);

}
