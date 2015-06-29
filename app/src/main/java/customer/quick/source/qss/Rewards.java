package customer.quick.source.qss;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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


public class Rewards extends ActionBarActivity {
    String baseUrl;
    ListView listView;
    ArrayList<String> rewards;
    AsyncHttpClient client = new AsyncHttpClient();

    @Override
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
    }
}
