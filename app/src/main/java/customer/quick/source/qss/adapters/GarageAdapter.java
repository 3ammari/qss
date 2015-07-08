package customer.quick.source.qss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import customer.quick.source.qss.R;
import customer.quick.source.qss.Vehicles;

/**
 * Created by abdul-rahman on 04/07/15.
 */
public class GarageAdapter extends BaseAdapter {
    Context context;
    List<Vehicles> vehiclesList;

    public GarageAdapter(Context context, List<Vehicles> vehiclesList) {
        this.context = context;
        this.vehiclesList = vehiclesList;
    }

    @Override
    public int getCount() {
        return vehiclesList.size();
    }

    @Override
    public Object getItem(int position) {
        return vehiclesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vehicleItem;
        if (position%2==0){
             vehicleItem=inflater.inflate(R.layout.garage_single_item,parent,false);
        }else {
            vehicleItem=inflater.inflate(R.layout.garage_single_item2,parent,false);
        }

        TextView vehicleNameTV = (TextView) vehicleItem.findViewById(R.id.vehicleNameTV);
        TextView modelTV = (TextView) vehicleItem.findViewById(R.id.modelTV);
        vehicleNameTV.setText(vehiclesList.get(position).getMake());
        modelTV.setText(vehiclesList.get(position).getModel());
        return vehicleItem;
    }
}
