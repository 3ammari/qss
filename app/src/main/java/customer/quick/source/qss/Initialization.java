package customer.quick.source.qss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;

import customer.quick.source.qss.receivers.GCMClientManager;


public class Initialization extends ActionBarActivity {


    private static final String TAG ="INITIALIZATION_ACTIVITY" ;
    Receiver receiver;
    AsyncHttpClient client=new AsyncHttpClient();
    String projectNumber ="413600573845";
    private static final String TAG1 = "GCM_REGISTER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialization);
        String action =getIntent().getAction();
        ImageView logoIV= new ImageView(this);
        logoIV.setImageResource(R.drawable.app_logo);
        //if (action.equals(Login.ACTION_INITIALIZE)){
            Intent intent= new Intent(this,MyService.class);
            intent.setAction(Login.ACTION_INITIALIZE);
            startService(intent);
            GCMClientManager gcmClientManager= new GCMClientManager(this,projectNumber);
            gcmClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
                @Override
                public void onSuccess(String registrationId, boolean isNewRegistration) {

                }
            });

       // }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (receiver ==null){
            receiver = new Receiver();
            IntentFilter intentFilter = new IntentFilter(MyService.DONE);
            registerReceiver(receiver,intentFilter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver !=null){
            unregisterReceiver(receiver);
        }
    }

    private class Receiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action= intent.getAction();
            if (action!=null){
                if (action.equals(MyService.DONE)){
                    startActivity(new Intent(context,Home.class));
                    finish();
                }
            }
        }
    }
}
