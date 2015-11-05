package customer.quick.source.qss;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Calendar;


/**
 * Created by abdul-rahman on 24/06/15.
 */
public class NotificationsQSS  {
    private static final String TAG ="NOTIFICATIONS_CLASS" ;
    Context context;
    private static final String NOTIFICATION_GROUP="NOTIFICATION_GROUP";
    private int UNIQUE_NOTIFICATION_ID=41232;

    public NotificationsQSS(Context context) {
        this.context = context;

    }

    public void spawnNotification(String title,String msg,int id){

        Intent intent = new Intent(context,NotificationBar.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification=builder.setContentTitle(title).setContentText(msg).setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_plusone_small_off_client).setSound(alarmSound).setNumber(id).setGroup(NOTIFICATION_GROUP).build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManagerCompat.notify(UNIQUE_NOTIFICATION_ID,notification);


    }

  /* */ public void spawnServiceNotifier(String title ,String msg,int id){

      Intent intent = new Intent(context,NotificationBar.class);
      PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
      Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

      NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
      Notification notification=builder.setContentTitle(title).setContentText(msg).setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_plusone_small_off_client).setSound(alarmSound).setNumber(id).setGroup(NOTIFICATION_GROUP).build();
      //NotificationManagerCompat notificationManager = (NotificationManagerCompat) context.getSystemService(Context.NOTIFICATION_SERVICE);
      NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
      notification.flags |= Notification.FLAG_AUTO_CANCEL;
      notificationManagerCompat.notify(UNIQUE_NOTIFICATION_ID,notification);

  }

    public void spawnBroadcastNotifier(String title,String msg,int id){
        Intent intent = new Intent(context,NotificationBar.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification=builder.setContentTitle(title).setContentText(msg).setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_plusone_small_off_client).setSound(alarmSound).setNumber(id).setGroup(NOTIFICATION_GROUP).build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManagerCompat.notify(UNIQUE_NOTIFICATION_ID,notification);

    }


    public void spawnGeneralNotification(String title,String msg,int id){
        Intent intent = new Intent(context,Home.class);
        intent.putExtra("Action","Notification");
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification=builder.setContentTitle(title).setContentText(msg).setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_plusone_small_off_client).setSound(alarmSound).setNumber(id).setGroup(NOTIFICATION_GROUP).build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManagerCompat.notify(UNIQUE_NOTIFICATION_ID,notification);

    }

    public void gcmNotification(String title,String msg){
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id= (int) Calendar.getInstance().getTimeInMillis();
        Log.d(TAG,"1");
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context).setContentTitle(title).setContentText(msg)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).setSmallIcon(android.R.drawable.ic_media_play);
        builder.setAutoCancel(true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,id,new Intent(context,Home.class),0);
        builder.setContentIntent(pendingIntent);
        Log.d(TAG, "2");
        notificationManager.notify(id,builder.build());

    }



}
