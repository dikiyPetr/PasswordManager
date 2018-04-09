package com.example.dikiy.passwordmain;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

import com.example.dikiy.passwordmain.ItemModel.PasswordList;
import com.example.dikiy.passwordmain.Model.PasswordModel;
import com.example.dikiy.passwordmain.Presenters.PasswordPresenter;
import com.example.dikiy.passwordmain.RecyclerView.RecyclerAdapter;
import com.example.dikiy.passwordmain.RecyclerView.RecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 16.02.2018.
 */

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private List<RecyclerItem> movieList = new ArrayList<>();

    private List<RecyclerItem> movieList2 = new ArrayList<>();
    private RecyclerView recyclerView, recyclerView2;
    private RecyclerAdapter mAdapter, mAdapter2;
    private RecyclerItem movie, movie2;
    EditText addTag, addTag2, etUrl, etLogin, etPassword, etname, etlog;
    ImageView addTagB, addTagB2, editandclose, acept, copyurl, copylogin, copypassword;
    boolean stat = false;
    String name = "";
    String url = "";
    String login = "";
    String password = "";
    String mode;
    String folder="0";
    Button addpass;
    private List<RecyclerItem> movieListD = new ArrayList<>();
    private List<RecyclerItem> movieList2D = new ArrayList<>();
    String log = "";
    PasswordModel model;
    PasswordPresenter presenter;
    Intent thisIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,R.anim.flye);
        setContentView(R.layout.create_password_activity);
        thisIntent=this.getIntent();
       folder= thisIntent.getStringExtra("folder");
       mode= thisIntent.getStringExtra("mode");





        init();
        loadDefault();
    }
    private void init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView2 = findViewById(R.id.rv2);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        editandclose = findViewById(R.id.editandclose);
        acept = findViewById(R.id.acept);
        copyurl = findViewById(R.id.copyurl);
        copylogin = findViewById(R.id.copylogin);
        addpass = findViewById(R.id.addpass);
        copypassword = findViewById(R.id.copypassword);
        etUrl = findViewById(R.id.eturl);
        etLogin = findViewById(R.id.etlogin);
        etPassword = findViewById(R.id.etpassword);
        etname = findViewById(R.id.etname);
        etlog = findViewById(R.id.etlog);




        addpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpass.setEnabled(false);
                presenter.createPass(etname.getText().toString(),folder,etUrl.getText().toString(),etPassword.getText().toString(),etLogin.getText().toString(),etlog.getText().toString());
            }
        });

        editandclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeEdit(1);
            }
        });
        acept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeEdit(0);
            }
        });
        copyurl.setOnClickListener(this);
        copylogin.setOnClickListener(this);
        copypassword.setOnClickListener(this);
        mAdapter = new RecyclerAdapter(movieList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        addTag = findViewById(R.id.addtag);
        addTagB = findViewById(R.id.addtagb);
        addTagB.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if(addTag.getText().length()!=0) {
                                               movie = new RecyclerItem(String.valueOf(addTag.getText()));
                                               movieList.add(movie);
                                               mAdapter.notifyDataSetChanged();
                                               recyclerView.scrollBy(1000000000, 1000000000);
                                               addTag.setText("");
                                           }
                                       }
                                   }
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter2 = new RecyclerAdapter(movieList2);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        addTag2 = findViewById(R.id.addgrup);
        addTagB2 = findViewById(R.id.addgrupb);
        addTagB2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(addTag2.getText().length()!=0) {
                                                movie2 = new RecyclerItem(String.valueOf(addTag2.getText()));

                                                movieList2.add(movie2);
                                                mAdapter2.notifyDataSetChanged();
                                                recyclerView2.scrollBy(1000000000, 1000000000);
                                                addTag2.setText("");
                                            }
                                        }
                                    }
        );

        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(mAdapter2);
        setEnabled(false);
        PasswordModel usersModel = new PasswordModel();
        presenter = new PasswordPresenter(usersModel);
        presenter.attachView(this);
        presenter.viewIsReady();

        if(mode.equals("1")){
         addpass.setVisibility(View.GONE);
        }else{
            editandclose.setVisibility(View.GONE);
            copyurl.setVisibility(View.GONE);
            copylogin.setVisibility(View.GONE);
            copypassword.setVisibility(View.GONE);
            acept.setVisibility(View.GONE);
            setEnabled(true);
        }

    }

    private void setEnabled(boolean b){
             etlog.setEnabled(b);
            etname.setEnabled(b);
        etPassword.setEnabled(b);
           etLogin.setEnabled(b);
             etUrl.setEnabled(b);
            addTag.setEnabled(b);
           addTag2.setEnabled(b);
    }
    public void loadDefault() {
        if(mode.equals("1")){

        }
        etname.setText(name);
        etUrl.setText(url);
        etLogin.setText(login);
        etPassword.setText(password);
        etlog.setText(log);
        movieList2.clear();
        movieList2.addAll(movieList2D);
        movieList.clear();
        movieList.addAll(movieListD);
        mAdapter2.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }
    public void saveDefault(){
        movieList2D.clear();
        movieList2D.addAll(movieList2);
        movieListD.clear();
        movieListD.addAll(movieList);

    }
    private void SwitchEdit(boolean i){
        etlog.setEnabled(i);
        etname.setEnabled(i);
        etPassword.setEnabled(i);
         etLogin.setEnabled(i);
           etUrl.setEnabled(i);
          addTag.setEnabled(i);
         addTag2.setEnabled(i);
         addTagB.setEnabled(i);
        addTagB2.setEnabled(i);
    }
    public void modeEdit(int i) {
        if (!stat) {
            acept.setVisibility(View.VISIBLE);
            editandclose.setImageResource(android.R.drawable.ic_delete);
            mAdapter.switchMode();
            mAdapter2.switchMode();
            mAdapter.notifyDataSetChanged();
            mAdapter2.notifyDataSetChanged();
       SwitchEdit(true);
            stat = true;
        } else {

            acept.setVisibility(View.GONE);
            editandclose.setImageResource(android.R.drawable.ic_menu_edit);
            mAdapter.switchMode();
            mAdapter2.switchMode();
            mAdapter.notifyDataSetChanged();
            mAdapter2.notifyDataSetChanged();
            SwitchEdit(false);
            addTag.setText("");
            addTag2.setText("");
            if (i==1) {
                loadDefault();
            } else {
                name = String.valueOf(etname.getText());
                url = String.valueOf(etUrl.getText());
                login = String.valueOf(etLogin.getText());
                password = String.valueOf(etPassword.getText());
                mAdapter.notifyDataSetChanged();
                mAdapter2.notifyDataSetChanged();
                saveDefault();
            }
            stat = false;
        }
    }

    public void copy(String s) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("123", s);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "copy", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copyurl:
                copy(String.valueOf(etUrl.getText()));
                break;
            case R.id.copylogin:
                copy(String.valueOf(etLogin.getText()));
                break;
            case R.id.copypassword:
                copy(String.valueOf(etPassword.getText()));
                break;
        }


    }

    public void showlist1(PasswordList list) {

    }
    public void showlist2(PasswordList list) {

    }


    public void execute() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}

