package customer.quick.source.qss;

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


public class Registration extends ActionBarActivity {
    String location;
    String name;
    String email;
    String password;
    String password2;
    EditText nameField;
    EditText emailField;
    EditText passwordField;
    EditText password2Field;
    EditText locationField;
    Button submitButton;
    String baseUrl;
    AsyncHttpClient client = new AsyncHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        nameField= (EditText) findViewById(R.id.nameField);
        emailField= (EditText) findViewById(R.id.usenameField);
        passwordField= (EditText) findViewById(R.id.passwordField);
        password2Field= (EditText) findViewById(R.id.rePasswordField);
        locationField = (EditText) findViewById(R.id.locationField);
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=nameField.getText().toString();
                email=emailField.getText().toString();
                password=passwordField.getText().toString();
                password2=password2Field.getText().toString();
                location=locationField.getText().toString();
                baseUrl=GeneralUtilities.getFromPrefs(Registration.this,GeneralUtilities.BASE_URL_KEY,"http://192.168.1.131/api/v1/client/");
                if (email.contains("@")&&(password.equals(password2))&&password.length()>=8){
                    RequestParams requestParams=new RequestParams();
                    requestParams.add("name",name);
                    requestParams.add("email",email);
                    requestParams.add("password",password);
                    requestParams.add("location_id",location);
                    client.post(Registration.this,baseUrl+"register",requestParams,new TextHttpResponseHandler() {

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("==-=-",Integer.toString(statusCode));
                            Toast.makeText(getApplicationContext(),"something went wrong.. try again \n code "+Integer.toString(statusCode),Toast.LENGTH_LONG);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            String status="";
                            Log.d("-=-=-",responseString);
                            try {
                                JSONObject jsonObject = new JSONObject(responseString);
                                status= jsonObject.getString("status");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(status.equals("true"))
                            {
                                GeneralUtilities.saveToPrefs(Registration.this,GeneralUtilities.USERNAME_KEY,email);
                                GeneralUtilities.saveToPrefs(Registration.this,GeneralUtilities.PASSWORD_KEY,password);
                                GeneralUtilities.saveToPrefs(Registration.this,GeneralUtilities.BASE_URL_KEY,baseUrl);
                                startActivity(new Intent(Registration.this,Login.class));
                                finish();

                            }
                        }
                    });

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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
