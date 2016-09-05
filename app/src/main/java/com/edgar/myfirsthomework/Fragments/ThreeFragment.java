package com.edgar.myfirsthomework.Fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
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

import info.androidhive.materialtabs.R;


public class ThreeFragment extends Fragment {
    private static final String LOG = "myLogs";
    private static final int MY_PERMISSIONS_REQUEST_CODE = 1;
    ImageButton imageButton;
    Camera camera = null;
    Camera.Parameters cameParameters;
    boolean isFlash;
    boolean isOn;
    TextView textFlashOn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_three, container, false);
        imageButton = (ImageButton) v.findViewById(R.id.image_buttonOff);
        textFlashOn = (TextView) v.findViewById(R.id.textFlashOn);
        if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            camera = Camera.open();
            cameParameters = camera.getParameters();
            isFlash = true;
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isFlash) {
                    if (!isOn) {
                        imageButton.setImageResource(R.drawable.btn_switch_on);
                        cameParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(cameParameters);
                        camera.startPreview();
                        textFlashOn.setText("FlashLight On");
                        isOn = true;
                    } else {
                        imageButton.setImageResource(R.drawable.btn_switch_off);
                        cameParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(cameParameters);
                        camera.startPreview();
                        textFlashOn.setText("FlashLight Off");
                        isOn = false;
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Error")
                            .setMessage("Flash is not avaible on this device...")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(getActivity(), "Permission has not been granted", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



}

