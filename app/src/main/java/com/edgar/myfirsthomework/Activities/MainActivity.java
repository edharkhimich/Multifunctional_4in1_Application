package com.edgar.myfirsthomework.Activities;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.edgar.myfirsthomework.Adapters.ViewPagerAdapter;
import com.edgar.myfirsthomework.Fragments.FourFragment;
import com.edgar.myfirsthomework.Fragments.OneFragment;
import com.edgar.myfirsthomework.Fragments.TwoFragment;
import com.edgar.myfirsthomework.Fragments.ThreeFragment;
import com.edgar.myfirsthomework.Services.RingtonePlayService;

import info.androidhive.materialtabs.R;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final int PERMS_REQUEST_CODE = 123;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static final String LOG = "myLogs";
    private static final String REQUEST_LOG = "log";


    private int[] tabIcons = {
            R.drawable.ic_time,
            R.drawable.ic_alarm2,
            R.drawable.ic_light,
            R.drawable.ic_contacts
    };

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(REQUEST_LOG, "onCreate");
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        checkPermission();
        String letterFromService = getIntent().getStringExtra(RingtonePlayService.KEY);


        if (letterFromService!=null) {
            Log.d(LOG, "letterFromService != null = " + letterFromService);
            viewPager.setCurrentItem(1);
        }
    }

    private void setupTabIcons() {
        Log.d(REQUEST_LOG, "setupTabIcons");
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        Log.d(REQUEST_LOG, "setupViewPager");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "TIME");
        adapter.addFragment(new TwoFragment(), "ALARM");
        adapter.addFragment(new ThreeFragment(), "FLASH");
        adapter.addFragment(new FourFragment(), "CONTACTS");
        viewPager.setAdapter(adapter);
    }

    private void checkPermission(){
        Log.d(REQUEST_LOG, "checkPermission");

        if (hasPermissions()){
            // our app has permissions.
            Log.d(REQUEST_LOG, "hasPermissions()");
            setupTabIcons();
        }
        else {
            //our app doesn't have permissions, So i m requesting permissions.
            Log.d(REQUEST_LOG, "don't hasPermissions()");
            requestPerms();
        }
    }




    private boolean hasPermissions(){
        int res = 0;
        //string array of permissions,
        String[] permissions = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};

        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(REQUEST_LOG, "onRequestPermissionsResult()");
        boolean allowed = true;

        switch (requestCode) {
            case PERMS_REQUEST_CODE:

                for (int res : grantResults) {
                    // if user granted all permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }

        if (allowed) {
            //user granted all permissions we can perform our task.
//            setupTabIcons();
        } else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "Camera Permissions denied.", Toast.LENGTH_SHORT).show();
                }
                else if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this, "Write External Storage Permission denied", Toast.LENGTH_SHORT).show();
                }
                else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Toast.makeText(this, "Read External Storage Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
                    setupTabIcons();
    }


    private void requestPerms(){
        Log.d(REQUEST_LOG, "requestPerms()");
        String[] permissions = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMS_REQUEST_CODE);
        }
    }
}






