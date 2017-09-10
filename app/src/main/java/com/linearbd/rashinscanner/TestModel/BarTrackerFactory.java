package com.linearbd.rashinscanner.TestModel;

import android.util.Log;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import com.linearbd.rashinscanner.Listener.BarcodeListener;

/**
 * Created by Genius 03 on 8/24/2017.
 */

public class BarTrackerFactory implements MultiProcessor.Factory<Barcode> {

    private BarcodeListener listener;
    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        Log.d("YYYYYY","YYYYYY");
        if(listener!= null){
            listener.detect(barcode);
        }
        return new Tracker<Barcode>();
    }

    public void  setListener(BarcodeListener listener){
        this.listener = listener;
    }


}
