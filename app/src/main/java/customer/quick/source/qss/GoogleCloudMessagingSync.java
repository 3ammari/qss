package customer.quick.source.qss;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.loopj.android.http.AsyncHttpClient;

import customer.quick.source.qss.receivers.GCMClientManager;

public class GoogleCloudMessagingSync extends Service {
    AsyncHttpClient client=new AsyncHttpClient();
    private static final String TAG="GOOGLE_CLOUD_MESSAGING_SYNC";
    private String regToken;
    private GCMClientManager gcmClientManager;
    String projectNumber ="413600573845";
    private static final String TAG1 = "GCM_REGISTER";

    public GoogleCloudMessagingSync() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        regToken=GeneralUtilities.getFromPrefs(this,GeneralUtilities.GCM_DEVICE_TOKEN_KEY,null);
        if (regToken==null){

        }
        return START_NOT_STICKY;
    }

    private class GoogleCloudMessagingSyncReciever extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }


}
