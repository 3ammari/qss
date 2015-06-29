package customer.quick.source.qss;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTimeUtils;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.List;


public class TestingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tesing);
        Button b = (Button) findViewById(R.id.button2);
        startService(new Intent(TestingActivity.this,MyService.class));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JodaTimeAndroid.init(TestingActivity.this);
/*

                List<RecentServices> recentServicesList=RecentServices.find(RecentServices.class,"service_type = ?","Filter Change");
                String recentServiceDate=recentServicesList.get(recentServicesList.size()-1).getDate();
                String date[]=recentServiceDate.split(" ");
                String date1= date[0];
                Log.d("[11]",date1);
                DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-mm-dd");
                LocalDate localDate = dtf.parseLocalDate(date1);
                LocalDate currentDate = new LocalDate();
                Period period1= new Period(localDate,currentDate);
                //Period period = Period.fieldDifference(localDate.dayOfMonth().withMaximumValue(),currentDate.dayOfMonth().withMinimumValue());
               // Period period = Period.fieldDifference(currentDate.dayOfMonth().withMaximumValue(),localDate.dayOfMonth().withMinimumValue());
                Log.d("[1]", currentDate.getDayOfMonth()+"\n"+String.valueOf(period1.getDays()));

*/



                /*LocalDateTime endOfMonth = now.dayOfMonth().withMaximumValue();
                  LocalDateTime firstOfMonth = now.dayOfMonth().withMinimumValue();
                  Period.fieldDifference(firstOfMonth, endOfMonth)*/
/*
                Duration duration = new Duration(recentServiceDate.toDateTime(),currentDate.toDateTime());
*/

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tesing, menu);
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
