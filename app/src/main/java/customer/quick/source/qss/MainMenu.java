package customer.quick.source.qss;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainMenu extends ActionBarActivity {

    Button findStation;
    Button guidelines;
    Button qrUserIDShow;
    Button optionButton;
    Button garage;
    Button rewards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //startService(new Intent(MainMenu.this,AlarmsService.class));
        optionButton = (Button) findViewById(R.id.optionsButton);
       // checkStatus= (Button) findViewById(R.id.checkStatus);
        rewards = (Button) findViewById(R.id.rewardsButton);

        findStation= (Button) findViewById(R.id.findStation);
        garage= (Button) findViewById(R.id.garageButton);
        qrUserIDShow= (Button) findViewById(R.id.qrUserIDShow);
        qrUserIDShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this,UserIDShowQR.class));
            }
        });
        rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this,Rewards.class));
            }
        });
        /*checkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this,StatusActivity.class));
            }
        });*/
        findStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   startActivity(new Intent(MainMenu.this,MapActivity.class));
            }
        });
        garage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(MainMenu.this,Garage.class));
            }
        });
        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this,Options.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
