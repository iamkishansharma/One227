package com.heycode.one227;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ShowObjAnimation extends AppCompatActivity {

    ObjctAnimatorDemo mObjctAnimatorDemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mObjctAnimatorDemo = new ObjctAnimatorDemo(this);
        setContentView(mObjctAnimatorDemo);
    }
}