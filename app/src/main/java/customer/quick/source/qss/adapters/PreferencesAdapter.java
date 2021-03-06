package customer.quick.source.qss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import customer.quick.source.qss.ObjectsORM.RemindersPreferencesORM;
import customer.quick.source.qss.ObjectsORM.ServicesTable;
import customer.quick.source.qss.R;

/**
 * Created by abdul-rahman on 08/07/15.
 */
public class PreferencesAdapter extends BaseAdapter {
    Context context;
  //  List<RemindersPreferencesORM> elements;
    List<ServicesTable> elements;
    public PreferencesAdapter(Context context,List<ServicesTable> list) {
        this.context= context;
        this.elements =list;
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Object getItem(int position) {
        return elements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View singlePref = inflater.inflate(R.layout.preferences_element,parent,false);
        TextView serviceTypeTV = (TextView) singlePref.findViewById(R.id.elementServiceTypeTV);
        TextView periodTV = (TextView) singlePref.findViewById(R.id.elementPeriodTV);

        //String serviceType = ServicesTable.find(ServicesTable.class,"service_type_id = ?",Integer.toString(elements.get(position).getServiceTypeID())).get(0).getServiceType();
        String period = null;
        try {
            period = String.valueOf(RemindersPreferencesORM.find(RemindersPreferencesORM.class, "service_type_id = ?", String.valueOf(elements.get(position).getServiceTypeID())).get(0).getPeriod());
        } catch (Exception e) {
            if(period==null){period="not set";}

            e.printStackTrace();
        }
        periodTV.setText(period);
        serviceTypeTV.setText(elements.get(position).toString());

        return singlePref;
    }
}
