package com.linearbd.rashinscanner.Listener;

import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by Genius 03 on 8/23/2017.
 */

public interface BarcodeListener {

    public void detect(Barcode barcode);
}
