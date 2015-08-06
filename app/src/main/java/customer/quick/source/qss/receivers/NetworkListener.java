package customer.quick.source.qss.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import customer.quick.source.qss.AlarmsService;
import customer.quick.source.qss.MyService;
// this receiver will fire both of the Services to update the data whenever the network status is changed
public class NetworkListener extends BroadcastReceiver {
    public NetworkListener() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(     ConnectivityManager.TYPE_MOBILE );
        if ( activeNetInfo != null )
        {
            Toast.makeText( context, "Active Network Type : " + activeNetInfo.getTypeName(), Toast.LENGTH_SHORT ).show();
            Intent intent1 = new Intent(context, AlarmsService.class);
            context.startService(intent1);
            Intent intent2 = new Intent(context, MyService.class);
            context.startService(intent2);
        }
        if( mobNetInfo != null )
        {
            Toast.makeText( context, "Mobile Network Type : " + mobNetInfo.getTypeName(), Toast.LENGTH_SHORT ).show();
            Intent intent1 = new Intent(context, AlarmsService.class);
            context.startService(intent1);
            Intent intent2 = new Intent(context, MyService.class);
            context.startService(intent2);
        }
    }
}
