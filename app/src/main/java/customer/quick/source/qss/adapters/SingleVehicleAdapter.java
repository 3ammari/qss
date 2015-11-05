package customer.quick.source.qss.adapters;

import android.content.Context;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import customer.quick.source.qss.ObjectsORM.RemindersPreferencesORM;
import customer.quick.source.qss.R;
import customer.quick.source.qss.ObjectsORM.RecentServices;
import customer.quick.source.qss.ObjectsORM.ServicesTable;

/**
 * Created by abdul-rahman on 08/07/15.
 */
public class SingleVehicleAdapter extends BaseAdapter {
    private Context context;
    private List<RecentServices> recentServices;
    private int vehicleID;
    private static final String TAG="Single_VEHICLE_ADAPTER";

    public SingleVehicleAdapter(Context context, List<RecentServices> recentServices) {
        this.context = context;
        this.vehicleID = vehicleID;
        this.recentServices = recentServices;


    }

    @Override
    public int getCount() {
        return recentServices.size();
    }

    @Override
    public Object getItem(int position) {
        return recentServices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // if (convertView == null){}else{}
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View singleVehicle= inflater.inflate(R.layout.single_vehicle_status_item, parent, false);
        TextView serviceTypeTV = (TextView) singleVehicle.findViewById(R.id.serviceTypeTV);
        TextView daysAgoTV = (TextView) singleVehicle.findViewById(R.id.daysAgoTV);
        TextView daysRemainingTV = (TextView) singleVehicle.findViewById(R.id.remainingDaysTV);
        String serviceType = ServicesTable.find(ServicesTable.class,"service_type_id = ?", String.valueOf(recentServices.get(position).getServiceTypeID())).get(0).getServiceType();
        String dateOfService = recentServices.get(position).getDate();
        int pref= 0;
        try {
            pref = RemindersPreferencesORM.find(RemindersPreferencesORM.class, "service_type_id = ?", String.valueOf(recentServices.get(position).getServiceTypeID())).get(0).getPeriod();
        } catch (Exception e) {

            e.printStackTrace();
        }
        int daysAgo = periodCalculator(dateOfService);
        int daysRemaining= pref - daysAgo;
        serviceTypeTV.setText(serviceType);
        daysAgoTV.setText(Integer.toString(daysAgo) + context.getString(R.string.days_ago));

        if (daysRemaining<1) {
            daysRemainingTV.setText("0"+context.getString(R.string.days));
        } else {
            daysRemainingTV.setText(Integer.toString(daysRemaining)+context.getString(R.string.days));
        }
        /*daysAgoTV.setTextColor(context.getResources().getColor(coloring(pref, daysRemaining)));
        daysRemainingTV.setHighlightColor(context.getResources().getColor(coloring(pref, daysRemaining)));*/

        return singleVehicle;
    }

    public int periodCalculator(String date){
        JodaTimeAndroid.init(context);
        LocalDateTime current = new LocalDateTime();

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime serviceDate = LocalDateTime.parse(date,dateTimeFormatter);
        Log.d(TAG,date);
        Log.d(TAG,serviceDate.toString());
        Days days=Days.daysBetween(serviceDate,current);


        return days.getDays();
    }

    /*public int coloring(int pref ,int remainingDays){
        double ratio = remainingDays/pref;

        if (ratio>0.65D){
            return R.color.Green;
        }
        if (ratio<.65D&&ratio>.34D){

            return R.color.Yellow;
        }
        else {
            return R.color.Brown;
        }



    }*/
}
