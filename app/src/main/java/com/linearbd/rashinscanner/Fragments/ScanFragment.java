package com.linearbd.rashinscanner.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.vision.barcode.Barcode;
import com.linearbd.rashinscanner.Activities.BarcodeScannerActivity;
import com.linearbd.rashinscanner.Activities.ScanActivity;
import com.linearbd.rashinscanner.Activities.TestActivity;
import com.linearbd.rashinscanner.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment implements View.OnClickListener {
    private ImageView ivScan,ivTest;
    private static final int BAR_CODE_REQUEST=100;


    public ScanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        initView(view);
        return  view;
    }

    private void initView(View view) {
        ivScan = view.findViewById(R.id.iv_scan);
        ivTest = view.findViewById(R.id.iv_test);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ivScan.setOnClickListener(this);
        ivTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_scan:
                openBarcodeScannerActivity();
                break;

            case R.id.iv_test:
                openTestActivity();
                break;
        }
    }

    private void openTestActivity() {
        //startActivity(new Intent(getActivity().getApplicationContext(), TestActivity.class));
        startActivityForResult(new Intent(getActivity().getApplicationContext(), TestActivity.class),BAR_CODE_REQUEST);
    }

    private void openBarcodeScannerActivity() {
        startActivity(new Intent(getActivity().getApplicationContext(), BarcodeScannerActivity.class));
        //startActivityForResult(new Intent(getActivity().getApplicationContext(), BarcodeScannerActivity.class),BAR_CODE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("SOHELCODE","onActivityResult");

        if(requestCode==BAR_CODE_REQUEST && resultCode== Activity.RESULT_OK){

            //Bundle bundle = data.getExtras();

            Log.d("SOHELCODE",data.getStringExtra("result"));

            Barcode barcode = (Barcode) data.getExtras().getParcelable("data");

            if(barcode!=null){

                startScanActivity(barcode);
                /*Log.d("SOHELCODE",barcode.displayValue);

                String url = barcode.displayValue;

                if(url.startsWith("http://") || url.startsWith("https://")){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(barcode.displayValue));
                    startActivity(browserIntent);
                }*/


            }


        }
    }


    private void startScanActivity(Barcode barcode) {
        Intent intent = new Intent(getActivity().getApplicationContext(),
                ScanActivity.class);
        intent.putExtra("barcode",barcode);
        startActivity(intent);
    }
}
