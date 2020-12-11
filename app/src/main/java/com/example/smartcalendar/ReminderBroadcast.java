package com.example.smartcalendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // receiving data from editactivity once the user clicks "done" button.
        String receivedtitle = intent.getStringExtra("Title");
        String receivedstarttime = intent.getStringExtra("Start Time");
        String receivedendtime = intent.getStringExtra("End Time");
        String receivedlocation = intent.getStringExtra("Location");
        String receiveddescription = intent.getStringExtra("Description");
        String concatsubject = receivedstarttime + " - " + receivedendtime;
        String concatdescription = "Location: " + receivedlocation + '\n' + receiveddescription;

        // use builder to construct details of our notification, also define a channel. need channel for api > 26
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyevent")
                .setSmallIcon(R.drawable.ic_baseline_add_alert_24)
                .setContentTitle(receivedtitle)
                .setContentText(concatsubject)
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(concatsubject + "\n" + concatdescription))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        if (Build.VERSION.SDK_INT >= 21) {
            builder.setVibrate(new long[0]);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());

    }

}
