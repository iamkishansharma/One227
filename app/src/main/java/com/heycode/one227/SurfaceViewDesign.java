package com.heycode.one227;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class SurfaceViewDesign extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder surfaceHolder;
    Paint p;
    float x=0,y=0;
    public SurfaceViewDesign(Context context) {
        super(context);
        if(surfaceHolder == null){
            surfaceHolder = getHolder();//get SurfaceHolder Object
            surfaceHolder.addCallback(SurfaceViewDesign.this);//passing the context to make changes on move or something
        }
        if(p==null){
            p = new Paint();
            p.setColor(Color.RED);
        }
        this.setBackgroundColor(Color.YELLOW);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public void drawCircle(){
        Canvas canvas = surfaceHolder.lockCanvas();
        Paint sp = new Paint();
//        draw(canvas);
//        canvas.drawRect(100, 100, 500, 500, p);
        sp.setColor(Color.CYAN);
        canvas.drawCircle(0,0,100,sp);
        canvas.drawCircle(x,y,100,p);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }
    public void drawRectangle(){
        Canvas canvas = surfaceHolder.lockCanvas();
        Paint sp = new Paint();
        sp.setColor(Color.BLUE);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), sp);
        canvas.drawRect(x, y, x+200, x+200, p);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }
    void setCircleX(float x){
        this.x = x;
    }
    void setCircleY(float y){
        this.y = y;
    }
    void setPaintColor(Paint pp){
        this.p = pp;
    }
}
