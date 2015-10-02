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
import android.widget.BaseAdapter;
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

import customer.quick.source.qss.ObjectsORM.RewardORM;
import customer.quick.source.qss.adapters.RewardObject;
import customer.quick.source.qss.adapters.RewardsAdapter;
//this is where the user will be able to fetch the rewards and his points
// it needs some improvements but it require connection with the server

public class Rewards extends Fragment {
    String baseUrl;
    ListView listView;
    ArrayList<String> rewards;
    List<RewardORM> objects;
    TextView msgBox;
    List<RewardORM> rewardsORM;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rewardsORM= new ArrayList<>();
        rewardsORM= RewardORM.listAll(RewardORM.class);
        //return super.onCreateView(inflater, container, savedInstanceState);
        objects = new ArrayList<>();
        objects=RewardORM.listAll(RewardORM.class);
        final FragmentActivity fa = (FragmentActivity) super.getActivity();
        final Context context = super.getActivity();
        View view = inflater.inflate(R.layout.activity_rewards, container, false);
        rewards = new ArrayList<>();
        TextView pointsTextView = (TextView) view.findViewById(R.id.pointsTextView);
        pointsTextView.setText(GeneralUtilities.getFromPrefs(super.getActivity(), "POINTS", "0"));
        Log.d("[rewards_text]", GeneralUtilities.getFromPrefs(super.getActivity(), "POINTS", "2"));
        listView = (ListView) view.findViewById(R.id.rewardsListView);
        msgBox = (TextView) view.findViewById(R.id.descriptionMsg);
        baseUrl = GeneralUtilities.getFromPrefs(super.getActivity(), GeneralUtilities.BASE_URL_KEY, "http://192.168.1.131/api/v1/client/");
        Button rewardsRefreshButton = (Button) view.findViewById(R.id.rewardsRefreshButton);



        listView.setAdapter(new RewardsAdapter(context,rewardsORM));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                msgBox.setText(objects.get(position).getDescription());

            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
    }
}
