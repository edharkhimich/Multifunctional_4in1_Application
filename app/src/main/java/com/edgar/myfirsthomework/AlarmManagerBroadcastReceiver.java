package com.edgar.myfirsthomework;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.edgar.myfirsthomework.Fragments.TwoFragment;
import com.edgar.myfirsthomework.Services.RingtonePlayService;


public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {


    final String LOG_TAG = "myLogs";

    @Override
    public void onReceive(Context ctx, Intent intent) {
        Log.d(LOG_TAG, "Receiver start work");

        String get_your_string = intent.getStringExtra(TwoFragment.EXTRA);

        Log.d(LOG_TAG, "Your key = " + get_your_string);

        Intent service_intent = new Intent(ctx, RingtonePlayService.class);

        service_intent.putExtra(TwoFragment.EXTRA, get_your_string);

        ctx.startService(service_intent);

    }

}
