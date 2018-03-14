package com.example.dikiy.passwordmain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by dikiy on 09.03.2018.
 */

public class LockActivity extends AppCompatActivity {
   ImageView mainIcon;
   LinearLayout tableLayout;
   RelativeLayout editText;
   EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_activity);

        init();
    }
    private void init(){
    mainIcon = findViewById(R.id.mainicon);
    tableLayout = findViewById(R.id.textl);
    editText=findViewById(R.id.buttonl);
    editText2=findViewById(R.id.editText13);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
            final TranslateAnimation anim = new TranslateAnimation(0,0,0,-screenHeight/2+mainIcon.getDrawable().getIntrinsicHeight());
        anim.setDuration(400);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Animation anim1 = new AnimationUtils().loadAnimation(LockActivity.this,R.anim.flys);
                anim1.setDuration(300);
                anim1.setFillAfter(true);
                tableLayout.startAnimation(anim1);
                editText.startAnimation(anim1);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mainIcon.startAnimation(anim);
    editText2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LockActivity.this, MainActivity.class);

            startActivity(intent);




            finish();
        }
    });
    }


}
