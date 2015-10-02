package customer.quick.source.qss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import customer.quick.source.qss.ObjectsORM.RewardORM;
import customer.quick.source.qss.R;

/**
 * Created by abdul-rahman on 04/07/15.
 */
public class RewardsAdapter extends BaseAdapter {
    Context context;
    List<RewardORM> rewardObjects;
    public RewardsAdapter(Context context,List<RewardORM> rewardObjects ) {
        this.context=context;
        this.rewardObjects=rewardObjects;
    }

    @Override
    public int getCount() {
        return rewardObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return rewardObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.reward_single_row,parent,false);
        TextView name = (TextView) row.findViewById(R.id.rewardNameTV);
        TextView cost = (TextView) row.findViewById(R.id.costTV);
        name.setText(rewardObjects.get(position).getTitle());
        cost.setText(Integer.toString(rewardObjects.get(position).getPrice()));
        return row;
    }

}
