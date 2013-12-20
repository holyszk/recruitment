package net.redexperts.recruitment.fragment;

import net.redexperts.recruitment.data.Place;
import net.redexperts.recruitment.data.PlaceWithImage;
import net.redexperts.recruitment.loader.BasicLoader.Callbacks;
import net.redexperts.recruitment.loader.ImageLoader;
import net.redexperts.recruitment.loader.JsonLoader;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;

//Store data during configuration changes
public class DataFragment extends Fragment {
	
	private PlaceWithImage placeWithImage;
	private OnLoadFinished listener;
	
	private Callbacks<Place> jsonCallbacks = new Callbacks<Place>() {

		@Override
		public void onPostExecute(Place data) {
			placeWithImage = new PlaceWithImage();
			placeWithImage.setPlace(data);
			ImageLoader loader = new ImageLoader();
			loader.addListener(imageCallbacks);
			loader.execute(data.getImage());
		}
	};
	
	private Callbacks<Bitmap> imageCallbacks = new Callbacks<Bitmap>() {

		@Override
		public void onPostExecute(Bitmap data) {
			placeWithImage.setImage(data);
			if(listener != null)
				listener.onLoadFinished(placeWithImage);
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		//Prevents the start of another AsyncTask after configuration changes
		setRetainInstance(true);
		
        JsonLoader jsonLoader = new JsonLoader();
        jsonLoader.addListener(jsonCallbacks);
        jsonLoader.execute("https://dl.dropboxusercontent.com/u/6556265/test.json");
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		listener = null;
	}
}
