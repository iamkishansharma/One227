package com.heycode.one227;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    CustomView cv;
    CustomViewWithBitmap gameWithImage;
    DrawPath drawPath;
    Paint p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //First Page Draw Rectangle, Arc, Triangle etc using CustomView
        cv = new CustomView(MainActivity.this);
        //Second Page to Make a Game using Bitmap & CustomView
        gameWithImage = new CustomViewWithBitmap(MainActivity.this);
        //Third Page to draw path on Canvas
        drawPath = new DrawPath(MainActivity.this);

        //TODO:: Don't forget to pass the object of your CustomView Class
        setContentView(drawPath);//pass object to show other custom things
    }


}