package customer.quick.source.qss;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.orm.SugarRecord;


/**
 * Created by abdul-rahman on 24/06/15.
 */
public class NotificationsQSS  {
    Context context;
    int notificationID;

    public NotificationsQSS(Context context, int notificationID) {
        this.context = context;
        this.notificationID = notificationID;
    }

    public void fiveDaysNotification(String serviceType){

        Intent intent = new Intent(context,FindStation.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification=builder.setContentTitle("Service Reminder").setContentText("your car needs your attention").setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_plusone_small_off_client).setSound(alarmSound).setNumber(notificationID).build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(++notificationID,notification);


    }

    public void twoDaysNotification(String serviceType){

        Intent intent = new Intent(context,FindStation.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification=builder.setContentTitle("Service Reminder").setContentText("two days to "+serviceType+" date\n find a near station?").setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_plusone_small_off_client).setSound(alarmSound).setNumber(notificationID).build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(++notificationID,notification);
    }

    public void endOfPeriodNotification(String serviceType){
        Intent intent = new Intent(context,FindStation.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification=builder.setContentTitle("Your service period ended").setContentText("find the closest station").setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_plusone_small_off_client).setSound(alarmSound).setNumber(notificationID).build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(++notificationID,notification);
    }

}
