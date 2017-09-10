package com.linearbd.rashinscanner.TestModel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;

import java.io.IOException;

/**
 * Created by Genius 03 on 8/24/2017.
 */

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private Context context;
    private CameraSource mCameraSource;
    private boolean mStartRequested;
    private boolean mSurfaceAvailable;

    public CameraView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        this.context= context;
        mStartRequested = false;
        mSurfaceAvailable = false;
        this.getHolder().addCallback(this);

    }

    public void start(CameraSource cameraSource) throws IOException {
        if (cameraSource == null) {
            stop();
        }

        mCameraSource = cameraSource;

        if (mCameraSource != null) {
            mStartRequested = true;
            startIfReady();
        }
    }

    public void release() {
        if (mCameraSource != null) {
            mCameraSource.release();
            mCameraSource = null;
        }
    }

    public void stop() {
        if (mCameraSource != null) {
            mCameraSource.stop();
        }
    }

    private void startIfReady() throws IOException {
        if (mStartRequested && mSurfaceAvailable) {
            //noinspection MissingPermission
            mCameraSource.start(getHolder());

            mStartRequested = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceAvailable = true;
        try {
            startIfReady();
        } catch (IOException e) {
           // Log.e(TAG, "Could not start camera source.", e);
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSurfaceAvailable = false;

    }
}
