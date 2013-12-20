package net.redexperts.recruitment;

import java.util.Map;

import net.redexperts.recruitment.data.PlaceWithImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class InfoWindow implements InfoWindowAdapter {
	
	private Map<Marker, PlaceWithImage> data;
	private View content;
	
	public InfoWindow(Context context, Map<Marker, PlaceWithImage> data) {
		this.data = data;
		LayoutInflater inflater = (LayoutInflater) 
				context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		content = inflater.inflate(R.layout.info_window, null);
	}

	@Override
	public View getInfoContents(Marker marker) {
		TextView textView = (TextView) content.findViewById(R.id.title);
		PlaceWithImage placeWithImage = data.get(marker);
		textView.setText(placeWithImage.getPlace().getText());
		
		ImageView imageView = (ImageView) content.findViewById(R.id.image);
		imageView.setImageBitmap(placeWithImage.getImage());
		
		return content;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}

}
