package com.example.dikiy.passwordmain;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dikiy.passwordmain.Model.AddServiceModel;
import com.example.dikiy.passwordmain.Presenters.AddServicePresenter;

public class AddServiceActivity extends AppCompatActivity {
    FrameLayout progresS;
    RelativeLayout toolbarS;
    EditText nameS,urlS,loginS,passwordS,tokenS,tokenNameS;
    Button createS;
    AddServicePresenter presenter;
    TextInputLayout nameTils,urlTils,tokenTils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        init();
    }

    private void init() {
        progresS=findViewById(R.id.progresS);
    nameTils=findViewById(R.id.nameTILS);
    urlTils=findViewById(R.id.urlTILS);
    tokenTils=findViewById(R.id.tokenTILS);
    toolbarS=findViewById(R.id.toolbarS);
    nameS=findViewById(R.id.nameS);
    urlS=findViewById(R.id.urlS);
    loginS=findViewById(R.id.loginS);
    passwordS=findViewById(R.id.passwordS);
    tokenS=findViewById(R.id.tokenS);
    tokenNameS=findViewById(R.id.tokenNameS);
    createS=findViewById(R.id.createS);

    AddServiceModel usersModel = new AddServiceModel();
    presenter = new AddServicePresenter(usersModel);
    presenter.attachView(this);

    createS.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isCorrectName()&& isCorrectUrl()&& isCorrectToken()){
                createS.setEnabled(false);
                progresS.setVisibility(View.VISIBLE);
        presenter.createService(nameS.getText().toString(),urlS.getText().toString(),loginS.getText().toString(),passwordS.getText().toString(),tokenS.getText().toString(),tokenNameS.getText().toString());
            }
        }
    });
    }
    private boolean isCorrectName(){
        if(nameS.getText().toString().length()!=0){
            return true;
        }
        nameTils.setError("incorrect name");
        return false;
    }
    private boolean isCorrectUrl(){
        if(urlS.getText().toString().length()!=0){
            return true;
        }
        urlTils.setError("incorrect url");
        return false;
    }
    private boolean isCorrectToken(){
        if(tokenS.getText().toString().length()!=0){
            return true;
        }
        tokenTils.setError("incorrect token");
        return false;
    }

    public void fail() {
        createS.setEnabled(true);
        progresS.setVisibility(View.GONE);
        Toast.makeText(this,"no connect to server",Toast.LENGTH_SHORT).show();
    }

    public void error() {
        createS.setEnabled(true);
        progresS.setVisibility(View.GONE);
        Toast.makeText(this,"Server error",Toast.LENGTH_SHORT).show();
    }

    public void execute() {
        progresS.setVisibility(View.GONE);
        setResult(RESULT_OK, getIntent());
        finish();
    }
}
