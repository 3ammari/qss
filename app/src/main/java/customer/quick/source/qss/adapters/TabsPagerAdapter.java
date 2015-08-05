package customer.quick.source.qss.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import customer.quick.source.qss.FindStation;
import customer.quick.source.qss.Garage;
import customer.quick.source.qss.R;
import customer.quick.source.qss.Rewards;

/**
 * Created by abdul-rahman on 04/07/15.
 */


public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    Context context;
    public TabsPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
       this.context=context;

    }

    String[] titles= new String[3];
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new Garage();

            case 1:

                return new FindStation();
            case 2:

                return new Rewards();

            /*case 4:

                return new UserIDShowQR();*/

        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        titles= new String[]{context.getResources().getString(R.string.title_activity_garage), context.getResources().getString(R.string.title_activity_find_station), context.getResources().getString(R.string.title_activity_rewards)};

        return titles[position];
    }
}
