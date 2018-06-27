package com.webant.password.manager;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.webant.password.manager.Adapters.Get.GetService_Items;
import com.webant.password.manager.Model.AddServiceModel;
import com.webant.password.manager.Presenters.AddServicePresenter;

public class AddServiceActivity extends AppCompatActivity {
    FrameLayout progresS;
    RelativeLayout toolbarS;
    EditText nameS, urlS, loginS, passwordS, tokenS, tokenNameS;
    Button createS;
    ImageView back, edit;
    AddServicePresenter presenter;
    TextInputLayout nameTils, urlTils, tokenTils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        init();
    }

    private void init() {
        back = findViewById(R.id.backImageView);
        edit = findViewById(R.id.edit);
        progresS = findViewById(R.id.progresS);
        nameTils = findViewById(R.id.nameTILS);
        urlTils = findViewById(R.id.urlTILS);
        tokenTils = findViewById(R.id.tokenTILS);
        toolbarS = findViewById(R.id.toolbarSC);
        nameS = findViewById(R.id.nameS);
        urlS = findViewById(R.id.urlS);
        loginS = findViewById(R.id.loginS);
        passwordS = findViewById(R.id.passwordS);
        tokenS = findViewById(R.id.tokenS);
        tokenNameS = findViewById(R.id.tokenNameS);
        createS = findViewById(R.id.createS);
        AddServiceModel usersModel = new AddServiceModel();
        presenter = new AddServicePresenter(usersModel);
        presenter.attachView(this);
        createS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCorrectName() && isCorrectUrl() && isCorrectToken()) {
                    createS.setEnabled(false);
                    progresS.setVisibility(View.VISIBLE);
                    presenter.createService(nameS.getText().toString(), urlS.getText().toString(),
                            loginS.getText().toString(), passwordS.getText().toString(),
                            tokenS.getText().toString(), tokenNameS.getText().toString());
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCorrectName() && isCorrectUrl() && isCorrectToken()) {
                    createS.setEnabled(false);
                    progresS.setVisibility(View.VISIBLE);
                    presenter.editService(Integer.parseInt(getIntent().getStringExtra("id")), nameS.getText().toString(), urlS.getText().toString(),
                            loginS.getText().toString(), passwordS.getText().toString(),
                            tokenS.getText().toString(), tokenNameS.getText().toString());
                }
            }
        });
        if (getIntent().getStringExtra("id") != null) {
            createS.setVisibility(View.GONE);
            presenter.loadService(Integer.parseInt(getIntent().getStringExtra("id")));
        } else {
            edit.setVisibility(View.GONE);
        }
    }

    public void cancelRefresh() {
        createS.setEnabled(true);
        progresS.setVisibility(View.GONE);
    }

    private boolean isCorrectName() {
        if (nameS.getText().toString().length() != 0) {
            return true;
        }
        nameTils.setError(getString(R.string.incorect_name));
        return false;
    }

    private boolean isCorrectUrl() {
        if (urlS.getText().toString().length() != 0) {
            return true;
        }
        urlTils.setError(getString(R.string.incorect_url));
        return false;
    }

    private boolean isCorrectToken() {
        if (tokenS.getText().toString().length() != 0) {
            return true;
        }
        tokenTils.setError(getString(R.string.incorect_token));
        return false;
    }

    public void error() {
        createS.setEnabled(true);
        progresS.setVisibility(View.GONE);
    }

    public void execute() {
        progresS.setVisibility(View.GONE);
        setResult(RESULT_OK, getIntent());
        finish();
    }

    public void loadDefault(GetService_Items item) {
        nameS.setText(item.getName());
        urlS.setText(item.getUrl());
        loginS.setText(item.getLogin());
        passwordS.setText(item.getPass());
        tokenS.setText(item.getToken());
        tokenNameS.setText(item.getToken_name());
    }
}
