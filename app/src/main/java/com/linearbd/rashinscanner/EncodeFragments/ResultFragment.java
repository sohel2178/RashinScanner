package com.linearbd.rashinscanner.EncodeFragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.linearbd.rashinscanner.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    private Bitmap mBitmap;
    private ImageView ivBarcode;


    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBitmap = getArguments().getParcelable("data");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ivBarcode = view.findViewById(R.id.iv_barcode);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(mBitmap!= null){
            ivBarcode.setImageBitmap(mBitmap);
            Log.d("KKk","JJJ");
        }
    }
}
