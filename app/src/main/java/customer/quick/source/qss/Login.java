package customer.quick.source.qss;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


public class Login extends ActionBarActivity {
    EditText usernameField;
    EditText passwordField;
    String username;
    String password;
    Button loginButton;
    Button registrationButton;
    String baseUrl;
    AsyncHttpClient client = new AsyncHttpClient();
    GeneralUtilities generalUtilities=new GeneralUtilities(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameField= (EditText) findViewById(R.id.userNameField);
        passwordField= (EditText) findViewById(R.id.passwordField);
        registrationButton= (Button) findViewById(R.id.registrationButton);
        loginButton= (Button) findViewById(R.id.loginButton);

        baseUrl=GeneralUtilities.getFromPrefs(Login.this,GeneralUtilities.BASE_URL_KEY,"http://192.168.1.131/api/v1/client/");
        if (GeneralUtilities.checkFromPrefs(Login.this,GeneralUtilities.USERNAME_KEY)&&GeneralUtilities.checkFromPrefs(Login.this,GeneralUtilities.PASSWORD_KEY)){
            usernameField.setText(GeneralUtilities.getFromPrefs(Login.this,GeneralUtilities.USERNAME_KEY,""));
            passwordField.setText(GeneralUtilities.getFromPrefs(Login.this,GeneralUtilities.PASSWORD_KEY,""));
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams requestParams= new RequestParams();
                username=usernameField.getText().toString();
                password= passwordField.getText().toString();

                if(generalUtilities.isNetworkConnected(Login.this)){
                    if (username.contains("@")&&password.length()>=8){
                        requestParams.add("email",username);
                        requestParams.add("password",password);
                        client.post(Login.this,baseUrl+"login",requestParams,new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Log.d("====",Integer.toString(statusCode));
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                                Log.d("-=-=",responseString);
                                try {
                                    JSONObject jsonObject= new JSONObject(responseString);
                                    String status= jsonObject.getString("status");
                                    String id= jsonObject.getString("id");
                                    if (status.equals("true")){
                                  /*      //beginning of AccountManager Integration
                                        Account account = new Account(username,"QSS");
                                        AccountManager am = AccountManager.get(Login.this);
                                        boolean accountCreated = am.addAccountExplicitly(account, password, null);
                                        Bundle extras = getIntent().getExtras();
                                        if (extras != null) {
                                            if (accountCreated) {  //Pass the new account back to the account manager
                                                AccountAuthenticatorResponse response = extras.getParcelable(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);
                                                Bundle result = new Bundle();
                                                result.putString(AccountManager.KEY_ACCOUNT_NAME, username);
                                                result.putString(AccountManager.KEY_ACCOUNT_TYPE,"QSS");
                                                result.putString("UserID",id);

                                                response.onResult(result);
                                            }
                                            //GeneralUtilities.saveToPrefs(Login.this,GeneralUtilities.USERID_KEY,id);

                                            startService(new Intent(Login.this,MyService.class));
                                            startActivity(new Intent(Login.this,MainMenu.class));
                                            finish();
                                        } //End of AccountManager Integration
*/

                                        GeneralUtilities.saveToPrefs(Login.this,GeneralUtilities.USERNAME_KEY,username);
                                        GeneralUtilities.saveToPrefs(Login.this,GeneralUtilities.PASSWORD_KEY,password);
                                        GeneralUtilities.saveToPrefs(Login.this,GeneralUtilities.USERID_KEY,id);
                                        startService(new Intent(Login.this,MyService.class));
                                        startActivity(new Intent(Login.this,MainMenu.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"wrong credentials !!",Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"invalid email or password!!!",Toast.LENGTH_LONG).show();}
                }
                else
                {Toast.makeText(getApplicationContext(),"No Network",Toast.LENGTH_LONG).show();}
            }
        });
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
