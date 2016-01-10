package com.zotlay.testapp3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by Akhil Rajput on 1/4/2016.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {


    private  AlarmManager alarmMgr;
    private  PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service = new Intent(context, GPSService.class);

          Log.d("stand","myalarm");


        startWakefulService(context, service);   //call for GPSService service.

    }


    public  void setAlarm(Context context) {
        try {
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("tywele.remindme.ACTION_EVENT_TIME");
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() , 10000, alarmIntent);  //this sets the time interval for next call of alarm.
            Log.d("chakki", "alarmReceiver2");
        ;}catch (Exception e) {}

    }



    }
