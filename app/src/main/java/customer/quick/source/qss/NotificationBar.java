package customer.quick.source.qss;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.Collections;
import java.util.List;

import customer.quick.source.qss.ObjectsORM.NotificationORM;
import customer.quick.source.qss.adapters.NotificationsAdapter;

public class NotificationBar extends DialogFragment {
    List<NotificationORM> notificationObjects;
    NotificationsAdapter notificationsAdapter;
    ExpandableListView list;
    Context context;

    public NotificationBar() {
    }
    static NotificationBar newInstance(int num) {
        NotificationBar f = new NotificationBar();

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


}
