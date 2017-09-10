package com.linearbd.rashinscanner.BarcodeModel.Helper;

import com.linearbd.rashinscanner.BarcodeModel.UI.GraphicOverlay;

/**
 * Created by Genius 03 on 8/23/2017.
 */

public abstract class TrackedGraphic<T> extends GraphicOverlay.Graphic {

    private int mId;

    public TrackedGraphic(GraphicOverlay overlay) {
        super(overlay);
    }

    void setId(int id) {
        mId = id;
    }

    protected int getId() {
        return mId;
    }

    abstract void updateItem(T item);
}
