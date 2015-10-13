package customer.quick.source.qss;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.List;

import customer.quick.source.qss.ObjectsORM.RemindersPreferencesORM;
import customer.quick.source.qss.ObjectsORM.ServicesTable;
import customer.quick.source.qss.adapters.PreferencesAdapter;


public class PreferencesActivity extends Fragment {

    String baseUrl;
    String userID;
    Context context;
    ListView listView;
    Button okButton;
    AsyncHttpClient client= new AsyncHttpClient();
    private static final String TAG="PREFERECNCES_";
    List<ServicesTable> services;
    boolean save=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        services = new ArrayList<>();
        services= ServicesTable.listAll(ServicesTable.class);
        context = this.getActivity();
        View view = inflater.inflate(R.layout.activity_preferences, container, false);
        baseUrl = GeneralUtilities.BASE_URL;
        userID = GeneralUtilities.getFromPrefs(context, GeneralUtilities.USERID_KEY, "");
        listView = (ListView) view.findViewById(R.id.prefsListView);

        okButton= (Button) view.findViewById(R.id.notifyServerButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save=true;
            }
        });

        List<RemindersPreferencesORM> remindersPreferencesORMs= RemindersPreferencesORM.listAll(RemindersPreferencesORM.class);
        /*ArrayAdapter<ServicesTable> serviceAdapter= new ArrayAdapter<ServicesTable>(context,android.R.layout.simple_list_item_1,services);
        listView.setAdapter(serviceAdapter);*/
        listView.setAdapter(new PreferencesAdapter(context, services));



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder prefsDialog = new AlertDialog.Builder(context);
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                prefsDialog.setView(input);
                prefsDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ServicesTable service = (ServicesTable) parent.getItemAtPosition(position);

                        RemindersPreferencesORM preferencesORM=null;

                        try {
                            preferencesORM = RemindersPreferencesORM.find(RemindersPreferencesORM.class, "service_type_id = ?", String.valueOf(service.getServiceTypeID())).get(0);
                        } catch (Exception e) {
                            preferencesORM = new RemindersPreferencesORM();
                            preferencesORM.setServiceTypeID(service.getServiceTypeID());
                            e.printStackTrace();
                        }

                        Log.d(TAG, String.valueOf(preferencesORM.getServiceTypeID()));
                        preferencesORM.setPeriod(Integer.parseInt(input.getText().toString()));
                        preferencesORM.save();
                    }
                });
                prefsDialog.setTitle("How often you'd like to be notified?");
                prefsDialog.create();
                prefsDialog.show();
            }
        });





        return view;
    }

    private void sendToServer() {
        RequestParams requestParams = new RequestParams();
        List<RemindersPreferencesORM> newPrefs = RemindersPreferencesORM.listAll(RemindersPreferencesORM.class);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < newPrefs.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("service_type_id", newPrefs.get(i).getServiceTypeID());
                jsonObject.put("period", newPrefs.get(i).getPeriod());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
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
    }

    @Override
    public void onStop() {
        super.onStop();
        if (save) {
            sendToServer();
        }
    }
}