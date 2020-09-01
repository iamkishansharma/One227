package com.heycode.one227;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;

class SurfaceHolderDemo extends SurfaceView {
    Bitmap bg, mario;
    Paint paint;
    int x=10;
    int y= 1550;
    SurfaceHolder mSurfaceHolder;
    public SurfaceHolderDemo(Context context) {
        super(context);

        mSurfaceHolder = getHolder();

        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Canvas c = mSurfaceHolder.lockCanvas();
                draw(c);
                mSurfaceHolder.unlockCanvasAndPost(c);

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();

        bg = BitmapFactory.decodeResource(getResources(), R.drawable.game_bg);
        Rect rect1 = new Rect(0,0,bg.getWidth(),bg.getHeight());
        Rect rect2 = new Rect(0,0,canvas.getWidth(),canvas.getHeight());
        canvas.drawBitmap(bg,rect1,rect2,null);

        mario = BitmapFactory.decodeResource(getResources(), R.drawable.small_mario);
        canvas.drawBitmap(mario, x,y,null);

        paint.setColor(Color.RED);
        canvas.drawArc(500  , 500, 800, 800, x+20, 45, true, paint);
        canvas.drawArc(500  , 500, 800, 800, x+120, 45, true, paint);
        canvas.drawArc(500  , 500, 800, 800, x+240, 45, true, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                jump();
                moveAhead();
                break;
            case MotionEvent.ACTION_UP:
                down();break;
        }
        return true;
    }
    public void moveAhead(){
        if(x>1000){x=-10; invalidate();
        }else {x+=50; invalidate();}
    }
    public void jump(){y-=100; invalidate();
    }
    public void down(){y+=100; invalidate();
    }
}

class MyThread extends Thread{

}