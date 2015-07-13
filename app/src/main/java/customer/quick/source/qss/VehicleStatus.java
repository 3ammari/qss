package customer.quick.source.qss;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import customer.quick.source.qss.ObjectsORM.RecentServices;
import customer.quick.source.qss.adapters.SingleVehilcleAdapter;


public class VehicleStatus extends ActionBarActivity {
    ListView servicesListView;
    String TAG = "lvnadlngvs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_vehicle_status);
        servicesListView = (ListView) findViewById(R.id.servicesListView);
        int vehicleID = getIntent().getExtras().getInt("vehicleID");
        Log.d(TAG, String.valueOf(vehicleID));
        List<RecentServices> recentServicesList= RecentServices.find(RecentServices.class, "vehicle_id = ?", String.valueOf(vehicleID));
        Log.d(TAG, String.valueOf(recentServicesList.size()));
        servicesListView.setAdapter(new SingleVehilcleAdapter(this,recentServicesList));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vehicle_status, menu);
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
