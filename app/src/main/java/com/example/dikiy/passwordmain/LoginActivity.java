package com.example.dikiy.passwordmain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Model.LoginModel;
import com.example.dikiy.passwordmain.Presenters.LoginPresenter;

/**
 * Created by dikiy on 09.03.2018.
 */

public class LoginActivity extends AppCompatActivity {
    ImageView mainIcon;

    EditText etemail,etpass,etemailR,etpassR,etnameR;
    Button blogin,bregister;
    private LoginPresenter presenter;
    private LoginModel model;
    ProgressBar loginP;
    ScrollView loginF;
    TextInputLayout emailTil,passwordTil,nameTilR,passwordTilR,emailTilR;
    TabHost tabHost;
    //   RelativeLayout editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,R.anim.flye);
        setContentView(R.layout.login_activity);

        init();
    }
    private void init(){
        loginF= findViewById(R.id.loginF);
    tabHost = findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec(  "login");
        tabSpec.setContent(R.id.loginForm);
        tabSpec.setIndicator("login");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("register");
        tabSpec.setContent(R.id.registerForm);
        tabSpec.setIndicator("register");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
        etemailR = findViewById(R.id.emailR);
        etnameR = findViewById(R.id.nameR);
        etpassR = findViewById(R.id.passwordR);
        emailTilR = findViewById(R.id.emailTilR);
        nameTilR = findViewById(R.id.nameTilR);
        passwordTilR = findViewById(R.id.passwordTilR);
        bregister = findViewById(R.id.bregister);

        etemail =findViewById(R.id.email);
        etpass=findViewById(R.id.password);
        blogin=findViewById(R.id.blogin);
        mainIcon = findViewById(R.id.mainicon1);
        loginP = findViewById(R.id.login_progress);
//        loginF= findViewById(R.id.login_form);
        emailTil = findViewById(R.id.emailTil);
        passwordTil= findViewById(R.id.passwordTil);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        Log.v("123xzxcasd", String.valueOf(mainIcon.getDrawable().getIntrinsicHeight()));
        mainIcon.setY(-screenHeight/2+mainIcon.getDrawable().getIntrinsicHeight()/4*3);
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
                attemptLogin();
            }
        });
        etpass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        etpassR.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });
        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
attemptRegister();
            }
        });
    }

    private void attemptRegister() {

        if((isEmailValidR(etemailR.getText().toString())&&isPasswordValidR(etpassR.getText().toString())&&isNameValidR(etnameR.getText().toString()))) {
            presenter.register(etnameR.getText().toString(),etemailR.getText().toString(),etpassR.getText().toString());
            setLoginProgress(true);
        }
    }

    private boolean isNameValidR(String s) {
        nameTilR.setError(null);
        if(!(s.length() >= 4)){
            nameTilR.setError(getString(R.string.error_invalid_name));
        }
        Log.v("12312312sdsz", String.valueOf( !(s.length() >=4)));
        return (s.length() >=4);

    }

    private boolean isPasswordValidR(String s) {
        passwordTilR.setError(null);
        if(!(s.length() >= 4)){
            passwordTilR.setError(getString(R.string.error_invalid_password));
        }
        Log.v("12312312sdsz", String.valueOf(!(s.length() >=4)));
        return (s.length() >=4);
    }

    private boolean isEmailValidR(String s) {
        emailTilR.setError(null);
        if(!(s.contains("@"))){
            emailTilR.setError(getString(R.string.error_invalid_email));
        }
        Log.v("12312312sdsz", String.valueOf(s.contains("@")));
        return s.contains("@");
    }

    private void attemptLogin() {

        if(!(isEmailValid(etemail.getText().toString())||isPasswordValid(etpass.getText().toString()))) {
            presenter.login(etemail.getText().toString(),etpass.getText().toString());
            setLoginProgress(true);
        }
    }

    private boolean isEmailValid(String email) {
        emailTil.setError(null);
        if(!(email.length()>=4)){
            emailTil.setError(getString(R.string.error_invalid_email));
        }
        return !(email.length()>=4);
    }
    private boolean isPasswordValid(String password) {
        passwordTil.setError(null);
        if(!(password.length() >= 4)){
            passwordTil.setError(getString(R.string.error_invalid_password));
        }

        return !(password.length() >=4);
    }
    public void loginStat(){
            Intent intent = new Intent(this, LockActivity.class);
            startActivity(intent);
            finish();
    }
    private void setLoginProgress(Boolean stat){

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if(stat){




            loginP.setVisibility(View.VISIBLE);
            loginF.setVisibility(View.GONE);


        }else{

            loginP.setVisibility(View.GONE);
            loginF.setVisibility(View.VISIBLE);
        }
    }
    public void fail(){
        setLoginProgress(false);
        Toast.makeText(this,"server no connection",Toast.LENGTH_SHORT).show();
    }
    public void error(int code){
        setLoginProgress(false);
        if(code==409){
            Toast.makeText(this, "Имя/почта уже используются", Toast.LENGTH_SHORT).show();
        }else if(code==400){
            emailTilR.setError("invalid email");
        }else {
            Toast.makeText(this, "server error", Toast.LENGTH_SHORT).show();
        }
        }
    public void rigisterOk(){
        setLoginProgress(false);
        etemail.setText(etemailR.getText().toString());
        etnameR.setText("");
        etemailR.setText("");
        etpassR.setText("");
        tabHost.setCurrentTab(0);

    }
}
