package customer.quick.source.qss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
//there the user is able to change his password

public class ChangePasswordFragment extends Fragment {
    EditText oldPasswordField;
    EditText newPasswordField;
    EditText newPasswordField2;
    String oldPassword;
    String newPassword;
    String newPassword2;
    Button submitButton;
    /*Button qrShowButton;*/
    String baseUrl;
    String userID;
    AsyncHttpClient client=new AsyncHttpClient();
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.options,container,false);
        context=this.getActivity();

        final boolean networkStatus=new GeneralUtilities(context).isNetworkConnected(context);
        oldPasswordField= (EditText) view.findViewById(R.id.oldPasswordField);
        newPasswordField= (EditText) view.findViewById(R.id.newPasswordField);
        newPasswordField2= (EditText) view.findViewById(R.id.newPasswordField2);

        submitButton= (Button) view.findViewById(R.id.submitButton);
        baseUrl=GeneralUtilities.getFromPrefs(context,GeneralUtilities.BASE_URL_KEY,"http://192.168.1.131/api/v1/employee/");
        userID=GeneralUtilities.getFromPrefs(context,GeneralUtilities.USERID_KEY,"");
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword=oldPasswordField.getText().toString();
                newPassword=newPasswordField.getText().toString();
                newPassword2=newPasswordField2.getText().toString();

                if (networkStatus){
                    if(newPassword.equals(newPassword2)&&newPassword.length()>=8){
                        RequestParams requestParams= new RequestParams();
                        requestParams.add("oldpassword",oldPassword);
                        requestParams.add("newpassword",newPassword);
                        Log.d("xxxxxx",baseUrl+userID+"/changepass");
                        client.post(context,baseUrl+userID+"/changepass",requestParams,new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Log.d("-=-=-=",Integer.toString(statusCode));
                                try{
                                    Log.d("-=-=-=",responseString);
                                }catch (Exception e){
                                    e.printStackTrace();}
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                Log.d("-=-=-=-=",responseString);
                            }
                        });
                    }
                }

            }
        });
        return view;
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        final boolean networkStatus=new GeneralUtilities(Options.this).isNetworkConnected(Options.this);
        oldPasswordField= (EditText) findViewById(R.id.oldPasswordField);
        newPasswordField= (EditText) findViewById(R.id.newPasswordField);
        newPasswordField2= (EditText) findViewById(R.id.newPasswordField2);
        qrShowButton = (Button) findViewById(R.id.qrUserIDShow);
        qrShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Options.this,UserIDShowQR.class));
            }
        });
        submitButton= (Button) findViewById(R.id.submitButton);
        baseUrl=GeneralUtilities.getFromPrefs(Options.this,GeneralUtilities.BASE_URL_KEY,"http://192.168.1.131/api/v1/employee/");
        userID=GeneralUtilities.getFromPrefs(Options.this,GeneralUtilities.USERID_KEY,"");
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword=oldPasswordField.getText().toString();
                newPassword=newPasswordField.getText().toString();
                newPassword2=newPasswordField2.getText().toString();

                if (networkStatus){
                    if(newPassword.equals(newPassword2)&&newPassword.length()>=8){
                        RequestParams requestParams= new RequestParams();
                        requestParams.add("oldpassword",oldPassword);
                        requestParams.add("newpassword",newPassword);
                        Log.d("xxxxxx",baseUrl+userID+"/changepass");
                        client.post(Options.this,baseUrl+userID+"/changepass",requestParams,new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Log.d("-=-=-=",Integer.toString(statusCode));
                                try{
                                    Log.d("-=-=-=",responseString);
                                }catch (Exception e){
                                    e.printStackTrace();}
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                Log.d("-=-=-=-=",responseString);
                            }
                        });
                    }
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
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
    }*/
}
