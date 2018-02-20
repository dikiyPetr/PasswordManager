package com.example.dikiy.passwordmain;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dikiy.passwordmain.MainRecycler.MainItem;
import com.example.dikiy.passwordmain.PasswordActivityPac.PasswordList;
import com.example.dikiy.passwordmain.PasswordActivityPac.PasswordModel;
import com.example.dikiy.passwordmain.PasswordActivityPac.PasswordPresenter;
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
    ImageView addTagB, addTagB2, changeIc, acceptIc, copyurl, copylogin, copypassword;
    boolean stat = false;
    String name = "name";
    String url = "url";
    String login = "login";
    String password = "password";
    private List<RecyclerItem> movieListD = new ArrayList<>();
    private List<RecyclerItem> movieList2D = new ArrayList<>();
    String log = "log";
    PasswordModel model;
    PasswordPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_password_activity);

        init();

    }
    private void init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView2 = findViewById(R.id.rv2);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        changeIc = findViewById(R.id.editandclose);
        acceptIc = findViewById(R.id.acept);
        copyurl = findViewById(R.id.copyurl);
        copylogin = findViewById(R.id.copylogin);
        copypassword = findViewById(R.id.copypassword);
        etUrl = findViewById(R.id.eturl);
        etLogin = findViewById(R.id.etlogin);
        etPassword = findViewById(R.id.etpassword);
        etname = findViewById(R.id.etname);
        etlog = findViewById(R.id.etlog);
        changeIc.setOnClickListener(this);
        acceptIc.setOnClickListener(this);
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

                                        }
                                    }
        );
        movie2 = new RecyclerItem(String.valueOf("tag"));
        movieList2.add(movie2);
        movieList.add(movie2);
        movieList2D.add(movie2);
        movieListD.add(movie2);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(mAdapter2);
        setEnabled(false);
        PasswordModel usersModel = new PasswordModel();
        presenter = new PasswordPresenter(usersModel);
        presenter.attachView(this);
        presenter.viewIsReady();
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

    }

    public void modeEdit(int i) {
        if (!stat) {
            acceptIc.setVisibility(View.VISIBLE);
            changeIc.setImageResource(android.R.drawable.ic_delete);
            mAdapter.switchMode();
            mAdapter2.switchMode();
            mAdapter.notifyDataSetChanged();
            mAdapter2.notifyDataSetChanged();
            etlog.setEnabled(true);
            etname.setEnabled(true);
            etPassword.setEnabled(true);
            etLogin.setEnabled(true);
            etUrl.setEnabled(true);
            addTag.setEnabled(true);
            addTag2.setEnabled(true);
            addTagB.setEnabled(true);
            addTagB2.setEnabled(true);

            stat = true;
        } else {

            acceptIc.setVisibility(View.GONE);
            changeIc.setImageResource(android.R.drawable.ic_menu_edit);
            mAdapter.switchMode();
            mAdapter2.switchMode();
            mAdapter.notifyDataSetChanged();
            mAdapter2.notifyDataSetChanged();
            etlog.setEnabled(false);
            etname.setEnabled(false);
            etPassword.setEnabled(false);
            etLogin.setEnabled(false);
            etUrl.setEnabled(false);
            addTag.setEnabled(false);
            addTag2.setEnabled(false);
            addTagB.setEnabled(false);
            addTagB2.setEnabled(false);
            addTag.setText("");
            addTag2.setText("");
            if (i == R.id.editandclose) {
                loadDefault();

            } else {
                name = String.valueOf(etname.getText());
                url = String.valueOf(etUrl.getText());
                login = String.valueOf(etLogin.getText());
                password = String.valueOf(etPassword.getText());
                mAdapter.notifyDataSetChanged();
                mAdapter2.notifyDataSetChanged();

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
            default:
                modeEdit(v.getId());
                break;
        }


    }

    public void showlist1(PasswordList list) {

    }
    public void showlist2(PasswordList list) {

    }
}

