package com.linearbd.rashinscanner.EncodeFragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.linearbd.rashinscanner.R;
import com.linearbd.rashinscanner.Utility.FragmentUtility;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

/**
 * A simple {@link Fragment} subclass.
 */
public class VCardFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout rlFirst,rlSecond;
    private ImageView ivBarcode,ivIcon;

    private EditText etName,etTitle,etEmail,etAddress,
            etCompanyName,etPhoneNumber,etWebsite;

    private Button btnGenerate;

    private Bitmap mBitmap;


    public VCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vcard, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        rlFirst = view.findViewById(R.id.rl_first);
        rlSecond = view.findViewById(R.id.rl_second);

        ivBarcode = view.findViewById(R.id.iv_barcode);
        ivIcon = view.findViewById(R.id.iv_icon);

        etName =view.findViewById(R.id.et_name);
        etTitle = view.findViewById(R.id.et_title);
        etEmail = view.findViewById(R.id.et_email);
        etAddress = view.findViewById(R.id.et_address);
        etCompanyName =  view.findViewById(R.id.et_company_name);
        etPhoneNumber =  view.findViewById(R.id.et_phone_number);
        etWebsite = view.findViewById(R.id.et_company_website);


        btnGenerate = view.findViewById(R.id.btn_generate);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnGenerate.setOnClickListener(this);

        ivIcon.setColorFilter(ContextCompat.getColor(getContext(),R.color.icon_color));
    }

    @Override
    public void onClick(View view) {
        String name = etName.getText().toString().trim();
        String title = etTitle.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String companyName = etCompanyName.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String website = etWebsite.getText().toString().trim();

        VCard vCard = new VCard(name)
                .setTitle(title)
                .setEmail(email)
                .setAddress(address)
                .setCompany(companyName)
                .setPhoneNumber(phoneNumber)
                .setWebsite(website);


        mBitmap = QRCode.from(vCard).bitmap();

        if(mBitmap!= null){
            changeLayout();

        }


    }

    private void changeLayout() {
        ivBarcode.setImageBitmap(mBitmap);
        hide(rlFirst);
        reveal(rlSecond);
    }

    private void gotoResultFragment(Bitmap bitmap) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("data",bitmap);

        ResultFragment resultFragment = new ResultFragment();
        resultFragment.setArguments(bundle);



        FragmentUtility.gotoFragment(getFragmentManager(),resultFragment,R.id.container);
    }


    private void reveal(View view){
        // get the center for the clipping circle
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;
        float finalRadius = (float) Math.hypot(cx, cy);
        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        anim.setDuration(1000);
        view.setVisibility(View.VISIBLE);
        anim.start();

    }

    private void hide(final View view){

        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        float initialRadius = (float) Math.hypot(cx, cy);

        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
        anim.setDuration(1000);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });

        anim.start();

    }
}
