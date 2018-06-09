package com.example.ftn.showbook.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.ftn.showbook.MainActivity;
import com.example.ftn.showbook.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class NotificationService extends FirebaseMessagingService
{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("MESSAGE RECEIVED");
        System.out.println(remoteMessage.getData().get("title"));
        System.out.println(remoteMessage.getData().get("body"));
        super.onMessageReceived(remoteMessage);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "showbook")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("comment_user") + " " + remoteMessage.getData().get("body"))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(remoteMessage.getData().get("comment_user") + " " + remoteMessage.getData().get("body")))
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setVibrate(new long[]{100, 100, 100, 100})
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(defaultSoundUri);


        //TODO: show fragment with comments
// Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, MainActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addNextIntentWithParentStack(resultIntent);

        resultIntent.putExtra("notification", "commentsFragment");

        resultIntent.putExtra("drawerUsername", remoteMessage.getData().get("username")); //mozda nece biti potrebno kad se zavrse notifikacije
        resultIntent.putExtra("showId", Long.parseLong(remoteMessage.getData().get("showId")));
        resultIntent.putExtra("showName", remoteMessage.getData().get("showName"));
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

// Get the PendingIntent containing the entire back stack

//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );


        notificationBuilder.setContentIntent(pIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);


        notificationManager.notify(1, notificationBuilder.build());
    }
}
