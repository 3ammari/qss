package customer.quick.source.qss.receivers;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by abdul-rahman on 26/10/15.
 */
public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG= "GCM_SERVICE_LISTENER";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message= data.getString("message");
        Log.d(TAG,from);
        Log.d(TAG,message);
        if (from.startsWith("")){

        }
    }
}
