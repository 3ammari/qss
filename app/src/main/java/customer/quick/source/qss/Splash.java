package customer.quick.source.qss;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Splash extends ActionBarActivity {
    String email;
    String password;
    String baseURL;
    String userID;
    AccountManager accountManager;
    AsyncHttpClient client= new AsyncHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

/*        accountManager= AccountManager.get(Splash.this);

        boolean isThereAccount=false;
        if (!(accountManager.getAccounts().length==0)){
            Account qSSAccount = accountManager.getAccountsByType("QSS")[0];
            email=accountManager.getUserData(qSSAccount,accountManager.KEY_ACCOUNT_NAME);
            password=accountManager.getPassword(qSSAccount);
            userID=accountManager.getUserData(qSSAccount,"userID");
            isThereAccount=true;
        }
        if (isThereAccount){

        }*/
        boolean networkStatus=new GeneralUtilities(Splash.this).isNetworkConnected(Splash.this);

        /*try{
           Thread.sleep(5000,Thread.MIN_PRIORITY);
        }catch(Exception e){
            e.printStackTrace();}*/
        userID= GeneralUtilities.getFromPrefs(Splash.this,GeneralUtilities.USERID_KEY,"");
        email=GeneralUtilities.getFromPrefs(Splash.this,GeneralUtilities.USERNAME_KEY,"");
        password=GeneralUtilities.getFromPrefs(Splash.this,GeneralUtilities.PASSWORD_KEY,"");

        baseURL=GeneralUtilities.getFromPrefs(Splash.this,GeneralUtilities.BASE_URL_KEY,"http://192.168.1.131/api/v1/client/");

        Log.d("*******",userID+"\n"+email+"\n"+password+"\n"+baseURL);
        RequestParams requestParams= new RequestParams();
        requestParams.add("email",email);
        requestParams.add("password",password);
        if (networkStatus){



            client.post(Splash.this,baseURL+"login",requestParams,new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("-=-=-=",Integer.toString(statusCode));
                    startActivity(new Intent(Splash.this,Login.class));

                }


                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                   String status="";
                    Log.d("-=-=-=",responseString);
                    try {
                        JSONObject jsonObject = new JSONObject(responseString);
                        status=jsonObject.getString("status");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("true")){
                        startService(new Intent(Splash.this,MyService.class));

                        Intent intent =new Intent(Splash.this,MainMenu.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        startActivity(new Intent(Splash.this,Login.class));
                        finish();
                    }
                }
            });


        }
        else
        {
            Toast.makeText(Splash.this,"No Network",Toast.LENGTH_LONG).show();
            startActivity(new Intent(Splash.this,Login.class));
            finish();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
