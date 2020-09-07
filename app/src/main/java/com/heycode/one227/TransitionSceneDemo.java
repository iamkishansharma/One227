package com.heycode.one227;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TransitionSceneDemo extends AppCompatActivity {

    Scene scene1, scene2;
    TransitionSet transitionSet;
    boolean start;
    ImageView img1, img2, img3, img4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_scene_demo);
        img1 = findViewById(R.id.image1_tran);
        img2 = findViewById(R.id.image2_tran);
        img3 = findViewById(R.id.image3_tran);
        img4 = findViewById(R.id.image4_tran);

        transitionSet = new TransitionSet();
        transitionSet.setDuration(2000);
        transitionSet.addTransition(new ChangeBounds());

        RelativeLayout relativeLayout = findViewById(R.id.relativeView_tran);
        ViewGroup startView = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_transition_scene_demo,relativeLayout,false);
        ViewGroup endView = (ViewGroup) getLayoutInflater().inflate(R.layout.transition_scene_2,relativeLayout,false);

        scene1 = new Scene(relativeLayout,startView);
        scene2 = new Scene(relativeLayout,endView);

        start = true;

    }

    public void changTheScene(View view){
        if(start){
            TransitionManager.go(scene2,transitionSet);
            start=false;
        }else {
            TransitionManager.go(scene1,transitionSet);
            start = true;
        }
    }
}