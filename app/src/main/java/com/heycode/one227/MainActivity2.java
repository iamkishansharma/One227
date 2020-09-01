package com.heycode.one227;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    CustomView cv;
    CustomViewWithBitmap gameWithImage;
    DrawPath drawPath;
    SurfaceHolderDemo surfaceHolderDemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //First Page Draw Rectangle, Arc, Triangle etc using CustomView
        cv = new CustomView(MainActivity2.this);
        //Second Page to Make a Game using Bitmap & CustomView
        gameWithImage = new CustomViewWithBitmap(MainActivity2.this);
        //Third Page to draw path on Canvas
        drawPath = new DrawPath(MainActivity2.this);

        //Fourth Page to draw mario game on SurfaceView
        surfaceHolderDemo = new SurfaceHolderDemo(MainActivity2.this);

        //TODO:: Don't forget to pass the object of your CustomView Class
        setContentView(drawPath);//pass object to show other custom things
    }
}