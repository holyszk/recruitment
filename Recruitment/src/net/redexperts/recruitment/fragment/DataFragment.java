package net.redexperts.recruitment.fragment;

import net.redexperts.recruitment.R;
import net.redexperts.recruitment.data.Place;
import net.redexperts.recruitment.data.PlaceWithImage;
import net.redexperts.recruitment.loader.BasicLoader.Callbacks;
import net.redexperts.recruitment.loader.ImageLoader;
import net.redexperts.recruitment.loader.JsonLoader;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

//Store data during configuration changes
public class DataFragment extends Fragment {
	
	private PlaceWithImage placeWithImage;
	private OnLoadFinished listener;
	
	private boolean isLoadFinished = false;
	private boolean isDataRequested = false;
	
	private Callbacks<Place> jsonCallbacks = new Callbacks<Place>() {

		@Override
		public void onPostExecute(Place data) {
			if(data != null) {
				placeWithImage = new PlaceWithImage();
				placeWithImage.setPlace(data);
				if(!isOnline()) {
					Toast.makeText(getActivity(), getString(R.string.no_connection), 
							Toast.LENGTH_LONG).show();
				}
				else {
					ImageLoader loader = new ImageLoader();
					loader.addListener(imageCallbacks);
					loader.execute(data.getImage());
				}
			}
			else
				Toast.makeText(getActivity(), getString(R.string.invalid_request), 
						Toast.LENGTH_LONG).show();
		}
	};
	
	private Callbacks<Bitmap> imageCallbacks = new Callbacks<Bitmap>() {

		@Override
		public void onPostExecute(Bitmap data) {
			if(data != null) {
				placeWithImage.setImage(data);
				isLoadFinished = true;
				if(listener != null && isDataRequested) 
				{
					isDataRequested = false;
					listener.onLoadFinished(placeWithImage);
				}
			}
			else
				Toast.makeText(getActivity(), getString(R.string.invalid_request), 
						Toast.LENGTH_LONG).show();
		}
	};
	
	public interface OnLoadFinished {
		public void onLoadFinished(PlaceWithImage placeWithImage);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		listener = (OnLoadFinished) activity;
	}
	
	public void dataRequest() {
		if(isLoadFinished)
		{
			if(listener != null)
				listener.onLoadFinished(placeWithImage);
		}
		else 
			isDataRequested = true;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		//Prevents the start of another AsyncTask after configuration changes
		setRetainInstance(true);
		
		if(!isOnline()) {
			Toast.makeText(getActivity(), getString(R.string.no_connection), 
					Toast.LENGTH_LONG).show();
		}
		else {
			JsonLoader jsonLoader = new JsonLoader();
			jsonLoader.addListener(jsonCallbacks);
			jsonLoader.execute("https://dl.dropboxusercontent.com/u/6556265/test.json");
		}
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		listener = null;
	}
	
	private boolean isOnline() {
		ConnectivityManager manager = (ConnectivityManager) 
				getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		return (info != null && info.isConnected());
	}
}
