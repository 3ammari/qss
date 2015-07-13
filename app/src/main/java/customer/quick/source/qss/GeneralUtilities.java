package customer.quick.source.qss;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.securepreferences.SecurePreferences;

/**
 * Created by abdul-rahman on 17/06/15.
 */
public class GeneralUtilities {
    public static final String USERNAME_KEY = "USERNAME" ;
    public static final String PASSWORD_KEY = "PASSWORD" ;
    public static final String BASE_URL_KEY="URL";
    public static final String USERID_KEY="USERID";
    public static final String PIN_KEY="PIN";
    public static final String KEY="KEY";
    public static final String SEASSION_KEY="SEASSION";
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
    public static void clearPrefsUrl(Context context,String url){
        SharedPreferences prefs = new SecurePreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(BASE_URL_KEY,url);
        editor.remove(USERNAME_KEY);
        editor.remove(PASSWORD_KEY);
        editor.remove(USERID_KEY);
        editor.commit();
        Toast.makeText(context, "All prefs were cleared\n", Toast.LENGTH_LONG).show();
    }
    public static void setPin(Context context,String pin)
    {
        SharedPreferences prefs = new SecurePreferences(context);
        final SharedPreferences.Editor editor =prefs.edit();
        editor.putString(PIN_KEY,pin);
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

}
