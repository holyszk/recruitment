package net.redexperts.recruitment.fragment;

import net.redexperts.recruitment.R;
import net.redexperts.recruitment.data.Place;
import net.redexperts.recruitment.data.PlaceWithImage;
import net.redexperts.recruitment.loader.BasicLoader.Callbacks;
import net.redexperts.recruitment.loader.ImageLoader;
import net.redexperts.recruitment.loader.JsonLoader;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

//Store data during configuration changes
public class DataFragment extends Fragment {
	
	private PlaceWithImage placeWithImage;
	private OnLoadFinished listener;
	
	private Callbacks<Place> jsonCallbacks = new Callbacks<Place>() {

		@Override
		public void onPostExecute(Place data) {
			if(data != null) {
				placeWithImage = new PlaceWithImage();
				placeWithImage.setPlace(data);
				ImageLoader loader = new ImageLoader();
				loader.addListener(imageCallbacks);
				loader.execute(data.getImage());
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
				if(listener != null)
					listener.onLoadFinished(placeWithImage);
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
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if(placeWithImage != null && placeWithImage.getImage() != null)
			if(listener != null)
				listener.onLoadFinished(placeWithImage);
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
