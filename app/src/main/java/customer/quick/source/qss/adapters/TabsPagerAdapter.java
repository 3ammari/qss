package customer.quick.source.qss.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import customer.quick.source.qss.FindStation;
import customer.quick.source.qss.Garage;
import customer.quick.source.qss.Rewards;

/**
 * Created by abdul-rahman on 04/07/15.
 */


public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new Rewards();

            case 1:

                return new Garage();

            case 2:

                return new FindStation();

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
        return String.valueOf(position);
    }
}
