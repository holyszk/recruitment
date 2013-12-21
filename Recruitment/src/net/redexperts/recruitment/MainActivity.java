package net.redexperts.recruitment;

import java.util.HashMap;
import java.util.Map;

import net.redexperts.recruitment.data.Location;
import net.redexperts.recruitment.data.PlaceWithImage;
import net.redexperts.recruitment.fragment.DataFragment;
import net.redexperts.recruitment.fragment.DataFragment.OnLoadFinished;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnLoadFinished {

	private final String DATA_FRAGMENT_TAG = "data";

	private DataFragment dataFragment;
	private GoogleMap map;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        FragmentManager fm = getSupportFragmentManager();
        dataFragment = (DataFragment) fm.findFragmentByTag(DATA_FRAGMENT_TAG);
        
        if(dataFragment == null) {
        	dataFragment = new DataFragment();
        	fm.beginTransaction().add(dataFragment, DATA_FRAGMENT_TAG).commit();
        }
        
        map = ((SupportMapFragment) fm.findFragmentById(R.id.map)).getMap();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onLoadFinished(PlaceWithImage placeWithImage) {
		Location location = placeWithImage.getPlace().getLocation();
		Marker marker = map.addMarker(new MarkerOptions()
				.position(new LatLng(location.getLatitude(), location.getLongitude())));
		Map<Marker, PlaceWithImage> data = new HashMap<Marker, PlaceWithImage>();
		data.put(marker, placeWithImage);
		map.setInfoWindowAdapter(new InfoWindow(this, data));
	}
    
}
