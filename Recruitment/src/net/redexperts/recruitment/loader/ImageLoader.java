package net.redexperts.recruitment.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

	@Override
	protected Bitmap doInBackground(String... urls) {
		try {
			return getImage(urls[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private Bitmap getImage(String imageUrl) throws IOException {
		InputStream is = null;
		HttpsURLConnection connection = null;
		Bitmap image = null;
		
		try {
			URL url = new URL(imageUrl);
			connection = (HttpsURLConnection) url.openConnection();
			connection.connect();
			is = connection.getInputStream();
			
			image = BitmapFactory.decodeStream(is);
		} 
		finally {
			if(is != null)
				is.close();
			if(connection != null)
				connection.disconnect();
		}
		
		return image;
	}

}
