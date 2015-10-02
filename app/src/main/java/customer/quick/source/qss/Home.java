package customer.quick.source.qss;



// instead of using a main menu this is probably the best approach to have tabs i'll comment extensively on this class keep reading

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
        // the string action is to determine if the Intent started this activity was coming from NOTIFICATIONS ,this variable will be used to determine to open the notification bar on not
        String action = getIntent().getStringExtra("Action");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        fa=Home.this;
        //
        //this IF CONDITION is to ensure that the user will not be able to view the data if he logs out because the seassion key is sat to false
        // this activity will end before drawing the layout
        // it is commented right now just for testing

       /* if (!(GeneralUtilities.getFromPrefs(this,GeneralUtilities.SEASSION_KEY,false))){
            finish();
        }*/
        setContentView(R.layout.activity_home);
        startService(new Intent(Home.this,AlarmsService.class));


        //pager adapter declared in the xml layout
        viewPager = (ViewPager) findViewById(R.id.pager);
        //setting the scrolling true
        viewPager.setHorizontalScrollBarEnabled(true);
        // the adapter class is in the adapters package
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(mAdapter);
        try {
            // try catch block because the action might be null if not and equels Notifications it will show the notification bar
            Log.d(TAG,action);
            if (action.equals("Notification")){
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("TAG");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                DialogFragment newFragment = NotificationBar.newInstance(mStackLevel++);
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
        /*if (!(GeneralUtilities.getFromPrefs(this,GeneralUtilities.SEASSION_KEY,false))){
            finish();
        }*/

        //same as the one in the beginning
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
            NotificationBar notificationBar = new NotificationBar();
            notificationBar.show(getSupportFragmentManager(),"TAG");
            return false;
        }

        return super.onOptionsItemSelected(item);
    }








}
