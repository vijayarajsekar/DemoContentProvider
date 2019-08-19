package com.demo.cp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class DemoApp extends Application {

    public static final String CHANNEL_ID = "DemoServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mNotificationChannel = new NotificationChannel(CHANNEL_ID, "DemoServiceChannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager mNotificationManager = getSystemService(NotificationManager.class);
            mNotificationManager.createNotificationChannel(mNotificationChannel);
        }

    }
}
