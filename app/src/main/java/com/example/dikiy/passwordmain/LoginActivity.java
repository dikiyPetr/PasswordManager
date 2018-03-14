package com.example.dikiy.passwordmain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by dikiy on 09.03.2018.
 */

public class LoginActivity extends AppCompatActivity {
    ImageView mainIcon;
    LinearLayout tableLayout;
    //   RelativeLayout editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        init();
    }
    private void init(){
        mainIcon = findViewById(R.id.mainicon1);
        tableLayout = findViewById(R.id.textl1);
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
                Animation anim1 = new AnimationUtils().loadAnimation(LoginActivity.this,R.anim.flys);
                anim1.setDuration(300);
                anim1.setFillAfter(true);
                tableLayout.startAnimation(anim1);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mainIcon.startAnimation(anim);
    }

}
