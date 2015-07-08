package customer.quick.source.qss;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import net.glxn.qrgen.android.QRCode;

import customer.quick.source.qss.adapters.SettingsTabsAdapter;


public class Home extends FragmentActivity{

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            String userID=GeneralUtilities.getFromPrefs(Home.this, GeneralUtilities.USERID_KEY, "");
            Dialog dialog = new Dialog(Home.this);
            dialog.setContentView(R.layout.dialog_qr);
            Bitmap myBitmap = QRCode.from(userID).bitmap();
            ImageView imageView = (ImageView) dialog.findViewById(R.id.qrDialog);

            imageView.setImageBitmap(myBitmap);
            dialog.setCancelable(true);
            dialog.show();
/*
            startActivity(new Intent(Home.this,Settings.class));
*/

        }

        return super.onOptionsItemSelected(item);
    }




}
