package com.linearbd.rashinscanner.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiDetector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.linearbd.rashinscanner.Listener.BarcodeListener;
import com.linearbd.rashinscanner.R;
import com.linearbd.rashinscanner.TestModel.BarTrackerFactory;
import com.linearbd.rashinscanner.TestModel.CameraView;

import java.io.IOException;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class TestActivity extends AppCompatActivity implements BarcodeListener {
    private static final int CAMERA_PERMISSION=4000;
    private static final int RC_HANDLE_GMS=5000;

    private CameraSource mCameraSource = null;
    private boolean hasPermission;
    private boolean mBarCodeFound;

    private Barcode barcode;

    private CameraView cameraView;

    BarcodeDetector barcodeDetector;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("LLLL","JJJJ");

            returnIntent();

            //TestActivity.this.finish();

        }
    };

    private void returnIntent() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("data",barcode);
        returnIntent.putExtra("result","uuuuu");
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        cameraPermission();


    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        intentFilter.addAction("sohel.ahmed.rashin.receiver");

        registerReceiver(broadcastReceiver,intentFilter);

    }

    @Override
    protected void onStop() {
        unregisterReceiver(broadcastReceiver);
        super.onStop();
    }

    @AfterPermissionGranted(CAMERA_PERMISSION)
    private void cameraPermission() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            hasPermission = true;

            cameraView = (CameraView) findViewById(R.id.camera_view);


            createCameraSource();






        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "App need to Permission for Camera",
                    CAMERA_PERMISSION, perms);
        }
    }

    private void createCameraSource() {
        barcodeDetector = new BarcodeDetector.Builder(getApplicationContext()).build();
        BarTrackerFactory barcodeFactory = new BarTrackerFactory();
        barcodeFactory.setListener(this);


        barcodeDetector.setProcessor(new MultiProcessor.Builder<>(barcodeFactory).build());

        MultiDetector multiDetector = new MultiDetector.Builder()
                .add(barcodeDetector)
                .build();

        if (!multiDetector.isOperational()) {
            Toast.makeText(getApplicationContext(), "Detector Dependency not Available", Toast.LENGTH_SHORT).show();
        }

        mCameraSource = new CameraSource.Builder(getApplicationContext(), multiDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f)
                .build();
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d("HHHH","onResume");

        // Start the Camera Source
        if(hasPermission){
            startCameraSource();

        }
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        if (mCameraSource != null) {
            mCameraSource.release();
        }
        super.onDestroy();
    }


    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                Log.d("TEST","CAmera Source Start");
                cameraView.start(mCameraSource);
            } catch (IOException e) {
                Log.d("TEST","CAmera Source Not Start");
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    @Override
    public void detect(Barcode barcode) {
        Log.d("GGG","Found");
        mBarCodeFound = true;
        this.barcode = barcode;


        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setAction("sohel.ahmed.rashin.receiver");


        sendBroadcast(intent);

       /* while (mBarCodeFound){
            cameraView.stop();
            mCameraSource.release();
            TestActivity.this.finish();
        }*/



    }

}
