package com.example.ftn.showbook.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.ftn.showbook.R;
import com.example.ftn.showbook.ShowCommentsFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {

        String tkn = FirebaseInstanceId.getInstance().getToken();
        Log.d("Not","Token ["+tkn+"]");

        sendRegistrationToServer(tkn);

    }


    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.



//        Intent intent = new Intent(this, ShowCommentsFragment.class);
//        intent.setAction(Intent.ACTION_MAIN);
////        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent.putExtra("drawerUsername", "Jeja");
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "1")
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle("FirebaseIDService")
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
////        NotificationManager notificationManager =
////                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//        notificationManager.notify(1410 /* ID of notification */, notificationBuilder.build());
    }
}
