package customer.quick.source.qss;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import customer.quick.source.qss.ObjectsORM.ServicesTable;
import customer.quick.source.qss.ObjectsORM.Stations;



public class FindStation extends Fragment implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,com.google.android.gms.location.LocationListener
{
    public static FragmentManager fragmentManager;
    Dialog productsDialog;
    Button goButton;
    Spinner servicesSpinner;
    QssProduct selectedProduct;
    SupportMapFragment mapFragment;
    GoogleMap map;
    List<Stations> stationsList;
    ArrayList<String> stationsAddresses;
    List<QssProduct> productsList;
    List<ServicesTable> services;
    private static View view;
    ArrayList<MarkerOptions> markers;
    GoogleApiClient mGoogleApiClient;
    LocationServices locationServices;
    Location location;
    private static String TAG="FIND_STATION";
    String baseUrl;
    Context context;
    LocationRequest mLocationRequest;
    LocationListener locationListener;
    ProgressDialog dialog;
    String response;
    ArrayList<TempStation> selectedServiceStationsList;
    ArrayList<TempStation> selectedProductStationsList;
    ServicesTable serviceSelected;
    AsyncHttpClient client = new AsyncHttpClient();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context= getActivity();

        baseUrl = GeneralUtilities.getFromPrefs(context, GeneralUtilities.BASE_URL_KEY, "http://192.168.1.131/api/v1/client/");

        locationListener=this;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(10);


        buildGoogleApiClient();

        selectedServiceStationsList = new ArrayList<>();
        selectedProductStationsList = new ArrayList<>();
        serviceSelected=null;
        selectedProduct=null;
        dialog= new ProgressDialog(context);

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

        try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            map.setMyLocationEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*View */
       // view = inflater.inflate(R.layout.activity_find_station,container,false);

        stationsAddresses = new ArrayList<>();
        productsList= new ArrayList<>();
        services= new ArrayList<>();
        services=ServicesTable.listAll(ServicesTable.class);
        goButton= (Button) view.findViewById(R.id.button);
        //goButton.setVisibility(View.INVISIBLE);
        servicesSpinner = (Spinner) view.findViewById(R.id.spinner);
//      productsList = ProductORM.listAll(ProductORM.class);
        stationsList = Stations.listAll(Stations.class);
        markers = new ArrayList<>();

        //map is ready and default markers will be displayed

        if (!(map==null)){
            defaultMarkers();
        }


        ArrayAdapter adapter=new ArrayAdapter(context, android.R.layout.simple_spinner_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicesSpinner.setAdapter(adapter);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceSelected!=null){
                    getSelectedServiceMarkers2(serviceSelected);

                }else
                {
                    Toast.makeText(context,"Please select a service",Toast.LENGTH_LONG).show();
                }



            }
        });



        servicesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, parent.getItemAtPosition(position).toString());
                serviceSelected= (ServicesTable) parent.getItemAtPosition(position);



            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            map.setMyLocationEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        /*thread = new Thread(new MyThread());
        thread.start();*/
        return view;
    }

    private void defaultMarkers() {
            map.clear();
        for (int i = 0; i <stationsList.size() ; i++) {
            Log.d(TAG,stationsList.get(i).getStationName());
            stationsAddresses.add(stationsList.get(i).getStationLocation());
            markers.add(new MarkerOptions().position(new LatLng(stationsList.get(i).getStationLat(), stationsList.get(i).getStationLong())).title(stationsList.get(i).getStationName()));
        }
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
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),10f);
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
        Log.d(TAG, location.toString());
        this.location=location;
        if (location!=null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10f);
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
        if (!(dialog ==null)){
            dialog.dismiss();
        }
        mGoogleApiClient.disconnect();
    }

    private class TempStation{
        String name;
        double longitude;
        double latitude;

        public TempStation(String name, double longitude, double latitude) {
            this.name = name;
            this.longitude = longitude;
            this.latitude = latitude;
        }
    }

    private void drawMarkers(GoogleMap map,ArrayList<TempStation> tempStations){
        map.clear();
        for (int i = 0; i <tempStations.size() ; i++) {
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(tempStations.get(i).latitude,tempStations.get(i).longitude)).title(tempStations.get(i).name);
            map.addMarker(markerOptions);
        }
    }


    private void productsDialog (final ArrayAdapter<QssProduct> productsAdapter){
        //reset the selected product
        Log.d(TAG,"products Dialog was called");
        selectedProduct=null;
        AlertDialog.Builder mDialog= new AlertDialog.Builder(getActivity());
        mDialog.setTitle("Select a product");
        mDialog.setCancelable(true);
        mDialog.setAdapter(productsAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedProduct = productsAdapter.getItem(which);


            }
        });
        mDialog.show();
    }

    private ArrayList<TempStation> getMarkersForProduct(QssProduct qssProduct){
        RequestParams params= new RequestParams();
        params.put("product_id", qssProduct.id);
        final ArrayList<TempStation> productsPositions=new ArrayList<>();

        client.post(context, baseUrl + "stations/positions", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                dialog.dismiss();
                if (!productsPositions.isEmpty()) {
                    productsPositions.clear();
                }
                try {
                    JSONArray arrayOfProductPositions = new JSONArray(responseString);
                    for (int i = 0; i < arrayOfProductPositions.length(); i++) {
                        JSONObject tempStationJsonObject = new JSONObject(arrayOfProductPositions.getString(i));
                        productsPositions.add(new TempStation(tempStationJsonObject.getString("name"), tempStationJsonObject.getDouble("longitude"), tempStationJsonObject.getDouble("latitude")));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStart() {
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Please wait ... Loading");
            }

            @Override
            public void onProgress(int bytesWritten, int totalSize) {
                dialog.show();
            }

            @Override
            public void onFinish() {
                drawMarkers(map,productsPositions);
            }
        });
   return productsPositions;
    }

    private void getSelectedServiceMarkers2 (ServicesTable serviceSelected){
        RequestParams params = new RequestParams();
        params.put("service_type_id", serviceSelected.getServiceTypeID());
        client.setMaxRetriesAndTimeout(5, AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT);
        // baseUrl + "stations/positions""http://www.mocky.io/v2/560b04d6b78b0f1002b22e50"
        client.post(context,baseUrl + "stations/positions" , params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.dismiss();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                dialog.dismiss();
                Log.d(TAG,responseString);
                try {
                    JSONObject responseObject= new JSONObject(responseString);
                    JSONArray positions= new JSONArray(responseObject.getString("positions"));
                    JSONArray products= new JSONArray(responseObject.getString("products"));
                    Log.d(TAG,positions.toString());
                    Log.d(TAG,products.toString());
                    if (positions.length()!=0){
                        selectedServiceStationsList.clear();
                        for (int i = 0; i <positions.length() ; i++) {
                            JSONObject tempStationObject= new JSONObject(positions.getString(i));
                            selectedServiceStationsList.add(new TempStation(tempStationObject.getString("name"), tempStationObject.getDouble("longitude"), tempStationObject.getDouble("latitude")));
                        }
                    }else{
                        selectedServiceStationsList.clear();}

                    if (products.length()!=0){
                            if (!productsList.isEmpty()){
                                productsList.clear();
                            }
                        for (int i = 0; i <products.length() ; i++) {
                           JSONObject productObject=products.getJSONObject(i);
                           productsList.add(new QssProduct(productObject.getString("name"),productObject.getInt("id")));
                        }
                    }
                    else{
                        productsList.clear();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onProgress(int bytesWritten, int totalSize) {
                dialog.show();

            }

            @Override
            public void onStart() {
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Please wait ... Loading");
                dialog.setIndeterminate(true);
            }

            @Override
            public void onFinish() {
                drawMarkers(map,selectedServiceStationsList);
                if (!productsList.isEmpty()){
                    Log.d(TAG,"products list is not empty");
                    final ArrayAdapter dialogAdapter=new ArrayAdapter<QssProduct>(context, android.R.layout.select_dialog_singlechoice, productsList);

                    AlertDialog.Builder productsDialog=new AlertDialog.Builder(context).setTitle("Want a specific product?").setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedProductStationsList.clear();
                            drawMarkers(map,selectedServiceStationsList);
                        }
                    }).setAdapter(dialogAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedProduct= (QssProduct) dialogAdapter.getItem(which);
                            getMarkersForProduct(selectedProduct);

                        }
                    });
                    productsDialog.create();
                productsDialog.show();
                }
            }
        });

    }


}
