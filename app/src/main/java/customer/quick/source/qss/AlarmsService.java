package customer.quick.source.qss;
// the service responsible of fetching the notifications and initiating the data updating

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import customer.quick.source.qss.ObjectsORM.NotificationORM;

public class AlarmsService extends Service {
    AsyncHttpClient client = new AsyncHttpClient();
    String baseUrl;
    String userID;
    NotificationsQSS notificationsQSS;
    Handler handler;
    public static String TAG = "ALARM_SERVICE_TAG";
    Context context;
    String responseStringEx=null;
    int numberOfNotifications;


    ArrayList<NotificationORM> notifcationObjects;


    public AlarmsService() {

    }



    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,intent.getComponent().getClassName());

        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;

        notifcationObjects=new ArrayList<>();
        Log.d(TAG,"ALARM SERVICE STARTED");
//        Log.d(TAG,intent.getComponent().getClassName());
        //Toast.makeText(AlarmsService.this,"alarm service started",Toast.LENGTH_LONG).show();
        Log.d("[alarmservice]", "isRunning");
         baseUrl = GeneralUtilities.getFromPrefs(this,GeneralUtilities.BASE_URL_KEY,"http://192.168.1.131/api/v1/client/");
         userID= GeneralUtilities.getFromPrefs(this,GeneralUtilities.USERID_KEY,"");
        notificationsQSS = new NotificationsQSS(AlarmsService.this);







         handler = new Handler();
        Timer timer = new Timer();
        /*TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                       client.get(AlarmsService.this,baseUrl+userID+"/notifications",null,new TextHttpResponseHandler() {
                          // client.get(AlarmsService.this,"http://www.mocky.io/v2/55b41ebf1c5bf1f70dc9008f",null,new TextHttpResponseHandler() {
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
                                    responseStringEx=responseString;
                                    numberOfNotifications=jsonArray.length();
                                    for (int i = 0; i <jsonArray.length() ; i++) {
                                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                                        String title = jsonObject.getString("title");
                                        String msg = jsonObject.getString("message");
                                        String type = jsonObject.getString("type");
                                        int id  = jsonObject.getInt("id");
                                        NotificationORM object= new NotificationORM();
                                        object.setTitle(title);
                                        object.setType(type);
                                        object.setMsg(msg);

                                        notifcationObjects.add(object);

                                        //notificationsQSS.spawnBroadcastNotifier("You have messages","check",id);
                                        //notifcationObjects.add(new NotificationObject(title,msg,type));
                                       *//* if (type.equals("service")){
                                            serviceList.add(new NotifcationObject(title,msg));
                                            notificationsQSS.spawnServiceNotifier(title,msg,id);
                                            startService(new Intent(AlarmsService.this, MyService.class));
                                        }
                                        if (type.equals("reminder")){
                                            reminderList.add(new NotifcationObject(title,msg));
                                            notificationsQSS.spawnNotification(title, msg, id);
                                            startService(new Intent(AlarmsService.this, MyService.class));
                                        }
                                        if (type.equals("broadcast")){
                                            broadcastList.add(new NotifcationObject(title,msg));
                                            notificationsQSS.spawnBroadcastNotifier(title, msg, id);
                                            startService(new Intent(AlarmsService.this, MyService.class));
                                        }
                                        if (type.equals("newVehicle")){
                                            vehicleList.add(new NotifcationObject(title,msg));
                                            notificationsQSS.spawnBroadcastNotifier(title,msg,id);
                                            startService(new Intent(AlarmsService.this,MyService.class));
                                        }*//*





                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                NotificationORM.saveInTx(notifcationObjects);

                            }

                            @Override
                            public void onFinish() {
                            if (!(responseStringEx==null) && (numberOfNotifications!=0)){
                                numberOfNotifications = NotificationORM.find(NotificationORM.class,"seen = ?","0").size();
                                Log.d(TAG, String.valueOf(numberOfNotifications));
                                String msg= String.format(getString(R.string.notificationTitle),String.valueOf(numberOfNotifications));
                                notificationsQSS.spawnGeneralNotification(msg,getString(R.string.notificationContent),0);
                                responseStringEx=null;
                                numberOfNotifications=0;
                            }
                            }
                        });

                    }
                });
            }
        };
        timer.schedule(task, 0, 30000);*/


        //add all the updating requests




        return START_STICKY;
    }



    }

