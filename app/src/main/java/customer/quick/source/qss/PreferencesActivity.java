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
    Button okButton;
    AsyncHttpClient client= new AsyncHttpClient();
    private static final String TAG="PREFERECNCES_";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = this.getActivity();
        View view = inflater.inflate(R.layout.activity_preferences, container, false);
        baseUrl = GeneralUtilities.getFromPrefs(context, GeneralUtilities.BASE_URL_KEY, "http://192.168.1.131/api/v1/client/");
        userID = GeneralUtilities.getFromPrefs(context, GeneralUtilities.USERID_KEY, "");
        listView = (ListView) view.findViewById(R.id.prefsListView);
        okButton= (Button) view.findViewById(R.id.notifyServerButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        List<RemindersPreferencesORM> remindersPreferencesORMs= RemindersPreferencesORM.listAll(RemindersPreferencesORM.class);
        listView.setAdapter(new PreferencesAdapter(context, remindersPreferencesORMs));

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                final Dialog dialog = new Dialog(context);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.preferences_dialog);
                final EditText frequencyField = (EditText) dialog.findViewById(R.id.frequencyField);
                Button okButton = (Button) dialog.findViewById(R.id.prefsDialogButton);
                dialog.show();
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        RemindersPreferencesORM preferencesORM = (RemindersPreferencesORM) parent.getItemAtPosition(position);
                        Log.d("[zxcv]", String.valueOf(preferencesORM.getServiceTypeID()));
                        try {
                            int days = Integer.parseInt(frequencyField.getText().toString());
                            preferencesORM.setPeriod(days);
                            preferencesORM.save();
                        } catch (NullPointerException | NumberFormatException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });


            }

        });*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder prefsDialog=new AlertDialog.Builder(context);
                final EditText input= new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                prefsDialog.setView(input);
                prefsDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RemindersPreferencesORM preferencesORM= (RemindersPreferencesORM) parent.getItemAtPosition(position);
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




}