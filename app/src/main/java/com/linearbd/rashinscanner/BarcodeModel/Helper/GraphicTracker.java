package com.linearbd.rashinscanner.BarcodeModel.Helper;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.linearbd.rashinscanner.BarcodeModel.UI.GraphicOverlay;

/**
 * Created by Genius 03 on 8/23/2017.
 */

public class GraphicTracker<T> extends Tracker<T> {
    private GraphicOverlay mOverlay;
    private TrackedGraphic<T> mGraphic;

    GraphicTracker(GraphicOverlay overlay, TrackedGraphic<T> graphic) {
        mOverlay = overlay;
        mGraphic = graphic;
    }


    @Override
    public void onNewItem(int id, T t) {
        mGraphic.setId(id);
    }

    @Override
    public void onUpdate(Detector.Detections<T> detections, T t) {
        mOverlay.add(mGraphic);
        mGraphic.updateItem(t);


    }

    @Override
    public void onMissing(Detector.Detections<T> detections) {
        mOverlay.remove(mGraphic);
    }

    @Override
    public void onDone() {
        mOverlay.remove(mGraphic);
    }
}
