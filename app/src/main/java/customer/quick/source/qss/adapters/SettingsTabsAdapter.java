package customer.quick.source.qss.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import customer.quick.source.qss.LogoutFragment;
import customer.quick.source.qss.Options;
import customer.quick.source.qss.PreferencesActivity;
import customer.quick.source.qss.UserIDShowQR;

/**
 * Created by abdul-rahman on 05/07/15.
 */
public class SettingsTabsAdapter extends FragmentPagerAdapter {
    String[] titles={"Notifications Frequencies","Changing Password","USER ID QR Code","Log Out"};
    public SettingsTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               return new PreferencesActivity();
           case 1:
               return new Options();
           case 2:
               return new UserIDShowQR();
           case 3:
               return new LogoutFragment();



       }

        return new UserIDShowQR();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
