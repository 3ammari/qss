package customer.quick.source.qss.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmReceiver;

import java.util.Calendar;
import java.util.Random;

import customer.quick.source.qss.MyService;
import customer.quick.source.qss.NotificationsQSS;
import customer.quick.source.qss.ObjectsORM.NotificationORM;

public class MyGcmReceiver extends GcmReceiver{
    private static final String TAG ="MY_GCM_RECEIVER" ;

    public MyGcmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        Log.d(TAG,action);
        if (action.equals("com.google.android.c2dm.intent.RECEIVE")){
            Log.d(TAG,"receive action");
            String message= intent.getExtras().getString("message");
            String title=intent.getExtras().getString("title");
            String type=intent.getExtras().getString("type");
            if (type != null) {
                if (type.equals("service")||type.equals("broadcast")||type.equals("newVehicle")||type.equals("reminder")){
                    NotificationsQSS notificationsQSS= new NotificationsQSS(context);
                    Log.d(TAG, "nqss created");
                    notificationsQSS.gcmNotification(title, message);
                    NotificationORM notificationObject=new NotificationORM();
                    notificationObject.setMsg(message);
                    notificationObject.setTitle(title);
                    notificationObject.setType(type);
                    notificationObject.save();
                }
                    context.startService(new Intent(context, MyService.class));

            }


            try {
                Log.d(TAG,message);
                Log.d(TAG,title);
                Log.d(TAG,type);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        if (action.equals("com.google.android.c2dm.intent.REGISTRATION")){
            Log.d(TAG,"registration action");
            String gcm_id=intent.getStringExtra("registration_id");


            try {
                Log.d(TAG, " caught gcm_id : " + gcm_id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }





    }
}
