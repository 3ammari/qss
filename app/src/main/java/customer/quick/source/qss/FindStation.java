package customer.quick.source.qss;

import android.app.FragmentManager;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import customer.quick.source.qss.ObjectsORM.Stations;


public class FindStation extends Fragment implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,com.google.android.gms.location.LocationListener
{
    public static FragmentManager fragmentManager;
    Button goButton;
    Spinner spinner;
    SupportMapFragment mapFragment;
    GoogleMap map;
    List<Stations> stationsList;
    ArrayList<String> stationsAddresses;

    private static View view;
    ArrayList<MarkerOptions> markers;
    GoogleApiClient mGoogleApiClient;
    LocationServices locationServices;
    Location location;
    private static String TAG="FIND_STATION";

    Context context;
    LocationRequest mLocationRequest;
    LocationListener locationListener;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context= getActivity();
        locationListener=this;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(10);
        /*mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();*/

        buildGoogleApiClient();


        /*handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                location = (Location) msg.obj;
                if (location!=null){
                    goButton.setVisibility(View.VISIBLE);
                }
            }
        };*/

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.activity_find_station, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        /*View */
       // view = inflater.inflate(R.layout.activity_find_station,container,false);
        stationsAddresses = new ArrayList<>();
        goButton= (Button) view.findViewById(R.id.button);
        //goButton.setVisibility(View.INVISIBLE);
        spinner= (Spinner) view.findViewById(R.id.spinner);
        stationsList = Stations.listAll(Stations.class);
        markers = new ArrayList<>();
        for (int i = 0; i <stationsList.size() ; i++) {
            stationsAddresses.add(stationsList.get(i).getStationLocation());
            markers.add(new MarkerOptions().position(new LatLng(stationsList.get(i).getStationLat(), stationsList.get(i).getStationLong())).title(stationsList.get(i).getStationName()));
        }
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                Log.d(TAG,location.toString());
                //location= LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
              /*  Log.d(TAG,"invoked the location request");
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
*/
               /* CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),3f);
                //CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(1.2,3),10f);
                map.animateCamera(update);*/
               // Log.d(TAG,"camera updated to new position");
            }
        });


        try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
           // map.setMyLocationEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*thread = new Thread(new MyThread());
        thread.start();*/
        return view;
    }

    private void initialize() {
        if (map==null) {
            Fragment fragment= getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment= (SupportMapFragment) fragment;
            map=mapFragment.getMap();
            map.setMyLocationEnabled(true);
            for (int i = 0; i <markers.size() ; i++) {
              map.addMarker(markers.get(i));
            }
            // check if map is created successfully or not
            if (map==null) {
                Toast.makeText(super.getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }


    }

    public void onDestroyView() {
        super.onDestroyView();


        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        SupportMapFragment fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        if (fragment!=null) {
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.remove(fragment);
                ft.commit();

        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location!=null){
            Log.d(TAG,location.toString());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),3f);
        map.animateCamera(update);
        }
        Log.d(TAG,"connected");
        /*LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        thread = new Thread(new MyThread());
        thread.start();*/
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG,"connection suspended "+String.valueOf(i));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG,"connection failed");
    }


    @Override
    public void onLocationChanged(Location location) {
        /*LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(10);
        location = (Location) LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) context);*/
        Log.d(TAG,location.toString());
        this.location=location;
        if (location!=null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 3f);
            map.animateCamera(update);
            Log.d(TAG, "camera updated to new position");
            goButton.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.d(TAG,"connect() was called");
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }
}
