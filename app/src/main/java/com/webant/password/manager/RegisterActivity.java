package com.webant.password.manager;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.webant.password.manager.Model.LoginModel;
import com.webant.password.manager.Presenters.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity {
    ProgressBar progressBar;
    ScrollView registerFormScrollView;
    TextInputLayout  emailTextInputLayout, passwordTextInputLayout;
    EditText  emailEditText, passwordEditText;
    Button registerButton;
    TextView loginButton;
    RegisterPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        progressBar = findViewById(R.id.progressBar);
        registerFormScrollView = findViewById(R.id.registerFormScrollView);
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton=findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailValid(emailEditText.getText().toString()) && isPasswordValid(passwordEditText.getText().toString())) {
                    presenter.register(emailEditText.getText().toString(),passwordEditText.getText().toString());
                    activityMode(true);
                }
            }
        });
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    presenter.register(emailEditText.getText().toString(),passwordEditText.getText().toString());
                    activityMode(true);
                    return true;
                }
                return false;
            }
        });
        LoginModel model = new LoginModel();
        presenter = new RegisterPresenter(model);
        presenter.attachView(this);
    }

    public void activityMode(boolean status) {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if (status) {
            registerFormScrollView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            registerFormScrollView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private boolean isPasswordValid(String field) {
        if (!(field.length() > 2)) {
            passwordTextInputLayout.setError(getString(R.string.short_password));
        }
        return field.length() > 2;
    }

    private boolean isEmailValid(String field) {
        if (!(field.length() > 2)) {
            emailTextInputLayout.setError(getString(R.string.incorect_mail));
        }
        return field.length() > 2;
    }

    public void execute() {
        finish();
    }

    public void error(String s) {
        activityMode(false);
    }

    public void setError(String error) {
        if(!error.isEmpty() && error.length()!=0)
       emailTextInputLayout.setError(error);
    }
}
