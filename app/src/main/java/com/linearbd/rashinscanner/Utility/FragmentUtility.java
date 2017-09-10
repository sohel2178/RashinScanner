package com.linearbd.rashinscanner.Utility;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Genius 03 on 8/26/2017.
 */

public class FragmentUtility {

    public static void gotoFragment(FragmentManager manager, Fragment fragment,int resId){
        manager.beginTransaction().replace(resId,fragment).commit();
    }
}
