package com.heycode.one227.sensorex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.heycode.one227.R;

public class MagnetometerDemo extends AppCompatActivity implements SensorEventListener{

    TextView tv;
    ImageView iv;
    SensorManager sm;
    Sensor mg, ac;

    float [] acl = new float[3];
    float [] mgl = new float[4];
    boolean acb = false;
    boolean mgb = false;

    float[] rotationMatrix = new float[9];
    float[] orientation = new float[3];
    float currentDegree = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetometer_demo);

        tv = findViewById(R.id.text_magnet);
        iv = findViewById(R.id.image_magnet);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        ac = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mg = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener( this,ac,SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener( this,mg,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this,ac);
        sm.unregisterListener(this,mg);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == ac){
            System.arraycopy(sensorEvent.values, 0, acl, 0, sensorEvent.values.length);
            acb = true;
        }else if(sensorEvent.sensor == mg){
            System.arraycopy(sensorEvent.values, 0, mgl, 0, sensorEvent.values.length);
            mgb = true;
        }

        if(acb && mgb){
            SensorManager.getRotationMatrix(rotationMatrix, null, acl, mgl);
            SensorManager.getOrientation(rotationMatrix,orientation );
            float radian = orientation[0];
            float toDegree = (float)(Math.toDegrees(radian));
            RotateAnimation rotateAnimation = new RotateAnimation(currentDegree, -toDegree, Animation.RELATIVE_TO_SELF,
                    0.5f,Animation.RELATIVE_TO_SELF,0.05f);
            rotateAnimation.setDuration(260);
            rotateAnimation.setFillAfter(true);
            tv.setText("Heading : "+toDegree+" deg");
            iv.startAnimation(rotateAnimation);//STARTING Animation
            currentDegree = -toDegree;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}