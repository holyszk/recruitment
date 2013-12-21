package net.redexperts.recruitment;

import java.util.HashMap;
import java.util.Map;

import net.redexperts.recruitment.data.PlaceWithImage;
import net.redexperts.recruitment.fragment.DataFragment;
import net.redexperts.recruitment.fragment.DataFragment.OnLoadFinished;
import net.redexperts.recruitment.fragment.ErrorDialogFragment;
import android.app.Dialog;
import android.content.IntentSender.SendIntentException;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnLoadFinished,
		ConnectionCallbacks, OnConnectionFailedListener {

	private final String DATA_FRAGMENT_TAG = "data";
	private final String DIALOG_FRAGMENT_TAG = "dialog";
	private final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	private DataFragment dataFragment;
	private GoogleMap map;
	
	private LocationClient locationClient;
	
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
        
        locationClient = new LocationClient(this, this, this);
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	
    	locationClient.connect();
    }
    
    @Override
    protected void onStop() {
    	locationClient.disconnect();
    	
    	super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onLoadFinished(PlaceWithImage placeWithImage) {
		showMarker(placeWithImage);
		showLocationInfo(placeWithImage);
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if(connectionResult.hasResolution())
		{
			try {
				connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
			} catch (SendIntentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			showErrorDialog(connectionResult.getErrorCode());
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	public Location getLocation() {
		Location location = null;
		if(isGooglePlayServicesAvailable())
			location = locationClient.getLastLocation();
		
		return location;
	}
	
	private boolean isGooglePlayServicesAvailable() {
		int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		if(ConnectionResult.SUCCESS == result)
			return true;
		else {
			showErrorDialog(result);
			return false;
		}
	}

	private void showErrorDialog(int errorCode) {
		Dialog dialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this, 0);
		if(dialog != null)
		{
			ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
			dialogFragment.setDialog(dialog);
			dialogFragment.show(getSupportFragmentManager(), DIALOG_FRAGMENT_TAG);
		}
	}
	
	private void showLocationInfo(PlaceWithImage placeWithImage) {
		Location location = getLocation();
		TextView textView = (TextView) findViewById(R.id.location);
		
		Location markerLocation = new Location("json");
		markerLocation.setLatitude(placeWithImage.getPlace().getLocation().getLatitude());
		markerLocation.setLongitude(placeWithImage.getPlace().getLocation().getLongitude());
		
		if(location != null)
		{
			textView.setText(getString(R.string.location) + "\n" + 
			getString(R.string.latitude) + " " + location.getLatitude() + "\n" + 
			getString(R.string.longitude) + " " + location.getLongitude() + "\n" +
			getString(R.string.distance) + " " + location.distanceTo(markerLocation) + " " +
			getString(R.string.meter));
		}
		else
			textView.setText(getString(R.string.no_location));
	}
	
	private void showMarker(PlaceWithImage placeWithImage) {
		LatLng latLng = new LatLng(
				placeWithImage.getPlace().getLocation().getLatitude(),
				placeWithImage.getPlace().getLocation().getLongitude());
		Marker marker = map.addMarker(new MarkerOptions()
				.position(latLng));
		Map<Marker, PlaceWithImage> data = new HashMap<Marker, PlaceWithImage>();
		data.put(marker, placeWithImage);
		map.setInfoWindowAdapter(new InfoWindow(this, data));
	}
    
}
