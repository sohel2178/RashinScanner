package com.linearbd.rashinscanner.BarcodeModel.Helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.android.gms.vision.barcode.Barcode;
import com.linearbd.rashinscanner.BarcodeModel.UI.GraphicOverlay;
import com.linearbd.rashinscanner.Listener.BarcodeListener;


/**
 * Created by Genius 03 on 8/23/2017.
 */

public class BarcodeGraphic extends TrackedGraphic<Barcode> {

    private BarcodeListener listener;

    private static final int COLOR_CHOICES[] = {
            Color.BLUE,
            Color.CYAN,
            Color.GREEN
    };
    private static int mCurrentColorIndex = 0;

    private Paint mRectPaint;
    private Paint mTextPaint;
    private volatile Barcode mBarcode;

    public BarcodeGraphic(GraphicOverlay overlay) {
        super(overlay);

        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];

        mRectPaint = new Paint();
        mRectPaint.setColor(selectedColor);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(4.0f);

        mTextPaint = new Paint();
        mTextPaint.setColor(selectedColor);
        mTextPaint.setTextSize(36.0f);
    }

    @Override
    void updateItem(Barcode item) {
        mBarcode = item;

        if(listener != null){
            listener.detect(item);
        }
        postInvalidate();

    }

    public void setBarcodeListener(BarcodeListener listener){
        this.listener = listener;
    }

    // This method is for Graphic Class
    @Override
    public void draw(Canvas canvas) {

        Barcode barcode = mBarcode;
        if (barcode == null) {
            return;
        }

        // Draws the bounding box around the barcode.
        RectF rect = new RectF(barcode.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        canvas.drawRect(rect, mRectPaint);

        // Draws a label at the bottom of the barcode indicate the barcode value that was detected.
        canvas.drawText(barcode.rawValue, rect.left, rect.bottom, mTextPaint);

    }
}
