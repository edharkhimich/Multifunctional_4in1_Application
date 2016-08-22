package com.edgar.myfirsthomework.Fragments;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import info.androidhive.materialtabs.R;

import com.edgar.myfirsthomework.AlarmManagerBroadcastReceiver;

import java.util.Calendar;


public class TwoFragment extends Fragment {

    final String LOG_TAG = "myLogs";
    private int mNumberPickerInputId = 0;
    public static final String EXTRA = "extra";

    AlarmManager alarmManager;
    TimePicker timePicker;
    Context context;
    Button turnOn, turnOff;
    Calendar calendar;
    PendingIntent pendingIntent;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_two, container, false);
        context = getActivity();
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        calendar = Calendar.getInstance();
        timePicker = (TimePicker) v.findViewById(R.id.timePicker);
        turnOn = (Button) v.findViewById(R.id.start_alarm);
        turnOff = (Button) v.findViewById(R.id.end_alarm);

        final Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);

        turnOn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                String str_min = "";
                String str_hour = "";

                int hour = 0;
                int min = 0;

                int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentApiVersion > android.os.Build.VERSION_CODES.LOLLIPOP_MR1){
                    hour = timePicker.getHour();
                    min = timePicker.getMinute();
                } else {
                    hour = timePicker.getCurrentHour();
                    min = timePicker.getCurrentMinute();
                }

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, min);

                if (min < 10) str_min = "0" + String.valueOf(min);
                else str_min = String.valueOf(min);

                if (hour < 10) str_hour = "0" + String.valueOf(hour);
                else str_hour = String.valueOf(hour);

                intent.putExtra(EXTRA, "Alarm On");

                pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                Toast.makeText(context, "Alarm set to: " + str_hour + ":" + str_min + " ", Toast.LENGTH_SHORT).show();
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

        turnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmManager.cancel(pendingIntent);
                Toast.makeText(context, "Alarm Off", Toast.LENGTH_SHORT).show();
                intent.putExtra(EXTRA, "Alarm Off");
                context.sendBroadcast(intent);
            }
        });
        return v;
    }
}
