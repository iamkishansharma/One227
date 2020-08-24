package com.heycode.one227;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;



class CustomView extends View {
    Paint p;
    int x = 10;
    public CustomView(Context context) {
        super(context);
        init();
    }
    public void init(){
        p = new Paint();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        p.setColor(Color.YELLOW);
        canvas.drawColor(Color.RED);
        canvas.drawRect(100, 100, 500, 500, p);
        canvas.drawArc(500  , 500, 800, 800, x+20, 45, true, p);
        canvas.drawArc(500  , 500, 800, 800, x+120, 45, true, p);
        canvas.drawArc(500  , 500, 800, 800, x+240, 45, true, p);
        drawImage(canvas);
    }

    void drawImage(Canvas canvas){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.hc_rect);
        bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
        Matrix matrix = new Matrix();
        matrix.postRotate(70);
        matrix.postTranslate(300, 300);
        canvas.drawBitmap(bitmap, matrix, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startArc();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
    public void startArc(){
        x = x+5;
        invalidate();
    }

}
