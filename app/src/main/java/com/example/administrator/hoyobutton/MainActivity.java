package com.example.administrator.hoyobutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

public class MainActivity extends AppCompatActivity {


    HoyoButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (HoyoButton) findViewById(R.id.btn);
        btn.setButtonColor(getResources().getColor(R.color.colorAccent));

        btn.setButtonColor(getResources().getColor(R.color.green));

        btn.setShape(HoyoButton.Shape.RECT, getResources().getColor(R.color.green), 2, getResources().getColor(R.color.colorAccent));


        btn.setAnimationDuration(500);
        btn.setAnimation(HoyoButton.Animation.FADEOUT);


        btn.setHoyoClickListner(new HoyoClickListner() {
            @Override
            public void onClick(View v) {

            }
        });


        btn.setAnimationListner(new HoyoAnimationListner() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Log.e("end", "enfd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
