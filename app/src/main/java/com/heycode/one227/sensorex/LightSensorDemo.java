package com.heycode.one227.sensorex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import com.heycode.one227.R;

public class LightSensorDemo extends AppCompatActivity {

    SensorManager sm;
    Sensor light;
    SensorEventListener sel;
    View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor_demo);

        root = findViewById(R.id.light_relative);

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);


        sel = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                int grayShade = (int) sensorEvent.values[0];
                if (grayShade > 255) grayShade = 255;
//                mTextView.setBackgroundColor(Color.rgb(255 - grayShade, 255 - grayShade, 255 - grayShade));
                root.setBackgroundColor(Color.rgb(grayShade, grayShade, grayShade));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(sel, light, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(sel);
    }
}