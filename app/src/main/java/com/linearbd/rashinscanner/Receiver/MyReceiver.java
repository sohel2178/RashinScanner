package com.linearbd.rashinscanner.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by Genius 03 on 8/24/2017.
 */

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();

        Bundle bundle = intent.getExtras();

        if(bundle!= null){
            Log.d("JJJJ","Bundle not Null");

            Barcode barcode = (Barcode) bundle.getSerializable("data");

        }
    }
}
