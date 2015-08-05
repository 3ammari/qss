package customer.quick.source.qss;

import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import customer.quick.source.qss.ObjectsORM.Stations;

//15.608177, 32.496330
public class FindStation extends Fragment {
    public static FragmentManager fragmentManager;
    Button goButton;
    Spinner spinner;
    SupportMapFragment mapFragment;
    GoogleMap map;
    List<Stations> stationsList;
    ArrayList<String> stationsAddresses;
    int selectedStationIndex;
    private static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


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
        spinner= (Spinner) view.findViewById(R.id.spinner);
        stationsList = Stations.listAll(Stations.class);
        for (int i = 0; i <stationsList.size() ; i++) {
            stationsAddresses.add(stationsList.get(i).getStationLocation());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(super.getActivity(),android.R.layout.simple_spinner_item,stationsAddresses);
        adapter.setDropDownViewResource(R.layout.spinner_item_customed);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStationIndex =position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MarkerOptions marker = new MarkerOptions().position(new LatLng(stationsList.get(selectedStationIndex).getStationLat(), stationsList.get(selectedStationIndex).getStationLong())).title(stationsList.get(selectedStationIndex).getStationName());
                MarkerOptions marker = new MarkerOptions().position(new LatLng((double)stationsList.get(selectedStationIndex).getStationLat(),(double)stationsList.get(selectedStationIndex).getStationLong()));
                try {
                    map.addMarker(marker);
                } catch (Exception e) {
                    e.printStackTrace();
                }
               /* CameraUpdate center=
                        CameraUpdateFactory.newLatLng(new LatLng(latCurrent,
                                lontCurrent));
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

                map.moveCamera(center);*/
                //  map.animateCamera(zoom);
                // adding marker
                // map.addMarker(marker);
                //CameraUpdate center = CameraUpdateFactory.newLatLng()
            }
        });


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


        return view;
    }

    private void initialize() {
        if (map==null) {
            Fragment fragment= getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment= (SupportMapFragment) fragment;
            map=mapFragment.getMap();

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



    /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_station);

        stationsAddresses = new ArrayList<>();
        goButton= (Button) findViewById(R.id.button);
        spinner= (Spinner) findViewById(R.id.spinner);
        stationsList = Stations.listAll(Stations.class);
        for (int i = 0; i <stationsList.size() ; i++) {
            stationsAddresses.add(stationsList.get(i).getStationLocation());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FindStation.this,android.R.layout.simple_spinner_item,stationsAddresses);
        adapter.setDropDownViewResource(R.layout.spinner_item_customed);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStationIndex =position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // MarkerOptions marker = new MarkerOptions().position(new LatLng(stationsList.get(selectedStationIndex).getStationLat(), stationsList.get(selectedStationIndex).getStationLong())).title(stationsList.get(selectedStationIndex).getStationName());
                MarkerOptions marker = new MarkerOptions().position(new LatLng((double)stationsList.get(selectedStationIndex).getStationLat(),(double)stationsList.get(selectedStationIndex).getStationLong()));
                map.addMarker(marker);
               *//* CameraUpdate center=
                        CameraUpdateFactory.newLatLng(new LatLng(latCurrent,
                                lontCurrent));
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

                map.moveCamera(center);*//*
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

        map.setMyLocationEnabled(true);




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

*/
}
