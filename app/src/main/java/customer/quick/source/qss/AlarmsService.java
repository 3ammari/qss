package customer.quick.source.qss;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import net.danlew.android.joda.JodaTimeAndroid;

import org.apache.http.Header;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AlarmsService extends Service {
    int filterNotificationID;
    AsyncHttpClient client = new AsyncHttpClient();
    public AlarmsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       filterNotificationID= Integer.parseInt(GeneralUtilities.getFromPrefs(this, GeneralUtilities.KEY, "86"));
        List<ServicesTable> servicesTable = ServicesTable.listAll(ServicesTable.class);
        JSONArray jsonArray= new JSONArray();

        for (int i = 0; i <servicesTable.size(); i++) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("period",servicesTable.get(i).getCycle());
                jsonObject.put("service_type_id",servicesTable.get(i).getServiceTypeID());
                jsonArray.put(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        Log.d("[8]",jsonArray.toString());
        final RequestParams requestParams = new RequestParams(jsonArray);
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                client.post(AlarmsService.this,"",requestParams,new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("[8]",Integer.toString(statusCode));
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.d("[7]",responseString);
                    }
                });

            }
        },0,30, TimeUnit.SECONDS);

    return START_STICKY;
    }
}
/*NotificationsQSS n= new NotificationsQSS(AlarmsService.this,453);
                JodaTimeAndroid.init(AlarmsService.this);
                List<Vehicles> vehiclesList = Vehicles.listAll(Vehicles.class);

                //check the oil change for all vehicles

                for (int i = 0; i <vehiclesList.size() ; i++) {
                    String oil = RecentServices.find(RecentServices.class,"vehicle_id = ?",vehiclesList.get(i).getVehicleID()).get(0).getOilChange();
                    if (!oil.equals("never")){
                        DateTimeFormatter jodaFormatter = DateTimeFormat.forPattern("yyyy-mm-dd");
                        LocalDate oilServiceDate= jodaFormatter.parseLocalDate(oil);
                        LocalDate currentDate= new LocalDate();
                        Period period=new Period(oilServiceDate,currentDate);

                        if (period.getMinutes()>=3){
                            n.fiveDaysNotification("oil change");
                        }
                        if (period.getMinutes()>=2&& period.getMinutes()<3){
                            n.twoDaysNotification("oil change");
                        }
                        if (period.getMinutes()>=1&&period.getMinutes()<2){
                            n.endOfPeriodNotification("oil change");
                        }

                    }



                }

                //check the filter for all vehicles

                for (int i = 0; i <vehiclesList.size() ; i++) {
                    String filter = RecentServices.find(RecentServices.class,"vehicle_id = ?",vehiclesList.get(i).getVehicleID()).get(0).getFilterChange();
                    DateTimeFormatter jodaFormatter = DateTimeFormat.forPattern("yyyy-mm-dd");
                    LocalDate filterServiceDate= jodaFormatter.parseLocalDate(filter);
                    LocalDate currentDate= new LocalDate();
                    Period period=new Period(filterServiceDate,currentDate);
                    if (period.getDays()>=1){

                    }
                    if (period.getDays()>=2){

                    }
                    if (period.getDays()>=3){

                    }

                }

                    //check checkups for all the vehicles

                for (int i = 0; i <vehiclesList.size() ; i++) {
                    String checkup = RecentServices.find(RecentServices.class,"vehicle_id = ?",vehiclesList.get(i).getVehicleID()).get(0).getCheckUp();
                    DateTimeFormatter jodaFormatter = DateTimeFormat.forPattern("yyyy-mm-dd");
                    LocalDate checkUpServiceDate= jodaFormatter.parseLocalDate(checkup);
                    LocalDate currentDate= new LocalDate();
                    Period period=new Period(checkUpServiceDate,currentDate);
                    if (period.getDays()>=1){

                    }
                    if (period.getDays()>=2){

                    }
                    if (period.getDays()>=3){

                    }

                }

                *//*List<RecentServices> recentServicesList=RecentServices.find(RecentServices.class,"service_type = ?","Filter Change");
                //String recentServiceDate=recentServicesList.get(recentServicesList.size()-1).getDate();
                String date[]=recentServiceDate.split(" ");
                String date1= date[0];
                Log.d("[11]", date1);
                DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-mm-dd");
                LocalDate localDate = dtf.parseLocalDate(date1);
                LocalDate currentDate = new LocalDate();
                Period period1= new Period(localDate,currentDate);
                //Period period = Period.fieldDifference(localDate.dayOfMonth().withMaximumValue(),currentDate.dayOfMonth().withMinimumValue());
                // Period period = Period.fieldDifference(currentDate.dayOfMonth().withMaximumValue(),localDate.dayOfMonth().withMinimumValue());
                Log.d("[1]", currentDate.getDayOfMonth()+"\n"+String.valueOf(period1.getDays()));
                if(period1.getDays()>=1){
                    Intent intent = new Intent(AlarmsService.this,FindStation.class);
                    PendingIntent pendingIntent= PendingIntent.getActivity(AlarmsService.this, 0, intent, 0);
                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(AlarmsService.this);
                    Notification notification=builder.setContentTitle("Y").setContentText("content Text").setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_plusone_small_off_client).setSound(alarmSound).setNumber(++filterNotificationID).build();
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    nm.notify(++filterNotificationID,notification);

                    GeneralUtilities.saveToPrefs(AlarmsService.this,GeneralUtilities.KEY, String.valueOf(filterNotificationID));


                }


*/
