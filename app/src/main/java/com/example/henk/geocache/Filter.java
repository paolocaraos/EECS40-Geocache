package com.example.henk.geocache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by Henk on 2016/5/29.
 */
public class Filter extends View {
    private Paint forText = new Paint();
    private Rect pokeSpace = new Rect();
    private Bitmap poke;
    public Filter(Context context) {
        super(context);
        setWillNotDraw(false); /*to call the ondraw method */
    }
    @Override
    protected void onDraw(Canvas canvas){
        int x = canvas.getWidth()/2;
        int y = canvas.getHeight()/2;
        int dx = canvas.getHeight()/3;
        int dy = canvas.getHeight()/3;
        pokeSpace.set(x-dx,y-dy,x+dx,y+dy);
        canvas.drawBitmap(poke,null,pokeSpace,null);

        forText.setColor(Color.BLUE);
        forText.setTextSize(80);
        canvas.drawText("Testing filter", 50, 50, forText);
    }
}
