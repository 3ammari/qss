package customer.quick.source.qss;




import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import customer.quick.source.qss.adapters.TabsPagerAdapter;


public class Home extends ActionBarActivity{
    private static final String TAG="HOME_TAG";
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    public static FragmentActivity fa;
    int mStackLevel=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String action = getIntent().getStringExtra("Action");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

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
        try {
            Log.d(TAG,action);
            if (action.equals("Notification")){
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("TAG");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                DialogFragment newFragment = NotificationsActivity.newInstance(mStackLevel++);
                newFragment.show(ft, "TAG");
                /*NotificationsActivity notificationsActivity= new NotificationsActivity();
                notificationsActivity.show(getSupportFragmentManager(),"TAG");*/
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


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
        if (id == R.id.notificationsMenu){
            NotificationsActivity notificationsActivity = new NotificationsActivity();
            notificationsActivity.show(getSupportFragmentManager(),"TAG");
            return false;
        }

        return super.onOptionsItemSelected(item);
    }








}
