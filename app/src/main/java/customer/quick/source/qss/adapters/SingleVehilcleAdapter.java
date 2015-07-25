package customer.quick.source.qss.adapters;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import customer.quick.source.qss.GeneralUtilities;
import customer.quick.source.qss.ObjectsORM.RemindersPreferencesORM;
import customer.quick.source.qss.R;
import customer.quick.source.qss.ObjectsORM.RecentServices;
import customer.quick.source.qss.ObjectsORM.ServicesTable;

/**
 * Created by abdul-rahman on 08/07/15.
 */
public class SingleVehilcleAdapter extends BaseAdapter {
    private Context context;
    private List<RecentServices> recentServices;
    private List<ServicesTable> servicesTables;
    private int vehicleID;

    public SingleVehilcleAdapter(Context context, List<RecentServices> recentServices) {
        this.context = context;
        this.vehicleID = vehicleID;
        this.recentServices = recentServices;
        this.servicesTables = ServicesTable.listAll(ServicesTable.class);

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
        int pref=RemindersPreferencesORM.find(RemindersPreferencesORM.class,"service_type_id = ?", String.valueOf(recentServices.get(position).getServiceTypeID())).get(0).getPeriod();
        int daysAgo = Math.abs(periodCalulator(dateOfService));
        int daysRemaining= pref - daysAgo;
        serviceTypeTV.setText(serviceType);
        daysAgoTV.setText(Integer.toString(daysAgo));
        daysRemainingTV.setText(Integer.toString(daysRemaining));
        daysAgoTV.setTextColor(context.getResources().getColor(coloring(pref, daysRemaining)));
        daysRemainingTV.setHighlightColor(context.getResources().getColor(coloring(pref, daysRemaining)));

        return singleVehicle;
    }

    public int periodCalulator(String date){

        LocalDate today = new LocalDate();
        LocalDateTime current = new LocalDateTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss");
        LocalDateTime serviceDate = dateTimeFormatter.parseLocalDateTime(date);
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-mm-dd");
        /*LocalDateTime localDate = dtf.parseLocalDate(date);*/

        Period period = new Period(serviceDate,current);
        Period period1 = Period.fieldDifference(current,serviceDate);
        return period1.getDays();
    }

    public int coloring(int pref ,int remainingDays){
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



    }
}
