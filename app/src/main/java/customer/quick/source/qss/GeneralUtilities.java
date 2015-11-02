package customer.quick.source.qss;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.securepreferences.SecurePreferences;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abdul-rahman on 17/06/15.
 */

// this class is mainly for saving preferences ,am using the encrypted SharedPreferences

public class GeneralUtilities {
    public static final String USERNAME_KEY = "USERNAME" ;
    public static final String PASSWORD_KEY = "PASSWORD" ;
    public static final String BASE_URL_KEY="URL";
    public static final String USERID_KEY="USERID";
    public static final String PIN_KEY="PIN";
    public static final String KEY="KEY";
    public static final String SEASSION_KEY="SEASSION";
    public static final String IMAGE_URI_KEY="IMAGE_URI";
    public static final String SENT_TOKEN_TO_SERVER="SENT_TOKEN_TO_SERVER";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String TOKEN_KEY = "TOKEN";
    public static final String BASE_URL="http://192.168.1.8/api/v1/client/";
    public static final String GCM_DEVICE_TOKEN_KEY="DEVICE_TOKEN_KEY";
    Context context;

    public static void saveToPrefs(Context context, String key, String value) {
        SharedPreferences prefs = new SecurePreferences(context);
        final SecurePreferences.Editor editor= (SecurePreferences.Editor) prefs.edit();
        editor.putString(key,value);
        editor.commit();
    }


    public static void saveToPrefs(Context context, String key, boolean value) {
        SharedPreferences prefs = new SecurePreferences(context);
        final SecurePreferences.Editor editor= (SecurePreferences.Editor) prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void saveToPrefs(Context context, String key, int value) {
        SharedPreferences prefs = new SecurePreferences(context);
        final SecurePreferences.Editor editor= (SecurePreferences.Editor) prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public GeneralUtilities(Context context) {
        this.context=context;
    }

    public static String getFromPrefs(Context context, String key, String defaultValue) {
        SharedPreferences prefs = new SecurePreferences(context);
        try {
            return prefs.getString(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }


    }

    public static boolean getFromPrefs(Context context, String key, boolean defaultValue) {
        SharedPreferences prefs = new SecurePreferences(context);
        try {
            return prefs.getBoolean(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }


    }

    public static int getFromPrefs(Context context, String key, int defaultValue) {
        SharedPreferences prefs = new SecurePreferences(context);
        try {
            return prefs.getInt(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }


    }

    public static boolean checkFromPrefs(Context context,String key){
        SharedPreferences prefs = new SecurePreferences(context);
        if(prefs.contains(key)){return true;}
        else{return false;}
    }
    public static void clearPrefsAll(Context context){
        SharedPreferences prefs = new SecurePreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.remove(USERNAME_KEY);
        editor.remove(PASSWORD_KEY);
        editor.remove(USERID_KEY);
        editor.remove(SEASSION_KEY);
        editor.commit();
        Toast.makeText(context, "All prefs were cleared\n", Toast.LENGTH_LONG).show();
    }
    public static void setPin(Context context,String pin)
    {
        SharedPreferences prefs = new SecurePreferences(context);
        final SharedPreferences.Editor editor =prefs.edit();
        editor.putString(PIN_KEY, pin);
        editor.commit();
    }
    public static boolean checkPin(Context context)
    {   SharedPreferences prefs= new SecurePreferences(context);
        final SharedPreferences.Editor editor=prefs.edit();
        return prefs.contains(PIN_KEY);
    }
    public static boolean validatePin(Context context,String pin)
    {

        String storedPin = getFromPrefs(context,PIN_KEY,"0000");
        if(pin.equals(storedPin)){
            Toast.makeText(context,"CORRECT!",Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            Toast.makeText(context,"WRONG PIN!!!",Toast.LENGTH_SHORT).show();
            return false;
        }


    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            Toast.makeText(context,"No Connection",Toast.LENGTH_LONG).show();
            return false;
        } else
            return true;
    }

    public static void refreshToken(final Context context){
        Log.d("REFRESH_TOKEN","invoked");
        final String username =getFromPrefs(context,USERNAME_KEY,null);
        final String password = getFromPrefs(context,PASSWORD_KEY,null);
        String baseUrl= getFromPrefs(context,BASE_URL_KEY,null);
        if (username!=null && password!=null && baseUrl!=null){
            AsyncHttpClient clientx = new AsyncHttpClient();
            RequestParams requestParams= new RequestParams();
            requestParams.put("username",username);
            requestParams.put("password",password);
            requestParams.add("grant_type","password");
            requestParams.add("client_id","client");
            requestParams.add("scope","client");
            requestParams.add("client_secret", "");
            clientx.post(context, baseUrl +  "oauth/access_token", requestParams, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("REFRESH_TOKEN", String.valueOf(statusCode));

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d("REFRESH_TOKEN",responseString);
                    try {
                        JSONObject jsonObject= new JSONObject(responseString);
                        if (jsonObject.has("error")){
                            Toast.makeText(context,"Wrong username or password",Toast.LENGTH_LONG ).show();
                        }
                        else {
                            String accessToken= jsonObject.getString("access_token");
                            saveToPrefs(context,TOKEN_KEY,accessToken);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }


    }

}
