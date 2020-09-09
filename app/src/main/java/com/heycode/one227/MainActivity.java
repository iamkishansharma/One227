package com.heycode.one227;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnCir, btnRect;
    LinearLayout mLinearLayout;
    SurfaceViewDesign mSurfaceViewDesign;
    boolean ball = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//pass object to show other custom things
        btnCir = findViewById(R.id.circle_btn);
        btnRect = findViewById(R.id.rectangle_btn);
        mLinearLayout = findViewById(R.id.linear2);

        btnCir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ball=true;
            }
        });
        btnRect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ball=false;
            }
        });


        //Setting View in LinearLayout2
        mSurfaceViewDesign = new SurfaceViewDesign(MainActivity.this);
        mSurfaceViewDesign.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(view instanceof SurfaceView){
                    float x = view.getX();
                    float y = view.getY();
                    mSurfaceViewDesign.setrcX(x);
                    mSurfaceViewDesign.setrcY(y);
                    if(ball){
                        Paint pp = new Paint();
                        pp.setColor(Color.WHITE);
                        mSurfaceViewDesign.drawCircle();
                        mSurfaceViewDesign.setPaintColor(pp);
                    }else {
                        Paint pp = new Paint();
                        pp.setColor(Color.BLACK);
                        mSurfaceViewDesign.drawRectangle();
                        mSurfaceViewDesign.setPaintColor(pp);
                    }
                    //Wow! onTouch worked!
                    return true;
                }else {
                    Toast.makeText(MainActivity.this, "Ummm! onTouch didn't worked!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });

        mLinearLayout.addView(mSurfaceViewDesign);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cc:
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
                //This is a transition
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_in_left);
                break;
            case R.id.ccwi:
                //Todo::
                startActivity(new Intent(MainActivity.this, TransitionSceneDemo.class));
                break;
            case R.id.ccoa:
                startActivity(new Intent(MainActivity.this, ShowObjAnimation.class));
                break;
            case R.id.googleMap:
                startActivity(new Intent(MainActivity.this, GoogleMapsActivity.class));
                break;
            case R.id.googleMapAddress:
                startActivity(new Intent(MainActivity.this, ReadableAdd.class));
                break;
            default:return false;
        }
        return true;
    }
}