package com.example.dikiy.passwordmain;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.dikiy.passwordmain.Model.AddServiceCommandModel;
import com.example.dikiy.passwordmain.Presenters.AddServiceCommandPresenter;

public class AddServiceCommandActivity extends AppCompatActivity {
    TextInputLayout nameTil,commandTil,methodTil,paramsTil,templateTil;
    EditText name,command,method,params,template;
    Button create;
    FrameLayout progress;
    AddServiceCommandPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_command);

        init();
    }

    private void init() {
            nameTil=findViewById(R.id.nameTILSC);
            commandTil=findViewById(R.id.commandTILSC);
            methodTil=findViewById(R.id.methodTILSC);
            paramsTil=findViewById(R.id.paramsTILSC);
            templateTil=findViewById(R.id.templateTILSC);
            name=findViewById(R.id.nameSC);
            command=findViewById(R.id.commandSC);
            params=findViewById(R.id.paramsSC);
            method=findViewById(R.id.methodSC);
            template=findViewById(R.id.templateSC);
            create=findViewById(R.id.createSC);
            progress=findViewById(R.id.progresSC);
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(isNameValid(name.getText().toString())&&isCommandValid(command.getText().toString())&&isMethodValid(method.getText().toString())){
                    create.setEnabled(false);
                    progress.setVisibility(View.VISIBLE);
                    presenter.createCommand(name.getText().toString(),getIntent().getStringExtra("id"),command.getText().toString(),method.getText().toString(),params.getText().toString(),template.getText().toString());
                }
                }
            });

        AddServiceCommandModel model = new AddServiceCommandModel();
        presenter = new AddServiceCommandPresenter(model);
        presenter.attachView(this);
    }
    public boolean isNameValid(String s){
        nameTil.setError(null);
        if(s.length()==0){
            nameTil.setError(getString(R.string.empty));
        }
        return s.length()!=0;
    }
    public boolean isCommandValid(String s){
       commandTil.setError(null);
        if(s.length()==0){
            commandTil.setError(getString(R.string.empty));
        }
        return s.length()!=0;
    }
    public boolean isMethodValid(String s){
        methodTil.setError(null);
        if(s.length()==0){
            methodTil.setError(getString(R.string.empty));
        }
        return s.length()!=0;
    }

    public void fail() {
        create.setEnabled(true);
        progress.setVisibility(View.GONE);
        Toast.makeText(this,"no connect to server",Toast.LENGTH_SHORT).show();
    }

    public void error() {
        create.setEnabled(true);
        progress.setVisibility(View.GONE);
        Toast.makeText(this,"Server error",Toast.LENGTH_SHORT).show();
    }

    public void execute() {
        progress.setVisibility(View.GONE);
        setResult(RESULT_OK, getIntent());
        finish();
    }
}
