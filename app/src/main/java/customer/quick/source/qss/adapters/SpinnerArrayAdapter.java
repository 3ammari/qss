package customer.quick.source.qss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import customer.quick.source.qss.ObjectsORM.ServicesTable;
import customer.quick.source.qss.R;

/**
 * Created by abdul-rahman on 22/09/15.
 */
public class SpinnerArrayAdapter extends ArrayAdapter<ServicesTable> {
    Context context;
    List<ServicesTable> services;
    ViewHolder holder;
    TextView serviceName;


    public SpinnerArrayAdapter(Context context, int resource, List<ServicesTable> objects) {
        super(context, resource, objects);
        this.context=context;
        this.services=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(R.layout.spinner_layout,parent,false);
        holder =new ViewHolder();
        holder.serviceName= (TextView) convertView.findViewById(R.id.spinnerTextView);
        convertView.setTag(holder);


        serviceName.setText(services.get(position).getServiceType());

        return convertView; }
        else{
            convertView.getTag();
            return convertView;
        }

        }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public ServicesTable getItem(int position) {
        return super.getItem(position);
    }

    static class ViewHolder{
        TextView serviceName;
    }
}
