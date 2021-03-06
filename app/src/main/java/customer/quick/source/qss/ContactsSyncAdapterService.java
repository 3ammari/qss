package customer.quick.source.qss;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;
import android.os.OperationCanceledException;
import android.util.Log;
//Ignore this for now!!
public class ContactsSyncAdapterService extends Service {
    private static final String TAG = "ContactsSyncAdapterService";
    private static SyncAdapterImpl sSyncAdapter = null;
    private static ContentResolver mContentResolver = null;
    public ContactsSyncAdapterService() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        IBinder ret = null;
        ret = getSyncAdapter().getSyncAdapterBinder();
        return ret;
    }


    private SyncAdapterImpl getSyncAdapter() {
        if (sSyncAdapter == null)
            sSyncAdapter = new SyncAdapterImpl(this);
        return sSyncAdapter;
    }

    private static void performSync(Context context, Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult)
            throws OperationCanceledException {
        mContentResolver = context.getContentResolver();
        Log.d("[&]","performSync: " + account.toString());
        //This is where the magic will happen!
    }

    private static class SyncAdapterImpl extends AbstractThreadedSyncAdapter{
        private Context mContext;


        public SyncAdapterImpl(Context context){
            super(context,true);
            mContext=context;
        }
        @Override
        public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        }
    }
}
