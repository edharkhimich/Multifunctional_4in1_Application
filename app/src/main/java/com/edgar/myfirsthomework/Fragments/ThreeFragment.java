package com.edgar.myfirsthomework.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import info.androidhive.materialtabs.R;


public class ThreeFragment extends Fragment {
    ImageButton imageButton;
    Camera camera;
    Context context;
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
        context = getContext();

        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            camera = Camera.open();
            cameParameters = camera.getParameters();
            isFlash = true;
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isFlash){
                    if(!isOn){
                        imageButton.setImageResource(R.drawable.btn_switch_on);
                        cameParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(cameParameters);
                        camera.startPreview();
                        textFlashOn.setText("FlashLight On");
                        isOn = true;
                    }
                    else{
                        imageButton.setImageResource(R.drawable.btn_switch_off);
                        cameParameters. setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(cameParameters);
                        camera.startPreview();
                        textFlashOn.setText("FlashLight Off");
                        isOn = false;
                    }
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        if(camera!=null){
            camera.release();
            camera = null;
        }
    }
}
