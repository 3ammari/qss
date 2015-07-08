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


    public NotificationsQSS(Context context) {
        this.context = context;

    }

    public void spawnNotification(String title,String msg,int id){

        Intent intent = new Intent(context,FindStation.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification=builder.setContentTitle(title).setContentText(msg).setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_plusone_small_off_client).setSound(alarmSound).setNumber(id).build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(id,notification);


    }

  /* */ public void spawnServiceNotifier(String title ,String msg,int id){

      Intent intent = new Intent(context,Home.class);
      PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
      Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

      NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
      Notification notification=builder.setContentTitle(title).setContentText(msg).setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_plusone_small_off_client).setSound(alarmSound).setNumber(id).build();
      NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      notification.flags |= Notification.FLAG_AUTO_CANCEL;
      notificationManager.notify(id,notification);

  }

    public void spawnBroadcastNotifier(String title,String msg,int id){
        Intent intent = new Intent(context,Home.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification=builder.setContentTitle(title).setContentText(msg).setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_plusone_small_off_client).setSound(alarmSound).setNumber(id).build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(id,notification);

    }

}
