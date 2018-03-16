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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dikiy.passwordmain.DBase.LoadText;

/**
 * Created by dikiy on 09.03.2018.
 */

public class LockActivity extends AppCompatActivity implements View.OnClickListener {
   ImageView mainIcon;
   EditText editText;
   TextView lotv,tvb1,tvb2,tvb3,tvb4,tvb5,tvb6,tvb7,tvb8,tvb9,tvb0,tvbc,tvm;
   int mode=1;
   String pass;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,R.anim.flye);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_activity);

        init();
    }


    private void init(){

    mainIcon = findViewById(R.id.mainicon);
    editText=findViewById(R.id.edp);
    editText.setEnabled(false);
    lotv = findViewById(R.id.tvbl);
    tvm=findViewById(R.id.tvm);

        tvbc=findViewById(R.id.tvbc);
        tvb0=findViewById(R.id.tvb0);
        tvb1=findViewById(R.id.tvb1);
        tvb2=findViewById(R.id.tvb2);
        tvb3=findViewById(R.id.tvb3);
        tvb4=findViewById(R.id.tvb4);
        tvb5=findViewById(R.id.tvb5);
        tvb6=findViewById(R.id.tvb6);
        tvb7=findViewById(R.id.tvb7);
        tvb8=findViewById(R.id.tvb8);
        tvb9=findViewById(R.id.tvb9);

        tvb0.setOnClickListener(this);
        tvb1.setOnClickListener(this);
        tvb2.setOnClickListener(this);
        tvb3.setOnClickListener(this);
        tvb4.setOnClickListener(this);
        tvb5.setOnClickListener(this);
        tvb6.setOnClickListener(this);
        tvb7.setOnClickListener(this);
        tvb8.setOnClickListener(this);
        tvb9.setOnClickListener(this);
        tvbc.setOnClickListener(this);

        if(LoadText.getText("pass").length()!=4){
            tvm.setText("set password");
        }else {mode=0;}
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        mainIcon.setY(-screenHeight/2+mainIcon.getDrawable().getIntrinsicHeight());
//
//            DisplayMetrics displaymetrics = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//            int screenWidth = displaymetrics.widthPixels;
//            int screenHeight = displaymetrics.heightPixels;
//                final TranslateAnimation anim = new TranslateAnimation(0,0,0,-screenHeight/2+mainIcon.getDrawable().getIntrinsicHeight());
//            anim.setDuration(400);
//            anim.setFillAfter(true);
//            anim.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                }
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    Animation anim1 = new AnimationUtils().loadAnimation(LockActivity.this,R.anim.flys);
//                    anim1.setDuration(300);
//                    anim1.setFillAfter(true);
//                    tableLayout.startAnimation(anim1);
//                    editText.startAnimation(anim1);
//                }
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//                }
//            });
//            mainIcon.startAnimation(anim);

    lotv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoadText.setNull();
            Intent intent = new Intent(LockActivity.this, LoginActivity.class);

            startActivity(intent);
            finish();
        }
    });
    }
    public void editPass(int namber){
        if(namber==11 ){
            editText.setText("");
        }else if(editText.getText().length()<4){
        editText.setText(editText.getText()+""+namber);
        if(editText.getText().length()==4){
            switch(mode){
                case 1:tvm.setText("repeat password");
                mode=2;
                pass=editText.getText().toString();
                    editText.setText(""); break;
                case 2:if(pass.equals(editText.getText().toString())){tvm.setText("write password");
                mode=0;
                LoadText.setText("pass",editText.getText().toString());
                    editText.setText("");
                }else{
                    tvm.setText("set password");
                    mode=1;
                    editText.setText("");
                } break;
                case 0:if(editText.getText().toString().equals(LoadText.getText("pass"))) {
                    Intent intent = new Intent(LockActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                   editText.setText("");
                    Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
                }
                    break;
            }
        }}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvb0:
                    editPass(0);
                break;
            case R.id.tvb1:
                editPass(1);
                break;
            case R.id.tvb2:
                editPass(2);
                break;
            case R.id.tvb3:
                editPass(3);
                break;
            case R.id.tvb4:
                editPass(4);
                break;
            case R.id.tvb5:
                editPass(5);
                break;
            case R.id.tvb6:
                editPass(6);
                break;
            case R.id.tvb7:
                editPass(7);
                break;
            case R.id.tvb8:
                editPass(8);
                break;
            case R.id.tvb9:
                editPass(9);
                break;

            case R.id.tvbc:
                editPass(11);
                break;
        }
    }
}
