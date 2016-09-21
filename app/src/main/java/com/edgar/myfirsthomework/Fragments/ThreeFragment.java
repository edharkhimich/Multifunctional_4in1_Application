package com.edgar.myfirsthomework.Fragments;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.edgar.myfirsthomework.Flash;

import java.io.IOException;

import info.androidhive.materialtabs.R;


public class ThreeFragment extends Fragment {
    private Flash flash = new Flash();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        flash.open();

        ToggleButton the_button = (ToggleButton) view.findViewById(R.id.flashlightButton);

        the_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ToggleButton) v).isChecked()) {
                    flash.on();
                    v.setKeepScreenOn(true);
                } else {
                    flash.off();
                    v.setKeepScreenOn(false);
                }
            }
        });

        if (the_button.isChecked()) {
            flash.on();
            the_button.setKeepScreenOn(true);
        }
        return view;
    }

        @Override
        public void onPause() {
            super.onPause();
            flash.close();
        }
}



