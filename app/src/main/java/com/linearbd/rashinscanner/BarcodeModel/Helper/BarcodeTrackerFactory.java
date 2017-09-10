package com.linearbd.rashinscanner.BarcodeModel.Helper;

import android.util.Log;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import com.linearbd.rashinscanner.BarcodeModel.UI.GraphicOverlay;
import com.linearbd.rashinscanner.Listener.BarcodeListener;

/**
 * Created by Genius 03 on 8/23/2017.
 */

public class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {

    private GraphicOverlay graphicOverlay;
    private BarcodeListener listener;

    public BarcodeTrackerFactory(GraphicOverlay graphicOverlay) {
        this.graphicOverlay = graphicOverlay;
    }

    public void setBarcodeListener(BarcodeListener listener){
        this.listener = listener;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        BarcodeGraphic barcodeGraphic = new BarcodeGraphic(graphicOverlay);

        Log.d("NNNN",barcode.displayValue);

        if(listener!= null){
            listener.detect(barcode);
        }
        return new GraphicTracker<>(graphicOverlay,barcodeGraphic);
    }
}
