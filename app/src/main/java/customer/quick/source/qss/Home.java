package customer.quick.source.qss;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import customer.quick.source.qss.adapters.TabsPagerAdapter;


public class Home extends FragmentActivity{

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    public static FragmentActivity fa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fa=Home.this;
        if (!(GeneralUtilities.getFromPrefs(this,GeneralUtilities.SEASSION_KEY,false))){
            finish();
        }
        setContentView(R.layout.activity_home);
        startService(new Intent(Home.this,AlarmsService.class));



        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setHorizontalScrollBarEnabled(true);

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);



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
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
            Log.d("settingsAction", "was pressed");


            startActivity(new Intent(Home.this,Settings.class));

            return false;
           // finish();


        }

        return super.onOptionsItemSelected(item);
    }






}
