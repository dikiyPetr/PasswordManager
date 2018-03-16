package com.example.dikiy.passwordmain;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.dikiy.passwordmain.Model.PasswordModel;
import com.example.dikiy.passwordmain.Model.PreloaderModel;
import com.example.dikiy.passwordmain.Presenters.MainPresenter;
import com.example.dikiy.passwordmain.Presenters.PasswordPresenter;
import com.example.dikiy.passwordmain.Presenters.PreloaderPresenter;

/**
 * Created by dikiy on 09.03.2018.
 */

public class PreloaderActivity extends AppCompatActivity {
    private PreloaderPresenter presenter;
    private PreloaderModel model;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,R.anim.flye);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preloader_activity);

        imageView=findViewById(R.id.mainicon2);
        imageView.setVisibility(View.VISIBLE);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.flys);
        animation.setDuration(600);
        imageView.startAnimation(animation);
        model = new PreloaderModel();
        presenter = new PreloaderPresenter(model);
        presenter.attachView(this);
        presenter.viewIsReady();

    }



    public void anim(final boolean stat){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        final TranslateAnimation anim = new TranslateAnimation(0,0,0,-screenHeight/2+imageView.getDrawable().getIntrinsicHeight());
        anim.setDuration(400);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent;
                if (stat) {
                    intent = new Intent(PreloaderActivity.this, LockActivity.class);
                }else{
                    intent = new Intent(PreloaderActivity.this, LoginActivity.class);
                }

                startActivity(intent);
                finish();

            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imageView.startAnimation(anim);

    }


}
