package customer.quick.source.qss.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmReceiver;

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
            try {
                Log.d(TAG,message);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        if (action.equals("com.google.android.c2dm.intent.REGISTRATION")){
            Log.d(TAG,"registration action");
            Bundle bundle= intent.getExtras();
            try {
                Log.d(TAG,bundle.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }





    }
}
