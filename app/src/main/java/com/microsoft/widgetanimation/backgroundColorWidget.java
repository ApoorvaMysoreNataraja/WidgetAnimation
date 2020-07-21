package com.microsoft.widgetanimation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */
public class backgroundColorWidget extends AppWidgetProvider {

    public static final String ACTION_AUTO_UPDATE = "AUTO_UPDATE";

    private static int counter;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        counter += 1;
        Resources resources = context.getResources();
        RemoteViews views;

        // Construct the RemoteViews object
        if (counter %4 == 0) {
            views = new RemoteViews(context.getPackageName(), R.layout.background_color_widget2);
        }
        else if (counter % 3 == 0) {
            views = new RemoteViews(context.getPackageName(), R.layout.background_color_widget3);
        }
        else if (counter % 2 == 0) {
            views = new RemoteViews(context.getPackageName(), R.layout.background_color_widget4);
        }
        else{
            views = new RemoteViews(context.getPackageName(), R.layout.background_color_widget);
        }

        Log.i("Apoorva", "calling update, count: " + counter);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
        appWidgetAlarm.startAlarm();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // start alarm
        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
        appWidgetAlarm.startAlarm();

        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);

        if(intent.getAction().equals(ACTION_AUTO_UPDATE))
        {
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(context);
            ComponentName thisAppWidget = new ComponentName(context
                    .getPackageName(), backgroundColorWidget.class.getName());
            int[] appWidgetIds = appWidgetManager
                    .getAppWidgetIds(thisAppWidget);
            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
        }
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        // stop alarm only if all widgets have been disabled
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidgetComponentName = new ComponentName(context.getPackageName(),getClass().getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName);
        if (appWidgetIds.length == 0) {
            // stop alarm
            AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
            appWidgetAlarm.stopAlarm();
        }
    }
}

