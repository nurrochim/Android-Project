package com.rohim.firebase;

/**
 * Created by Nurochim on 27/09/2016.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rohim.jasaservice.MainActivity;
import com.rohim.jasaservice.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification());
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(RemoteMessage.Notification notification) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        String notifTitleSplit[] = notification.getTitle().split("#");
        String notifTitle = notifTitleSplit[0];
        intent.putExtra("msgId", notifTitle);
        String idRequest = "";
        if(notifTitle.equals("RO")){
            notifTitle = "New Task";
            idRequest = notifTitleSplit[1];
            intent.putExtra("idRequest", idRequest);
        }

        if(notifTitle.equals("PROCESS")){
            notifTitle = "System Notification";
            idRequest = notifTitleSplit[1];
            intent.putExtra("idRequest", idRequest);
            intent.putExtra("idUserAccepted", notifTitleSplit[2]);
            intent.putExtra("userNameAccepted", notifTitleSplit[3]);
            intent.putExtra("userNoTelp", notifTitleSplit[4]);
        }

        if(notifTitle.equals("CANCEL1")){
            notifTitle = "System Replace Enginer";
            idRequest = notifTitleSplit[1];
            intent.putExtra("idRequest", idRequest);
            intent.putExtra("msgTitle", notifTitle);
            intent.putExtra("msgBody", notification.getBody());
        }

        if(notifTitle.equals("FINISH")){
            notifTitle = "Finish Service Confirmation";
            idRequest = notifTitleSplit[1];
            intent.putExtra("idRequest", idRequest);
            intent.putExtra("msgTitle", notifTitle);
            intent.putExtra("msgBody", notification.getBody());
        }


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.launcher_b)
                .setContentTitle(notifTitle)
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
