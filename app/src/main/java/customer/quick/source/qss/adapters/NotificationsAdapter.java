package customer.quick.source.qss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import customer.quick.source.qss.ObjectsORM.NotificationORM;
import customer.quick.source.qss.R;

/**
 * Created by abdul-rahman on 25/07/15.
 */
public class NotificationsAdapter extends BaseExpandableListAdapter {
    private List<NotificationORM> list;
    private Context context;
    public NotificationsAdapter(Context context,List<NotificationORM> list) {
        this.context= context;
        this.list=list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(childPosition).getMsg();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (list.get(groupPosition).isSeen()) {
            convertView = inflater.inflate(R.layout.list_group_seen, parent, false);
        }else{convertView= inflater.inflate(R.layout.list_group,parent,false);}
        TextView titleTV = (TextView) convertView.findViewById(R.id.notificationTitle);
        TextView typeTV = (TextView) convertView.findViewById(R.id.notificationType);
        titleTV.setText(list.get(groupPosition).getTitle());
        typeTV.setText(list.get(groupPosition).getType());


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.notification_expandable_msg,parent,false);
        }
        TextView msgTV= (TextView) convertView.findViewById(R.id.childNotificationMsg);
        msgTV.setText(list.get(groupPosition).getMsg());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
