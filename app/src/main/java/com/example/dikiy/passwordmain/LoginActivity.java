package com.example.dikiy.passwordmain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Model.LoginModel;
import com.example.dikiy.passwordmain.Model.PreloaderModel;
import com.example.dikiy.passwordmain.Presenters.LoginPresenter;
import com.example.dikiy.passwordmain.Presenters.PreloaderPresenter;

/**
 * Created by dikiy on 09.03.2018.
 */

public class LoginActivity extends AppCompatActivity {
    ImageView mainIcon;
    LinearLayout tableLayout;
    EditText etname,etpass;
    Button blogin;
    private LoginPresenter presenter;
    private LoginModel model;
    //   RelativeLayout editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,R.anim.flye);
        setContentView(R.layout.login_activity);

        init();
    }
    private void init(){
        etname=findViewById(R.id.etname);
        etpass=findViewById(R.id.etpass);
        blogin=findViewById(R.id.blogin);
        mainIcon = findViewById(R.id.mainicon1);
        tableLayout = findViewById(R.id.textl1);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        mainIcon.setY(-screenHeight/2+mainIcon.getDrawable().getIntrinsicHeight());
//        final TranslateAnimation anim = new TranslateAnimation(0,0,0,-screenHeight/2+mainIcon.getDrawable().getIntrinsicHeight());
//        anim.setDuration(400);
//        anim.setFillAfter(true);
//        anim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                Animation anim1 = new AnimationUtils().loadAnimation(LoginActivity.this,R.anim.flys);
//                anim1.setDuration(300);
//                anim1.setFillAfter(true);
//                tableLayout.startAnimation(anim1);
//            }
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//        mainIcon.startAnimation(anim);
        model = new LoginModel();
        presenter = new LoginPresenter(model);
        presenter.attachView(this);
        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login(etname.getText().toString(),etpass.getText().toString());
                etpass.setEnabled(false);
                etname.setEnabled(false);
            }
        });
    }
    public void loginStat(int stat){
        Log.v("123123123123123", String.valueOf(stat));

        if(stat==2){
            LoadText.setText("pass","");
            Intent intent = new Intent(this, LockActivity.class);
            startActivity(intent);
            finish();
        }else{
            etpass.setEnabled(true);
            etname.setEnabled(true);
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
        }
    }

}
