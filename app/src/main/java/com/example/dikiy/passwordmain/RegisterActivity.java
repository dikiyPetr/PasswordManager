package com.example.dikiy.passwordmain;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dikiy.passwordmain.Model.LoginModel;
import com.example.dikiy.passwordmain.Presenters.LoginPresenter;
import com.example.dikiy.passwordmain.Presenters.RegisterPrisenter;

public class RegisterActivity extends AppCompatActivity {
    ProgressBar progressBar;
    ScrollView registerFormScrollView;
    TextInputLayout loginTextInputLayout, emailTextInputLayout, passwordTextInputLayout;
    EditText loginEditText, emailEditText, passwordEditText;
    Button registerButton;
    RegisterPrisenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        progressBar = findViewById(R.id.progressBar);
        registerFormScrollView = findViewById(R.id.registerFormScrollView);
        loginTextInputLayout = findViewById(R.id.loginTextInputLayout);
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        loginEditText = findViewById(R.id.loginEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginValid(loginEditText.getText().toString()) && isEmailValid(emailEditText.getText().toString()) && isPasswordValid(passwordEditText.getText().toString())) {
                    presenter.register(loginEditText.getText().toString(),emailEditText.getText().toString(),passwordEditText.getText().toString());
                    activitiMode(true);
                }
            }
        });
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    presenter.register(loginEditText.getText().toString(),emailEditText.getText().toString(),passwordEditText.getText().toString());
                    activitiMode(true);
                    return true;
                }
                return false;
            }
        });
        LoginModel model = new LoginModel();
        presenter = new RegisterPrisenter(model);
        presenter.attachView(this);
    }

    private void activitiMode(boolean status) {
        //true progress
        //false form
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
            passwordTextInputLayout.setError("короткий пароль");
        }
        return field.length() > 2;
    }

    private boolean isLoginValid(String field) {
        if (!(field.length() > 2)) {
            loginTextInputLayout.setError("короткий логин");
        }
        return field.length() > 2;
    }

    private boolean isEmailValid(String field) {
        if (!(field.length() > 2)) {
            emailTextInputLayout.setError("неправильная почта");
        }
        return field.length() > 2;
    }

    public void execute() {
        finish();
        Toast.makeText(this,"Регистрация прошла успешно",Toast.LENGTH_SHORT).show();
    }

    public void error(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        activitiMode(false);
    }
}
