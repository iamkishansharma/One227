package com.heycode.one227.sensorex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.heycode.one227.R;

public class ProximityDemo extends AppCompatActivity {

    TextView mTextView;
    SensorManager sm;
    SensorEventListener sel;
    Sensor proximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_demo);
        mTextView = findViewById(R.id.text_proximity);


        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        proximity = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        sel = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] == proximity.getMaximumRange()){
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    mTextView.setText("Screen Awake");
                }else {
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    mTextView.setText("Screen Sleep");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sm.registerListener(sel,proximity,2*1000*1000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(sel);
    }
}