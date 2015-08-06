package customer.quick.source.qss;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import customer.quick.source.qss.adapters.SettingsTabsAdapter;
//same as home activity nothing different

public class Settings extends FragmentActivity {

    private SettingsTabsAdapter tabsPagerAdapter;
    private ViewPager viewPager;
    public static FragmentActivity fb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (!(GeneralUtilities.getFromPrefs(this,GeneralUtilities.SEASSION_KEY,false))){
            finish();
        }*/
        fb=this;
        Log.d("[settings activity]", "created");
        setContentView(R.layout.activity_settings);
        Log.d("[settings activity]", "layout sat");
        viewPager= (ViewPager) findViewById(R.id.pagerSetting);
        viewPager.setHorizontalScrollBarEnabled(true);
        tabsPagerAdapter = new SettingsTabsAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(tabsPagerAdapter);

        Log.d("[settings activity]","end of oncreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* if (!(GeneralUtilities.getFromPrefs(this,GeneralUtilities.SEASSION_KEY,false))){
            finish();
        }*/
    }


}
