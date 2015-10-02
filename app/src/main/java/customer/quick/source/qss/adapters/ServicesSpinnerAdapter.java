package customer.quick.source.qss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import customer.quick.source.qss.ObjectsORM.ServicesTable;
import customer.quick.source.qss.R;

/**
 * Created by abdul-rahman on 21/09/15.
 */
public class ServicesSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    List<ServicesTable> services;
    Context context;
    TextView serviceName;
    ViewHolder holder;
    public ServicesSpinnerAdapter(List<ServicesTable> services, Context context) {
        this.services = services;
        this.context = context;
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.spinner_layout,parent,false);
            TextView name= (TextView) convertView.findViewById(R.id.spinnerTextView);
            name.setText("asdasd");
//        serviceName.setText(services.get(position).getServiceType());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.spinner_item_customed,parent,false);
        holder= new ViewHolder();
        holder.serviceName= (TextView) convertView.findViewById(R.id.spinnerTextView);
        return convertView;
    }

    static class ViewHolder{
        TextView serviceName;
    }
}
