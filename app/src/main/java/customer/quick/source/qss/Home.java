package customer.quick.source.qss;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import net.glxn.qrgen.android.QRCode;

import customer.quick.source.qss.adapters.SettingsTabsAdapter;


public class Home extends FragmentActivity{

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    public static FragmentActivity fa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fa=this;
        setContentView(R.layout.activity_home);
        startService(new Intent(Home.this,AlarmsService.class));



        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setHorizontalScrollBarEnabled(true);

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);



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
