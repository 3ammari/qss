package customer.quick.source.qss;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

// unused service meant for the Authenticator and Authentication IGNORE IT FOR NOW
public class AccountAuthenticatorService extends Service {

    private static final String TAG = "ACCOUNT_SERVICE";
    private static AccountAuthenticatorImpl sAccountAuthenticator = null;

    private static AsyncHttpClient client= new AsyncHttpClient();
    static String authToken;
    public AccountAuthenticatorService() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        IBinder ret = null;
        if (intent.getAction().equals(AccountManager.ACTION_AUTHENTICATOR_INTENT))
            ret = getAuthenticator().getIBinder();
        return ret;
    }

    private AccountAuthenticatorImpl getAuthenticator() {
        if (sAccountAuthenticator == null)
            sAccountAuthenticator = new AccountAuthenticatorImpl(this);
        return sAccountAuthenticator;
    }

    public static class AccountAuthenticatorImpl extends AbstractAccountAuthenticator{
        private Context mContext;
        public AccountAuthenticatorImpl(Context context) {
            super(context);
            mContext=context;
        }

        @Override
        public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
            return null;
        }

        @Override
        public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
            Bundle reply = new Bundle();

            Intent i = new Intent(mContext, Login.class);
            i.setAction("customer.quick.source.qss.Login");
            i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
            reply.putParcelable(AccountManager.KEY_INTENT, i);

            return reply;
        }

        @Override
        public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, final String authTokenType, Bundle options) throws NetworkErrorException {
            final AccountManager am = AccountManager.get(mContext);

            authToken = am.peekAuthToken(account, authTokenType);

            // Lets give another try to authenticate the user
            if (TextUtils.isEmpty(authToken)) {
                final String password = am.getPassword(account);
                if (password != null) {
                    RequestParams requestParams= new RequestParams();
                    requestParams.put("username",account.name);
                    requestParams.put("password",password);
                    client.post(mContext, "url", requestParams, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d(TAG, String.valueOf(statusCode));
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            Log.d(TAG,responseString);
                            //experimental
                                authToken=responseString;
                            }
                    });
                    /*authToken = sServerAuthenticate.userSignIn(account.name, password, authTokenType);*/
                }
            }

            // If we get an authToken - we return it
            if (!TextUtils.isEmpty(authToken)) {
                final Bundle result = new Bundle();
                result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
                return result;
            }

            // If we get here, then we couldn't access the user's password - so we
            // need to re-prompt them for their credentials. We do that by creating
            // an intent to display our AuthenticatorActivity.
            final Intent intent = new Intent(mContext, Login.class);
            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
            intent.putExtra("QSS_ACCOUNT", account.type);
            intent.putExtra("QSS_TOKEN", authTokenType);
            final Bundle bundle = new Bundle();
            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
            return bundle;

        }

        @Override
        public String getAuthTokenLabel(String authTokenType) {
            return null;
        }

        @Override
        public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
            return null;
        }
    }
}
