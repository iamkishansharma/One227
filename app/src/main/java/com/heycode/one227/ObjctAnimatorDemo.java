package com.heycode.one227;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

class ObjctAnimatorDemo extends View {
    int duration = 4000;
    int delay = 1000;
    float x,y,radius;
    AnimatorSet animatorSet;
    Paint paint;
    public ObjctAnimatorDemo(Context context) {
        super(context);
        animatorSet = new AnimatorSet();
        paint= new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x,y,radius,paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ObjectAnimator growCircle = new ObjectAnimator().ofFloat(this,"radius",0,getWidth());
        growCircle.setDuration(duration);
        growCircle.setInterpolator(new LinearInterpolator());

        ObjectAnimator shrinkCircle = new ObjectAnimator().ofFloat(this,"radius",getWidth(),0);
        shrinkCircle.setDuration(duration);
        shrinkCircle.setInterpolator(new DecelerateInterpolator());
        shrinkCircle.setStartDelay(delay);

        animatorSet.play(growCircle).before(shrinkCircle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                if(animatorSet!=null && animatorSet.isRunning()){
                    animatorSet.end();
                }
                animatorSet.start();
                break;
        }
        return false;
    }
    public void setRadius(float r){
        radius = r;
        paint.setColor(Color.GREEN+(int)radius/5);
        invalidate();
    }
}
