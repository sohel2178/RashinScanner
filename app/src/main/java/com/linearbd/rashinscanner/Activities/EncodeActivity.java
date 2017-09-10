package com.linearbd.rashinscanner.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;

import com.linearbd.rashinscanner.EncodeFragments.VCardFragment;
import com.linearbd.rashinscanner.R;


public class EncodeActivity extends AppCompatActivity implements View.OnClickListener {

    private int mPosition;
    // Init View Here...........



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);

        mPosition =getIntent().getIntExtra("position",-1);
        //setupWindowAnimations();

        initLayout();

        //initView();
    }

    private void initView() {

    }

    private void setupWindowAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.slide);
        getWindow().setEnterTransition(slide);
        getWindow().setExitTransition(fade);
    }

    private void initLayout() {
        switch (mPosition){
            case 0:
                getSupportFragmentManager().beginTransaction().add(R.id.container,new VCardFragment()).commit();
                break;
        }
    }

    @Override
    public void onClick(View view) {




    }
}
