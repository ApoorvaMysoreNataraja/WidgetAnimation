package com.microsoft.widgetanimation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class AppWidgetAlarm {
    private final int ALARM_ID = 0;
    private final int INTERVAL_MILLIS = 50;

    private Context mContext;


    public AppWidgetAlarm(Context context)
    {
        mContext = context;
    }
    public void stopAlarm() {
        Intent alarmIntent = new Intent(backgroundColorWidget.ACTION_AUTO_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    public void startAlarm() {

        Intent alarmIntent=new Intent(mContext, backgroundColorWidget.class);
        alarmIntent.setAction(backgroundColorWidget.ACTION_AUTO_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC, INTERVAL_MILLIS, pendingIntent);
        }
    }
}
