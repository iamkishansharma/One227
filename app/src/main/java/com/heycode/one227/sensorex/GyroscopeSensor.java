package com.heycode.one227.sensorex;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

class GyroscopeSensor {
    interface callGyro{
        void callGyroMethod(float x, float y, float z);/////
    }
    callGyro mCallGyro;
    void setListener(callGyro cg){
        mCallGyro = cg;
    }

    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    GyroscopeSensor(Context context){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(mCallGyro!=null){
                    mCallGyro.callGyroMethod(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);
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
