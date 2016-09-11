package com.edgar.myfirsthomework.Fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import info.androidhive.materialtabs.R;


public class ThreeFragment extends Fragment {
    private Camera camera = null;
    private Camera.Parameters cameraParameters;
    private TextView textFlashOn;
    public String flash_on = "Flashlight ON";
    public String flash_off = "Flashlight OFF";

    private String previousFlashMode = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_three, container, false);
        textFlashOn = (TextView) v.findViewById(R.id.textFlashOn);

        open();

        ToggleButton the_button = (ToggleButton) v.findViewById(R.id.flashlightButton);


        the_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((ToggleButton) view).isChecked()) {
                    on();
                    view.setKeepScreenOn(true);
                    textFlashOn.setText(flash_on);
                } else {
                    off();
                    view.setKeepScreenOn(false);
                    textFlashOn.setText(flash_off);
                }
            }
        });
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        close();
    }


    public synchronized void open() {
        camera = Camera.open();
        if (camera != null) {
            cameraParameters = camera.getParameters();
            previousFlashMode = cameraParameters.getFlashMode();
        }
        if (previousFlashMode == null) {
            // could be null if no flash, i.e. emulator
            previousFlashMode = Camera.Parameters.FLASH_MODE_OFF;
        }
    }

    public synchronized void close() {
        if (camera != null) {
            cameraParameters.setFlashMode(previousFlashMode);
            camera.setParameters(cameraParameters);
            camera.release();
            camera = null;
        }
    }

    public synchronized void on() {
        if (camera != null) {
            cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(cameraParameters);
            camera.startPreview();
        }
    }

    public synchronized void off() {
        if (camera != null) {
            camera.stopPreview();
            cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(cameraParameters);
        }
    }

}

