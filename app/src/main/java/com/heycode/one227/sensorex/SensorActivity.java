package com.heycode.one227.sensorex;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.heycode.one227.sensorex.AccelerometerSensor;
import com.heycode.one227.R;

public class SensorActivity extends AppCompatActivity {

    AccelerometerSensor mAccelerometerSensor;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccelerometerSensor = new AccelerometerSensor(this);
        setContentView(R.layout.activity_sensor);
        tv= findViewById(R.id.tv_sensor);

        mAccelerometerSensor.setListener(new AccelerometerSensor.call() {
            @Override
            public void transition(float x, float y, float z) {
                if(x>1.0f){
//                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    tv.setText("Left Side");
                }else if(x<-1.0f) {
//                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    tv.setText("Right Side");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAccelerometerSensor.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAccelerometerSensor.unRegister();
    }
}