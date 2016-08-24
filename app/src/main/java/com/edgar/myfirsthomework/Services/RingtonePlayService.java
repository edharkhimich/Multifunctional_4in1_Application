package com.edgar.myfirsthomework.Services;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.edgar.myfirsthomework.Activities.MainActivity;
import com.edgar.myfirsthomework.Fragments.TwoFragment;

import info.androidhive.materialtabs.R;


public class RingtonePlayService extends Service {

    public static final String KEY = "key";
    Context context;
    private static final String LOG = "myLogs";
    MediaPlayer mediaPlayer;
    boolean isRunning;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG, "We are in onStartCommand");

        String state = intent.getExtras().getString(TwoFragment.EXTRA);
        context = getApplicationContext();

        Log.d(LOG, "Extra String state from receiver = " + state);



        assert state != null;
        switch (state) {
            case "Alarm On":
                startId = 1;
                Log.d("myLogs", "Start id = " + startId);
                break;
            case "Alarm Off":
                startId = 0;
                Log.d("myLogs", "Start id = " + startId);
                break;
            default:
                startId = 0;
                break;
        }

        //If the music are not playing and user press Alarm On
        if (!this.isRunning && startId == 1) {
            Log.d(LOG, "Music start");

            mediaPlayer = MediaPlayer.create(this, R.raw.earlysunrise);
            mediaPlayer.start();


            isRunning = true;
            startId = 0;



            NotificationManager not_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Intent intent_for_two_fragment = new Intent(context, MainActivity.class);
            Log.d(LOG, "BEFORE PUTEXTRA");
            intent_for_two_fragment.putExtra(KEY, "extra").putExtra("bool", true);
            Log.d(LOG, "PUTEXTRA DONE");
            PendingIntent pendingIntentToTwoFragment = PendingIntent.getActivity(context, 0, intent_for_two_fragment, 0);

            Notification notification_popUp = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_nnn)
                    .setContentTitle("An alarm is going off")
                    .setContentText("Click me")
                    .setContentIntent(pendingIntentToTwoFragment)
                    .setSmallIcon(R.drawable.ic_alarm)
                    .setAutoCancel(true)
                    .build();
            not_manager.notify(0, notification_popUp);

        }

        //If the music are playing and user press Alarm Off
        else if (this.isRunning && startId == 0) {
            Log.d(LOG, "Music running, but we will stop it");

            mediaPlayer.stop();
            mediaPlayer.reset();

            isRunning = false;
            startId = 0;
        }

        //If the music are not playing and user press Alarm Off
        else if (!this.isRunning && startId == 0) {
            Log.d(LOG, "For first choose your alarm time");

            isRunning = true;
            startId = 0;

            Toast.makeText(context, "For first choose your alarm time",
                    Toast.LENGTH_LONG).show();
        }
        //If the music are playing and user press Alarm On
        else if (this.isRunning && startId == 1) {
            Log.d(LOG, "Music playing. Don't press \"Alarm on\"");

            isRunning = true;
            startId = 1;

            Toast.makeText(context, "Music playing. Don't press \"Alarm on\"",
                    Toast.LENGTH_LONG).show();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG, "onDestroyed  start ");

        isRunning = false;
    }
}
