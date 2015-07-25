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


public class Settings extends FragmentActivity {

    private SettingsTabsAdapter tabsPagerAdapter;
    private ViewPager viewPager;
    public static FragmentActivity fb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(GeneralUtilities.getFromPrefs(this,GeneralUtilities.SEASSION_KEY,false))){
            finish();
        }
        fb=this;
        Log.d("[settings activity]", "created");
        setContentView(R.layout.activity_settings);
        Log.d("[settings activity]", "layout sat");
        viewPager= (ViewPager) findViewById(R.id.pagerSetting);
        viewPager.setHorizontalScrollBarEnabled(true);
        tabsPagerAdapter = new SettingsTabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsPagerAdapter);

        Log.d("[settings activity]","end of oncreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!(GeneralUtilities.getFromPrefs(this,GeneralUtilities.SEASSION_KEY,false))){
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
