package customer.quick.source.qss;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import customer.quick.source.qss.adapters.GarageAdapter;


public class Garage extends Fragment {
    ListView listView;
    List<Vehicles> vehiclesList;
    ArrayList<String> vehiclesNames;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.designed_garage,container,false);
        context=super.getActivity();
        vehiclesNames = new ArrayList<>();
        listView= (ListView) view.findViewById(R.id.listView2);
        vehiclesList= Vehicles.listAll(Vehicles.class);

        /*for (int i = 0; i <vehiclesList.size() ; i++) {
            vehiclesNames.add(vehiclesList.get(i).getMake()+" "+vehiclesList.get(i).getModel()+" "+vehiclesList.get(i).getYear());
        }*/
       // ArrayAdapter arrayAdapter = new ArrayAdapter(Garage.this,android.R.layout.simple_list_item_1,android.R.id.text1,vehiclesNames);
        listView.setAdapter(new GarageAdapter(context,vehiclesList));
/*        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String vehicleID= vehiclesList.get(position).getVehicleID();
                Intent intent = new Intent(context,VehicleStatus.class);
                intent.putExtra("vehicleID",vehicleID);
                startActivity(intent);
            }
        });*/

        return view;
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);
        vehiclesNames = new ArrayList<>();
        listView= (ListView) findViewById(R.id.list);
        vehiclesList= Vehicles.listAll(Vehicles.class);

        *//*for (int i = 0; i <vehiclesList.size() ; i++) {
            vehiclesNames.add(vehiclesList.get(i).getMake()+" "+vehiclesList.get(i).getModel()+" "+vehiclesList.get(i).getYear());
        }*//*
        ArrayAdapter arrayAdapter = new ArrayAdapter(Garage.this,android.R.layout.simple_list_item_1,android.R.id.text1,vehiclesNames);
        listView.setAdapter(new GarageAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String vehicleID= vehiclesList.get(position).getVehicleID();
                Intent intent = new Intent(Garage.this,VehicleStatus.class);
                intent.putExtra("vehicleID",vehicleID);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_garage, menu);
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
    }*/
}
