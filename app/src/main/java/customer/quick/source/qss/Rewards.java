package customer.quick.source.qss;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import customer.quick.source.qss.adapters.RewardObject;
import customer.quick.source.qss.adapters.RewardsAdapter;
//this is where the user will be able to fetch the rewards and his points
// it needs some improvements but it require connection with the server

public class Rewards extends Fragment {
    String baseUrl;
    ListView listView;
    ArrayList<String> rewards;
    AsyncHttpClient client = new AsyncHttpClient();
    List<RewardObject> objects;
    TextView msgBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        objects = new ArrayList<RewardObject>();
        final FragmentActivity fa = (FragmentActivity) super.getActivity();
        final Context context = super.getActivity();
        View view =inflater.inflate(R.layout.activity_rewards,container,false);
        rewards= new ArrayList<>();
        TextView pointsTextView = (TextView) view.findViewById(R.id.pointsTextView);
        pointsTextView.setText(GeneralUtilities.getFromPrefs(super.getActivity(),"POINTS","0"));
        Log.d("[rewards_text]", GeneralUtilities.getFromPrefs(super.getActivity(), "POINTS", "2"));
        listView= (ListView) view.findViewById(R.id.rewardsListView);
        msgBox = (TextView) view.findViewById(R.id.descriptionMsg);
        baseUrl=GeneralUtilities.getFromPrefs(super.getActivity(),GeneralUtilities.BASE_URL_KEY,"http://192.168.1.131/api/v1/client/");
        Button rewardsRefreshButton = (Button) view.findViewById(R.id.rewardsRefreshButton);
        rewardsRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.get(context,baseUrl+"rewards",null,new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("[rewards]",Integer.toString(statusCode));

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.d("[rewards]",responseString);
                        if (!objects.isEmpty()){objects.clear();}
                        try {
                            JSONArray jsonArray = new JSONArray(responseString);
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                                String title=jsonObject.getString("title");
                                String cost= jsonObject.getString("cost");
                                String description = jsonObject.getString("description");
                                objects.add(new RewardObject(title,description,Integer.parseInt(cost)));
                            }
/*
                                rewards.add(title+" for "+cost+" points");
*/

                                //ArrayAdapter<String> adapter= new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,rewards);
                                listView.setAdapter(new RewardsAdapter(context,objects));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                msgBox.setText(objects.get(position).description);

            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }




    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d("settingsAction", "was pressed");

            /*String userID=GeneralUtilities.getFromPrefs(Home.this, GeneralUtilities.USERID_KEY, "");
            Dialog dialog = new Dialog(Home.this);
            dialog.setContentView(R.layout.dialog_qr);
            Bitmap myBitmap = QRCode.from(userID).bitmap();
            ImageView imageView = (ImageView) dialog.findViewById(R.id.qrDialog);

            imageView.setImageBitmap(myBitmap);
            dialog.setCancelable(true);
            dialog.show();*/
            startActivity(new Intent(this.getActivity(),Settings.class));


        }

        return super.onOptionsItemSelected(item);
    }
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);
        rewards= new ArrayList<>();
        TextView pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        pointsTextView.setText(GeneralUtilities.getFromPrefs(Rewards.this,"POINTS","0"));
        Log.d("[rewards_text]",GeneralUtilities.getFromPrefs(Rewards.this,"POINTS","2"));
        listView= (ListView) findViewById(R.id.rewardsListView);
        baseUrl=GeneralUtilities.getFromPrefs(Rewards.this,GeneralUtilities.BASE_URL_KEY,"http://192.168.1.131/api/v1/client/");
        Button rewardsRefreshButton = (Button) findViewById(R.id.rewardsRefreshButton);
        rewardsRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.get(Rewards.this,baseUrl+"rewards",null,new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("[rewards]",Integer.toString(statusCode));

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.d("[rewards]",responseString);
                        try {
                            JSONArray jsonArray = new JSONArray(responseString);
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                                String title=jsonObject.getString("title");
                                String cost= jsonObject.getString("cost");
                                rewards.add(title+" for "+cost+" points");

                                ArrayAdapter<String> adapter= new ArrayAdapter<>(Rewards.this,android.R.layout.simple_list_item_1,rewards);
                                listView.setAdapter(adapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rewards, menu);
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
