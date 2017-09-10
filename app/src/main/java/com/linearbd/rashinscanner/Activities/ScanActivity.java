package com.linearbd.rashinscanner.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.linearbd.rashinscanner.R;

import net.glxn.qrgen.android.QRCode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScanActivity extends BaseDetailActivity {

    private Barcode mBarcode;
    private ImageView ivBarcode;

    private TextView tvFormat,tvType,tvTime,tvText;

    private LinearLayout llTextContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        setupWindowAnimations();
        setupToolbar();

        mBarcode = getIntent().getParcelableExtra("barcode");

        if(mBarcode!=null){
            Log.d("TTT","Pass");
            initView();
            updateData(mBarcode);
        }else{
            Log.d("TTT","Failed");

        }

    }

    private void updateData(Barcode mBarcode) {

        Bitmap bitmap = null;

        String format = getFormat(mBarcode);
        String type = getType(mBarcode);

        String time = getTime();


        switch (mBarcode.valueFormat){
            case Barcode.CONTACT_INFO:
                Toast.makeText(this, "Contact Info", Toast.LENGTH_SHORT).show();
                break;

            case Barcode.EMAIL:
                bitmap = QRCode.from(mBarcode.displayValue).bitmap();

                LinearLayout emailContainer = (LinearLayout) findViewById(R.id.email_container);

                TextView tvEmail = (TextView) findViewById(R.id.tv_email);
                TextView tvSubject = (TextView) findViewById(R.id.tv_subject);
                TextView tvMessage = (TextView) findViewById(R.id.tv_message);

                Barcode.Email barCodeEmail = mBarcode.email;

                tvEmail.setText(barCodeEmail.address);
                tvSubject.setText(barCodeEmail.subject);
                tvMessage.setText(barCodeEmail.body);

                emailContainer.setVisibility(View.VISIBLE);

                break;

            case Barcode.ISBN:
                //Toast.makeText(this, "ISBN", Toast.LENGTH_SHORT).show();
                String value = mBarcode.displayValue;
                bitmap = QRCode.from(value).bitmap();

                LinearLayout llIsbn = (LinearLayout) findViewById(R.id.isbn_container);
                TextView tvIsbn = (TextView) findViewById(R.id.tv_isbn);
                tvIsbn.setText(value);
                llIsbn.setVisibility(View.VISIBLE);


                break;

            case Barcode.PHONE:
                break;

            case Barcode.PRODUCT:
                break;

            case Barcode.SMS:
                break;

            case Barcode.TEXT:
                llTextContainer = (LinearLayout) findViewById(R.id.text_container);
                tvText = (TextView) findViewById(R.id.tv_text);
                tvText.setText(mBarcode.displayValue);
                llTextContainer.setVisibility(View.VISIBLE);
                bitmap = QRCode.from(mBarcode.displayValue).bitmap();

                if(bitmap!=null){
                    ivBarcode.setImageBitmap(bitmap);
                }
                break;

            case Barcode.URL:
                break;

            case Barcode.WIFI:
                break;

            case Barcode.GEO:
                break;

            case Barcode.CALENDAR_EVENT:
                break;

            case Barcode.DRIVER_LICENSE:
                break;


        }

        // Set All Values
        tvFormat.setText(format);
        tvType.setText(type);
        tvTime.setText(time);

        if(bitmap!=null){
            ivBarcode.setImageBitmap(bitmap);
        }
    }

    private String getTime() {
        String time="";

        Date date = new Date();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        time = df.format(date);

        return time;
    }

    private String getType(Barcode mBarcode) {
        String retStr ="";

        switch (mBarcode.valueFormat){
            case Barcode.CONTACT_INFO:
                retStr ="CONTACT_INFO";
                break;

            case Barcode.EMAIL:
                retStr ="EMAIL";
                break;

            case Barcode.ISBN:
                retStr ="ISBN";
                break;

            case Barcode.PHONE:
                retStr ="PHONE";
                break;

            case Barcode.PRODUCT:
                retStr ="PRODUCT";
                break;

            case Barcode.SMS:
                retStr ="SMS";
                break;

            case Barcode.TEXT:
                retStr ="TEXT";
                break;

            case Barcode.URL:
                retStr ="URL";
                break;

            case Barcode.WIFI:
                retStr ="WIFI";
                break;

            case Barcode.GEO:
                retStr ="GEO";
                break;

            case Barcode.CALENDAR_EVENT:
                retStr ="CALENDAR_EVENT";
                break;

            case Barcode.DRIVER_LICENSE:
                retStr ="DRIVER_LICENSE";
                break;


        }

        return retStr;


    }

    private String getFormat(Barcode mBarcode) {
        String retStr ="";

        switch (mBarcode.format){
            case Barcode.ALL_FORMATS:
                retStr = "ALL_FORMATS";
                break;

            case Barcode.CODE_128:
                retStr = "CODE_128";
                break;

            case Barcode.CODE_39:
                retStr = "CODE_39";
                break;

            case Barcode.CODE_93:
                retStr = "CODE_93";
                break;

            case Barcode.CODABAR:
                retStr = "CODABAR";
                break;

            case Barcode.DATA_MATRIX:
                retStr = "DATA_MATRIX";
                break;

            case Barcode.EAN_13:
                retStr = "EAN_13";

                break;

            case Barcode.EAN_8:
                retStr = "EAN_8";
                break;

            case Barcode.ITF:
                retStr = "ITF";
                break;

            case Barcode.QR_CODE:
                retStr = "QR_CODE";
                break;

            case Barcode.UPC_A:
                retStr = "UPC_A";
                break;

            case Barcode.UPC_E:
                retStr = "UPC_E";
                break;

            case Barcode.PDF417:
                retStr = "PDF417";
                break;

            case Barcode.AZTEC:
                retStr = "AZTEC";
                break;
        }

        return retStr;
    }

    private void initView() {
        ivBarcode = (ImageView) findViewById(R.id.barcode_image);

        tvFormat = (TextView) findViewById(R.id.tv_format);
        tvType = (TextView) findViewById(R.id.tv_type);
        tvTime = (TextView) findViewById(R.id.tv_time);



    }

    private void setupWindowAnimations() {
        Visibility enterTransition = buildEnterTransition();
        getWindow().setEnterTransition(enterTransition);
    }

    private Visibility buildEnterTransition() {
        Fade enterTransition = new Fade();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        enterTransition.excludeTarget(R.id.et_name,true); // You can replace any integer for |R.id.square_red|

        return enterTransition;

    }

}
