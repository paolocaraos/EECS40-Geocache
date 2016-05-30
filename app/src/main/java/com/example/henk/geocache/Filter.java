package com.example.henk.geocache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceView;

/**
 * Created by Henk on 2016/5/29.
 */
public class Filter extends SurfaceView {
    private Paint forText = new Paint();
    private Rect bulbSpace = new Rect();
    private Bitmap bulb = BitmapFactory.decodeResource(getResources(), R.mipmap.charmander);
    public Filter(Context context) {
        super(context);
        forText.setColor(Color.BLUE);
        forText.setTextSize(80);
        setWillNotDraw(false); /*to call the ondraw method */
    }
    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawText("Testing filter", 50, 50, forText);
    }
}
