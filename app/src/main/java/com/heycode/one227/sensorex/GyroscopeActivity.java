package com.heycode.one227.sensorex;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.heycode.one227.R;

public class GyroscopeActivity extends AppCompatActivity {
GyroscopeSensor mGyroscopeSensor;
ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGyroscopeSensor = new GyroscopeSensor(this);
        setContentView(R.layout.activity_gyrosope);
        mImageView = findViewById(R.id.imageRotate);

        mGyroscopeSensor.setListener(new GyroscopeSensor.callGyro() {
            @Override
            public void callGyroMethod(float x, float y, float z) {
                if(z>1.0f){
//                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    mImageView.setImageResource(R.drawable.pointing);
                    mImageView.setRotation(90);
                }else if(z<-1.0f) {
//                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    mImageView.setImageResource(R.drawable.pointing);
                    mImageView.setRotation(-90);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGyroscopeSensor.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGyroscopeSensor.unRegister();
    }
}