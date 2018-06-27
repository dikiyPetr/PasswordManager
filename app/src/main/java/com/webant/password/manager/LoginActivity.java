package com.webant.password.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.webant.password.manager.Model.LoginModel;
import com.webant.password.manager.Presenters.LoginPresenter;


/**
 * Created by dikiy on 09.03.2018.
 */

public class LoginActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText;
    Button loginButton;
    private LoginPresenter presenter;
    ProgressBar progressBar;
    ScrollView loginFormScrollView;
    TextInputLayout emailTextInputLayout,passwordTextInputLayout;
    TextView registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.close_activity_anim, R.anim.start_activity_anim);
        setContentView(R.layout.login_activity);
        init();
    }

    private void init() {
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        loginFormScrollView = findViewById(R.id.loginFormScrollView);
        registerButton = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBar);
        loginButton = findViewById(R.id.loginButton);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        LoginModel model = new LoginModel();
        presenter = new LoginPresenter(model);
        presenter.attachView(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoginValid(emailEditText.getText().toString())&&isPasswordValid(passwordEditText.getText().toString())){
                    setEnable(false);
                    presenter.login(emailEditText.getText().toString(),passwordEditText.getText().toString());
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
            emailTextInputLayout.setError("Короткий логин");
        }
        return field.length()>2;
    }

    public void login() {
        startActivity(new Intent(this, LockActivity.class));
        finish();
    }


    public void  setEnable(boolean b){
        emailTextInputLayout.setEnabled(b);
        passwordTextInputLayout.setEnabled(b);
        loginButton.setEnabled(b);
        registerButton.setEnabled(b);
    }

    public void setError(String error) {
        if(!error.isEmpty() && error.length()!=0)
       passwordTextInputLayout.setError(error);
    }
}
