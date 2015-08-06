package customer.quick.source.qss;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import customer.quick.source.qss.ObjectsORM.Vehicles;
import customer.quick.source.qss.adapters.GarageAdapter;


public class Garage extends Fragment {
    ListView listView;
    List<Vehicles> vehiclesList;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.designed_garage,container,false);
        context=super.getActivity();
        listView= (ListView) view.findViewById(R.id.listView2);



        return view;
    }
//made the population of the listview inside the onResume method because it improves if any Update occured you don't need to exit the application if you made
    //inside the onResume unlike in the onCreate method
    @Override
    public void onResume() {
        super.onResume();
        vehiclesList= Vehicles.listAll(Vehicles.class);
        listView.setAdapter(new GarageAdapter(context,vehiclesList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int vehicleID = vehiclesList.get(position).getVehicleID();
                Intent intent = new Intent(context, SingleVehicleStatus.class);
                intent.putExtra("vehicleID", vehicleID);
                startActivity(intent);
            }
        });


    }


}
