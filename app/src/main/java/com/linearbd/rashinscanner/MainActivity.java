package com.linearbd.rashinscanner;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;

import com.linearbd.rashinscanner.Adapter.MyPagerAdapter;
import com.linearbd.rashinscanner.Fragments.EncodeFragment;
import com.linearbd.rashinscanner.Fragments.HistoryFragment;
import com.linearbd.rashinscanner.Fragments.ScanFragment;

import net.glxn.qrgen.android.QRCode;

import java.io.File;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private static final int READ_WRITE_PERMISSION=3000;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set Window Animation
        setupWindowAnimations();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setUpIcon();


        externalStoragePermission();
    }

    private void setupWindowAnimations() {
        // Re-enter transition is executed when returning to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }

    private void setUpIcon() {
        tabLayout.getTabAt(0).setIcon(R.drawable.camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.encode);
        tabLayout.getTabAt(2).setIcon(R.drawable.history);
    }

    private void setupViewPager(ViewPager viewPager){
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ScanFragment(), getString(R.string.scan));
        adapter.addFragment(new EncodeFragment(), getString(R.string.decode));
        adapter.addFragment(new HistoryFragment(), getString(R.string.save));
        viewPager.setAdapter(adapter);
    }


    @AfterPermissionGranted(READ_WRITE_PERMISSION)
    private void externalStoragePermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing

            File file = QRCode.from("HELLO SOHEL").file();

            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            //imageView.setImageBitmap(bitmap);




        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "App need to Permission for Location",
                    READ_WRITE_PERMISSION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }
}
