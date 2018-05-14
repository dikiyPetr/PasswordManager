package com.example.dikiy.passwordmain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dikiy.passwordmain.Model.PreloaderModel;
import com.example.dikiy.passwordmain.Presenters.PreloaderPresenter;

/**
 * Created by dikiy on 09.03.2018.
 */

public class PreloaderActivity extends AppCompatActivity {
    private PreloaderPresenter presenter;
    private PreloaderModel model;
    ImageView mainIcon;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,R.anim.flye);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preloader_activity);

        mainIcon =findViewById(R.id.mainicon2);
        mainIcon.setVisibility(View.VISIBLE);
        progressBar= findViewById(R.id.progressBar);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.flys);
        animation.setDuration(600);
        mainIcon.startAnimation(animation);
        progressBar.startAnimation(animation);
        model = new PreloaderModel();
        presenter = new PreloaderPresenter(model);
        presenter.attachView(this);
        presenter.viewIsReady();

    }



    public void anim(final int stat){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        final TranslateAnimation anim = new TranslateAnimation(0,0,0,-screenHeight/2+ mainIcon.getDrawable().getIntrinsicHeight()/4*3);
        anim.setDuration(400);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Animation animation1= AnimationUtils.loadAnimation(PreloaderActivity.this,R.anim.flye);
                animation1.setFillAfter(true);
                progressBar.startAnimation(animation1);
            }
            @Override
            public void onAnimationEnd(Animation animation) {

                Intent intent;
                if (stat==1) {
                    intent = new Intent(PreloaderActivity.this, LockActivity.class);
                    startActivity(intent);
                    finish();
                }else if(stat==0){
                    intent = new Intent(PreloaderActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(PreloaderActivity.this,"internet ne internet",Toast.LENGTH_SHORT);
                    presenter.conect();
                }



            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mainIcon.startAnimation(anim);

    }


}
