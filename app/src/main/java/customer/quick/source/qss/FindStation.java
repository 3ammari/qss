package customer.quick.source.qss;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

//15.608177, 32.496330
public class FindStation extends FragmentActivity  {
    public static FragmentManager fragmentManager;
    Button goButton;
    Spinner spinner;
    SupportMapFragment mapFragment;
    GoogleMap map;
    List<Stations> stationsList;
    ArrayList<String> stationsAddresses;
    int selectedStationIndex;
    LocationManager locManager;
    long latCurrent;
    long lontCurrent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_station);

        stationsAddresses = new ArrayList<>();
        goButton= (Button) findViewById(R.id.button);
        spinner= (Spinner) findViewById(R.id.spinner);
       /* stationsList = Stations.listAll(Stations.class);
        for (int i = 0; i <stationsList.size() ; i++) {
            stationsAddresses.add(stationsList.get(i).getStationLocation());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FindStation.this,android.R.layout.simple_spinner_item,stationsAddresses);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedStationIndex =position;
            }
        });*/

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // MarkerOptions marker = new MarkerOptions().position(new LatLng(stationsList.get(selectedStationIndex).getStationLat(), stationsList.get(selectedStationIndex).getStationLong())).title(stationsList.get(selectedStationIndex).getStationName());
                MarkerOptions marker = new MarkerOptions().position(new LatLng((double)latCurrent,(double)lontCurrent));
                map.addMarker(marker);
                CameraUpdate center=
                        CameraUpdateFactory.newLatLng(new LatLng(latCurrent,
                                lontCurrent));
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

                map.moveCamera(center);
              //  map.animateCamera(zoom);
        // adding marker
       // map.addMarker(marker);
                //CameraUpdate center = CameraUpdateFactory.newLatLng()
            }
        });
        Fragment fragment = getSupportFragmentManager().findFragmentById(
                R.id.map);
            mapFragment= (SupportMapFragment) fragment;
        map=mapFragment.getMap();

        /*try {Thread thread= new Thread(new Runnable(){
            @Override
            public void run() {
                Log.d("Runnable is running","f");
                GPSTracker gpsTracker = new GPSTracker(FindStation.this);
                if (gpsTracker.canGetLocation()){
                    lontCurrent= (long) gpsTracker.getLongitude();
                    latCurrent = (long) gpsTracker.getLatitude();
                }
            }
        });


        } catch (Exception e) {
            e.printStackTrace();
        }*/
        map.setMyLocationEnabled(true);


        GPSTracker gpsTracker = new GPSTracker(FindStation.this);
        if (gpsTracker.canGetLocation()){
            Log.d("Runnable is running","f");
            lontCurrent= (long) gpsTracker.getLongitude();
            latCurrent = (long) gpsTracker.getLatitude();}else {gpsTracker.showSettingsAlert();}

        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(latCurrent,
                        lontCurrent));

        CameraUpdate zoom=CameraUpdateFactory.zoomIn();
      /*  try {Log.d("camera","waiting before moving to center");
            Thread.sleep(20000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        map.animateCamera(center);
        Log.d("camera","moved to center");
       /* try {
            Log.d("camera","waiting before zooming");
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        map.animateCamera(zoom);
        Log.d("camera","zoomed");





    }

    private final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {}

        public void onStatusChanged(String provider,int status,Bundle extras){}
    };

    private void updateWithNewLocation(Location location) {

        String latLongString = "";
        if (location != null) {
            long lat = (long) location.getLatitude();
            long lng = (long) location.getLongitude();
            latCurrent=lat;
            lontCurrent=lng;

            latLongString = "Lat:" + lat + "\nLong:" + lng;
        } else {
            latLongString = "No location found";
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_station, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
/*
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_find_station, container, false);
            return rootView;
        }
    }
*/
}
