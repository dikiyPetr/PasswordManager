package com.example.dikiy.passwordmain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dikiy.passwordmain.Model.LoginModel;
import com.example.dikiy.passwordmain.Presenters.LoginPresenter;

import java.util.concurrent.locks.Lock;

/**
 * Created by dikiy on 09.03.2018.
 */

public class LoginActivity extends AppCompatActivity {
    EditText loginEditText, passwordEditText;
    Button loginButton,registerButton;
    private LoginPresenter presenter;
    ProgressBar progressBar;
    ScrollView loginFormScrollView;
    TextInputLayout loginTextInputLayout,passwordTextInputLayout;
    TextView returnPasswordTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.close_activity_anim, R.anim.start_activity_anim);
        setContentView(R.layout.login_activity);
        init();
    }

    private void init() {
        returnPasswordTextView = findViewById(R.id.returnPasswordTextView);
        loginTextInputLayout = findViewById(R.id.loginTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        loginFormScrollView= findViewById(R.id.loginFormScrollView);
        registerButton = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBar);
        loginButton = findViewById(R.id.loginButton);
        loginEditText = findViewById(R.id.loginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        LoginModel model = new LoginModel();
        presenter = new LoginPresenter(model);
        presenter.attachView(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoginValid(loginEditText.getText().toString())&&isPasswordValid(passwordEditText.getText().toString())){
                    setEnable(false);
                    presenter.login(loginEditText.getText().toString(),passwordEditText.getText().toString());
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this,RegisterActivity.class),1);
            }
        });

    }
    private void activitiMode(boolean status){
        //true progress
        //false form
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if(status){
            loginFormScrollView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            loginFormScrollView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
    private boolean isPasswordValid(String field){
        if(!(field.length()>2)){
            passwordTextInputLayout.setError("Короткий пароль");
        }
        return field.length()>2;
    }
    private boolean isLoginValid(String field){
        if(!(field.length()>2)){
            loginTextInputLayout.setError("Короткий логин");
        }
        return field.length()>2;
    }

    public void login() {
        startActivity(new Intent(this, LockActivity.class));
        finish();
    }

    public void modelFail() {
        setEnable(true);
        Toast.makeText(this,"Нет ответа от сервера",Toast.LENGTH_SHORT).show();
    }

    public void modelError() {
        setEnable(true);
        Toast.makeText(this,"Ошибка на сервере",Toast.LENGTH_SHORT).show();
    }

    public void invalidLogin() {
        setEnable(true);
        passwordTextInputLayout.setError("Неверный логин/пароль");
    }
    public void  setEnable(boolean b){
        loginTextInputLayout.setEnabled(b);
        passwordTextInputLayout.setEnabled(b);
        loginButton.setEnabled(b);
        registerButton.setEnabled(b);
    }
}
