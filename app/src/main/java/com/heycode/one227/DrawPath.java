package com.heycode.one227;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

class DrawPath extends View {
    Path path;
    Paint paint;
    public DrawPath(Context context) {
        super(context);

        path = new Path();
        paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.YELLOW);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if((x>=10&&x<=110) && (y>=10&&y<=120)){
                    path.reset();
                }
                break;

        }
        invalidate();
        return true;
    }
}
