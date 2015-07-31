package customer.quick.source.qss;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.Collections;
import java.util.List;

import customer.quick.source.qss.ObjectsORM.NotificationORM;
import customer.quick.source.qss.adapters.NotificationsAdapter;


public class NotificationsActivity extends DialogFragment {
    List<NotificationORM> notificationObjects;
    NotificationsAdapter notificationsAdapter;
    ExpandableListView list;
    Context context;

    public NotificationsActivity() {
    }
    static NotificationsActivity newInstance(int num) {
        NotificationsActivity f = new NotificationsActivity();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context= this.getActivity();
        notificationObjects = NotificationORM.listAll(NotificationORM.class);

        Collections.reverse(notificationObjects);
        notificationsAdapter = new NotificationsAdapter(context,notificationObjects);
        View view = inflater.inflate(R.layout.activity_notifications,container,false);
        list = (ExpandableListView) view.findViewById(R.id.expandableListView);
        Button clearButton = (Button) view.findViewById(R.id.notifcationClearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationORM.executeQuery("UPDATE NOTIFICATION_ORM SET SEEN = '1'");
                notificationObjects = NotificationORM.listAll(NotificationORM.class);
                notificationsAdapter= new NotificationsAdapter(context,notificationObjects);
                Collections.reverse(notificationObjects);
                /*for (int i = 0; i < notificationObjects.size(); i++) {
                    notificationObjects.get(i).setSeen(true);
                }
                NotificationORM.saveInTx(notificationObjects);*/
                list.setAdapter(notificationsAdapter);
            }
        });

        if (!notificationObjects.isEmpty()){

            list.setAdapter(notificationsAdapter);
        }

        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                NotificationORM notificationObject= (NotificationORM) parent.getItemAtPosition(groupPosition);
                notificationObject.setSeen(true);
                notificationObject.save();


                return false;
            }
        });
    return view;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationObjects = NotificationORM.listAll(NotificationORM.class);

        Collections.reverse(notificationObjects);
        notificationsAdapter = new NotificationsAdapter(this,notificationObjects);
        setContentView(R.layout.activity_notifications);
        list = (ExpandableListView) findViewById(R.id.expandableListView);
        Button clearButton = (Button) findViewById(R.id.notifcationClearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < notificationObjects.size(); i++) {
                    notificationObjects.get(i).setSeen(true);
                }
                NotificationORM.saveInTx(notificationObjects);
                list.setAdapter(notificationsAdapter);
            }
        });

        if (!notificationObjects.isEmpty()){

            list.setAdapter(notificationsAdapter);
        }

        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               NotificationORM notificationObject= (NotificationORM) parent.getItemAtPosition(groupPosition);
                notificationObject.setSeen(true);
                notificationObject.save();


                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
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
    }*/
}
