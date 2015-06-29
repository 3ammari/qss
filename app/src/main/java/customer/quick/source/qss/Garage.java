package customer.quick.source.qss;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Garage extends ActionBarActivity {
    ListView listView;
    List<Vehicles> vehiclesList;
    ArrayList<String> vehiclesNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);
        vehiclesNames = new ArrayList<>();
        listView= (ListView) findViewById(R.id.list);
        vehiclesList= Vehicles.listAll(Vehicles.class);
        for (int i = 0; i <vehiclesList.size() ; i++) {
            vehiclesNames.add(vehiclesList.get(i).getMake()+" "+vehiclesList.get(i).getModel()+" "+vehiclesList.get(i).getYear());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(Garage.this,android.R.layout.simple_list_item_1,android.R.id.text1,vehiclesNames);
        listView.setAdapter(arrayAdapter);
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
    }
}
