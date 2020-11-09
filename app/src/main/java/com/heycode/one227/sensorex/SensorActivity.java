package com.heycode.one227.sensorex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.heycode.one227.sensorex.AccelerometerSensor;
import com.heycode.one227.R;

public class SensorActivity extends AppCompatActivity {

    AccelerometerSensor mAccelerometerSensor;
    TextView tv;
    SensorManager mSensorManager;
    Sensor accelerometer;
    SensorEventListener sel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccelerometerSensor = new AccelerometerSensor(this);
        setContentView(R.layout.activity_sensor);
        tv= findViewById(R.id.tv_sensor);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sel = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float values = sensorEvent.values[0];
                if(values>0.0f){
                    tv.setText("LEFT");
                }else if(values<0.0f){
                    tv.setText("RIGHT");
                }else {
                    tv.setText("");
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

// THIS IS THE OTHER WAY OF IMPLEMENTING ACCELEROMETER--------------------------
//        mAccelerometerSensor.setListener(new AccelerometerSensor.call() {
//            @Override
//            public void transition(float x, float y, float z) {
//                if(x>1.0f){
////                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
//                    tv.setText("Left Side");
//                }else if(x<-1.0f) {
//                    tv.setText("Right Side");
//                }
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mAccelerometerSensor.register(); //for second way
        mSensorManager.registerListener(sel, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mAccelerometerSensor.unRegister();//for second way
        mSensorManager.unregisterListener(sel);
    }
}