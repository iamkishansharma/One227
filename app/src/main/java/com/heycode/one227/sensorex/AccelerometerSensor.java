package com.heycode.one227.sensorex;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

class AccelerometerSensor {
    interface call{
        void transition(float x, float y, float z);/////
    }
    call l;
    void setListener( call l){
        this.l = l;
    }
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    AccelerometerSensor(Context context){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(l!=null){
                    l.transition(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    void register(){
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);

    }
    void unRegister(){
        sensorManager.unregisterListener(sensorEventListener);
    }
}
