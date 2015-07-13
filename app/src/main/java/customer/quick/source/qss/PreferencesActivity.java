package customer.quick.source.qss;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import customer.quick.source.qss.ObjectsORM.RemindersPreferencesORM;
import customer.quick.source.qss.adapters.PreferencesAdapter;


public class PreferencesActivity extends Fragment {

    String baseUrl;
    String userID;
    Context context;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = this.getActivity();
        View view = inflater.inflate(R.layout.activity_preferences, container, false);
        baseUrl = GeneralUtilities.getFromPrefs(context, GeneralUtilities.BASE_URL_KEY, "http://192.168.1.131/api/v1/client/");
        userID = GeneralUtilities.getFromPrefs(context, GeneralUtilities.USERID_KEY, "");
        listView = (ListView) view.findViewById(R.id.prefsListView);
        List<RemindersPreferencesORM> remindersPreferencesORMs= RemindersPreferencesORM.listAll(RemindersPreferencesORM.class);
        listView.setAdapter(new PreferencesAdapter(context,remindersPreferencesORMs));





/*
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*try {
                    oil = oilCycle.getText().toString();
                    filter = filterCycle.getText().toString();
                    checkup = checkupCycle.getText().toString();

                    GeneralUtilities.saveToPrefs(context, "OIL_CYCLE", oil);
                    GeneralUtilities.saveToPrefs(context, "FILTER_CYCLE", filter);
                    GeneralUtilities.saveToPrefs(context, "CHECKUP_CYCLE", checkup);
                    ServicesTable servicesTable1 = ServicesTable.find(ServicesTable.class, "service_type_id = ?", "1").get(0);
                    servicesTable1.setCycle(oil);
                    ServicesTable servicesTable2 = ServicesTable.find(ServicesTable.class, "service_type_id = ?", "2").get(0);
                    servicesTable2.setCycle(filter);
                    ServicesTable servicesTable3 = ServicesTable.find(ServicesTable.class, "service_type_id = ?", "3").get(0);
                    servicesTable3.setCycle(checkup);
                    servicesTable1.save();
                    servicesTable2.save();
                    servicesTable3.save();
                    List<ServicesTable> table = ServicesTable.listAll(ServicesTable.class);
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < table.size(); i++) {
                        JSONObject jsonObject = new JSONObject();
                        String serviceTypeID = table.get(i).getServiceTypeID();
                        String cycle = table.get(i).getCycle();
                        jsonObject.put("service_type_id", serviceTypeID);
                        jsonObject.put("period", cycle);
                        jsonArray.put(jsonObject);
                    }
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("preferences", jsonArray);
                    client.post(context, baseUrl + userID + "/preferences/reminders", requestParams, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("[preferences]", String.valueOf(statusCode));
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            Log.d("[preferences]", responseString);
                        }
                    });


                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
*/
        return view;
    }



  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseUrl = GeneralUtilities.getFromPrefs(PreferencesActivity.this,GeneralUtilities.BASE_URL_KEY,"http://192.168.1.131/api/v1/client/");
        userID = GeneralUtilities.getFromPrefs(PreferencesActivity.this,GeneralUtilities.USERID_KEY,"");

        setContentView(R.layout.activity_preferences);
        final EditText oilCycle= (EditText) findViewById(R.id.oilCycleTextField);
        final EditText filterCycle= (EditText) findViewById(R.id.filterCycleTextField);
        final EditText checkupCycle= (EditText) findViewById(R.id.checkupCycleTextField);

        Button saveButton= (Button) findViewById(R.id.savePreferences);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    oil=oilCycle.getText().toString();
                    filter=filterCycle.getText().toString();
                    checkup=checkupCycle.getText().toString();

                    GeneralUtilities.saveToPrefs(PreferencesActivity.this,"OIL_CYCLE",oil);
                    GeneralUtilities.saveToPrefs(PreferencesActivity.this,"FILTER_CYCLE",filter);
                    GeneralUtilities.saveToPrefs(PreferencesActivity.this,"CHECKUP_CYCLE",checkup);
                    ServicesTable servicesTable1 =ServicesTable.find(ServicesTable.class,"service_type_id = ?","1").get(0);
                    servicesTable1.setCycle(oil);
                    ServicesTable servicesTable2 =ServicesTable.find(ServicesTable.class,"service_type_id = ?","2").get(0);
                    servicesTable2.setCycle(filter);
                    ServicesTable servicesTable3 =ServicesTable.find(ServicesTable.class,"service_type_id = ?","3").get(0);
                    servicesTable3.setCycle(checkup);
                    servicesTable1.save();
                    servicesTable2.save();
                    servicesTable3.save();
                    List<ServicesTable> table=ServicesTable.listAll(ServicesTable.class);
                    JSONArray jsonArray= new JSONArray();
                    for (int i = 0; i <table.size(); i++) {
                        JSONObject jsonObject= new JSONObject();
                        String serviceTypeID =table.get(i).getServiceTypeID();
                        String cycle= table.get(i).getCycle();
                        jsonObject.put("service_type_id",serviceTypeID);
                        jsonObject.put("period",cycle);
                        jsonArray.put(jsonObject);
                    }
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("preferences",jsonArray);
                    client.post(PreferencesActivity.this,baseUrl+userID+"/preferences/reminders",requestParams,new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("[preferences]", String.valueOf(statusCode));
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            Log.d("[preferences]",responseString);
                        }
                    });


                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
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

*/
}