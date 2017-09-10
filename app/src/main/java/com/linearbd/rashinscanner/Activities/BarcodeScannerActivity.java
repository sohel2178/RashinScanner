package com.linearbd.rashinscanner.Activities;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.linearbd.rashinscanner.BarcodeModel.Helper.BarcodeTrackerFactory;
import com.linearbd.rashinscanner.BarcodeModel.UI.CameraSourcePreview;
import com.linearbd.rashinscanner.BarcodeModel.UI.GraphicOverlay;
import com.linearbd.rashinscanner.Listener.BarcodeListener;
import com.linearbd.rashinscanner.R;

import java.io.IOException;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class BarcodeScannerActivity extends AppCompatActivity implements BarcodeListener {
    private static final int CAMERA_PERMISSION=4000;
    private static final int RC_HANDLE_GMS=5000;

    private GraphicOverlay mGraphicOverLay;
    private CameraSourcePreview mCameraSourcePreview;
    private CameraSource mCameraSource = null;

    private boolean hasPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        cameraPermission();
    }


    @AfterPermissionGranted(CAMERA_PERMISSION)
    private void cameraPermission() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            hasPermission = true;

            initView();
            
            createCameraSource();






        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "App need to Permission for Camera",
                    CAMERA_PERMISSION, perms);
        }
    }

    private void initView() {
        mCameraSourcePreview = (CameraSourcePreview) findViewById(R.id.preview);
        mGraphicOverLay = (GraphicOverlay) findViewById(R.id.graphic_overlay);
    }

    private void createCameraSource() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getApplicationContext()).build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverLay);
        barcodeFactory.setBarcodeListener(this);

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
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
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
        mCameraSourcePreview.stop();
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
                mCameraSourcePreview.start(mCameraSource, mGraphicOverLay);
            } catch (IOException e) {
                Log.d("TEST","CAmera Source Not Start");
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    @Override
    public void detect(Barcode barcode) {
        Log.d("SOHELCODE","Found");
        /*Log.d("SOHELCODE",barcode.displayValue);
        Log.d("SOHELCODE",barcode.rawValue);
        Log.d("SOHELCODE",barcode.contactInfo.name.first);
        Log.d("SOHELCODE",barcode.contactInfo.name.last);
       // Log.d("SOHELCODE",barcode.displayValue);

        Bundle bundle = new Bundle();
        bundle.putSerializable("kkk", (Serializable) barcode);

        Intent intent = new Intent();
        intent.putExtras(bundle);

        setResult(RESULT_OK);*/

       /* Bundle bundle = new Bundle();
        bundle.putSerializable("kkk", (Serializable) barcode);

        Intent intent = new Intent();
        intent.putExtras(bundle);

        setResult(RESULT_OK);*/

       BarcodeScannerActivity.this.finish();
    }
}
