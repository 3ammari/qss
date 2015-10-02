package customer.quick.source.qss.receivers;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import customer.quick.source.qss.R;

public class GcmRegister extends ActionBarActivity {

    private GCMClientManager gcmClientManager;
    String projectNumber ="413600573845";
    private static final String TAG = "GCM_REGISTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcm_register);
        gcmClientManager = new GCMClientManager(this, projectNumber);
        gcmClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                Log.d(TAG,registrationId);
            }
        });

    }


}
