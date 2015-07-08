package customer.quick.source.qss;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.logging.LogRecord;

public class AlarmsService extends Service {
    int filterNotificationID;
    AsyncHttpClient client = new AsyncHttpClient();
    String baseUrl;
    String userID;
    NotificationsQSS notificationsQSS;
    Handler handler;

    public AlarmsService() {
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(AlarmsService.this,"alarm service started",Toast.LENGTH_LONG).show();
        Log.d("[alarmservice]","isRunning");
         baseUrl = GeneralUtilities.getFromPrefs(this,GeneralUtilities.BASE_URL_KEY,"http://192.168.1.131/api/v1/client/");
         userID= GeneralUtilities.getFromPrefs(this,GeneralUtilities.USERID_KEY,"");
        notificationsQSS = new NotificationsQSS(AlarmsService.this);






         handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        client.get(AlarmsService.this,baseUrl+userID+"/notifications",null,new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Log.d("[notifications]",Integer.toString(statusCode));
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                Log.d("[notifications]",responseString);
                                JSONArray jsonArray= null;
                                try {
                                    jsonArray = new JSONArray(responseString);
                                    for (int i = 0; i <jsonArray.length() ; i++) {
                                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                                        String title = jsonObject.getString("title");
                                        String msg = jsonObject.getString("message");
                                        String type = jsonObject.getString("type");

                                        int id  = jsonObject.getInt("id");
                                        if (type.equals("service")){notificationsQSS.spawnServiceNotifier(title,msg,id);}
                                        if (type.equals("reminder")){notificationsQSS.spawnNotification(title,msg,id);}
                                        if (type.equals("broadcast")){notificationsQSS.spawnBroadcastNotifier(title,msg,id);}



                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                    }
                });
            }
        };
        timer.schedule(task, 0, 30000);




        return START_STICKY;
    }
}
