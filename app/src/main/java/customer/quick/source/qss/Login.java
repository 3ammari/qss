package customer.quick.source.qss;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
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
    private static String TAG="LOGIN_LOGCAT";
    public static final String ACTION_INITIALIZE="INITIALIZE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, getIntent().getComponent().getClassName());



        setContentView(R.layout.login_designed);
        usernameField= (EditText) findViewById(R.id.usernameField);
        passwordField= (EditText) findViewById(R.id.passwordField);
        registrationButton= (Button) findViewById(R.id.registrationButton);
        loginButton= (Button) findViewById(R.id.loginButton);
        baseUrl=GeneralUtilities.BASE_URL;
        Log.d(TAG,baseUrl);
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
                        requestParams.add("username",username);
                        requestParams.add("password",password);
                        requestParams.add("grant_type","password");
                        requestParams.add("client_id","client");
                        requestParams.add("scope","client");
                        requestParams.add("client_secret", "");


                        client.post(Login.this, baseUrl + "oauth/access_token", requestParams, new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Log.d("====", Integer.toString(statusCode));
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                                Log.d("-=-=", responseString);

                                try {
                                    JSONObject jsonObject= new JSONObject(responseString);
                                    if (jsonObject.has("error")){
                                        Toast.makeText(Login.this,"Wrong Credentials ..!!! ",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        String accessToken=jsonObject.getString("access_token");
                                        int clientID = jsonObject.getInt("client_id");
                                        GeneralUtilities.saveToPrefs(Login.this,GeneralUtilities.USERID_KEY,clientID);
                                        GeneralUtilities.saveToPrefs(Login.this,GeneralUtilities.SEASSION_KEY,true);
                                        GeneralUtilities.saveToPrefs(Login.this,GeneralUtilities.USERNAME_KEY,username);
                                        GeneralUtilities.saveToPrefs(Login.this,GeneralUtilities.PASSWORD_KEY,password);
                                        GeneralUtilities.saveToPrefs(Login.this, GeneralUtilities.TOKEN_KEY, accessToken);
                                        Log.d(TAG,accessToken);
                                       // Intent intent = new Intent(Login.this,Home.class);
                                        Intent intent = new Intent(Login.this,Initialization.class);
                                        intent.setAction(ACTION_INITIALIZE);
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                    else{
                        Toast.makeText(Login.this,"invalid email or password!!!",Toast.LENGTH_LONG).show();}
                }
                else
                {Toast.makeText(Login.this,"No Network",Toast.LENGTH_LONG).show();}
            }
        });
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });
    }



}
