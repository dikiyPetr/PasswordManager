package com.example.dikiy.passwordmain;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.DBase.DBWorker;
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
    EditText   etUrl, etLogin, etPassword, etname, etlog;
    FrameLayout refreshl;
AutoCompleteTextView addTag, addGroup;
    ImageView addTagB, addTagB2, editandclose, acept, copyurl, copylogin, copypassword;
    boolean stat = false;
    String name = "";
    String url = "";
    String login = "";
    final Boolean tag=false,group=true;
    String password = "";
    String mode="";
    int folder=0;
    Button addpass;
    private List<RecyclerItem> movieListD = new ArrayList<>();
    private List<RecyclerItem> movieList2D = new ArrayList<>();
    String log = "";
    PasswordModel model;
    PasswordPresenter presenter;
    Intent thisIntent;
    DBWorker dbWorker=new DBWorker();
    Intent intent = new Intent();
    ArrayAdapter tagAdapter,groupAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,R.anim.flye);
        setContentView(R.layout.create_password_activity);
        thisIntent=this.getIntent();
        Log.v("123sda1223",thisIntent.getStringExtra("folder"));
       folder= Integer.parseInt(thisIntent.getStringExtra("folder"));
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
        refreshl= findViewById(R.id.progressl);



        addpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpass.setEnabled(false);
                List<String> tagList= new ArrayList<>();
                List<String> groupList = new ArrayList<>();
                for(int i=0;i<movieList.size();i++){
                    tagList.add(movieList.get(i).getName());
                }

                for(int i=0;i<movieList2.size();i++){
                    groupList.add(movieList2.get(i).getName());
                }

                presenter.createPass(etname.getText().toString(),folder,etUrl.getText().toString(),etPassword.getText().toString(),etLogin.getText().toString(),etlog.getText().toString(),tagList,groupList);
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
                Log.v("123123123aasc","123");
                if(password.equals(etPassword.getText().toString())||
                        url.equals(etUrl.getText().toString())||
                        name.equals(etname.getText().toString())||
                        login.equals(etLogin.getText().toString())||
                        log.equals(etlog.getText().toString())){
                    presenter.updatePass(Integer.valueOf(folder),etname.getText().toString(),etUrl.getText().toString(),etLogin.getText().toString(),etPassword.getText().toString());
                }
                modeEdit(0);
            }
        });
        copyurl.setOnClickListener(this);
        copylogin.setOnClickListener(this);
        copypassword.setOnClickListener(this);
        mAdapter = new RecyclerAdapter(movieList, new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                if(mode.equals("1")) {
                    presenter.removeTagOrGroup(s, folder, tag);
                }
                tagAdapter.add(s);
                tagAdapter.notifyDataSetChanged();
                addTag.setAdapter(tagAdapter);
            }
        });
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        addTag = findViewById(R.id.addtag);
        addTagB = findViewById(R.id.addtagb);
        addTagB.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if(addTag.getText().length()!=0) {
                                               if( addItemInRV(addTag.getText().toString(),movieList)) {
                                                   if(mode.equals("1")) {
                                                       presenter.addTagOrGroup(tag, addTag.getText().toString(), folder);
                                                   }
                                                   tagAdapter.remove(addTag.getText().toString());
                                               movie = new RecyclerItem(String.valueOf(addTag.getText()));
                                               movieList.add(movie);
                                               mAdapter.notifyDataSetChanged();
                                               recyclerView.scrollBy(1000000000, 1000000000);
                                               addTag.setText("");
                                           }}
                                       }
                                   }
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter2 = new RecyclerAdapter(movieList2, new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                if(mode.equals("1")) {
                    presenter.removeTagOrGroup(s, folder, group);
                }
                groupAdapter.add(s);
                groupAdapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        addGroup = findViewById(R.id.addgrup);
        addTagB2 = findViewById(R.id.addgrupb);
        addTagB2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(addGroup.getText().length()!=0) {
                                               if( addItemInRV(addGroup.getText().toString(),movieList2)) {
                                                   if(mode.equals("1")) {
                                                       presenter.addTagOrGroup(group, addGroup.getText().toString(), folder);
                                                   }
                                                   groupAdapter.remove(addGroup.getText().toString());
                                                   movie2 = new RecyclerItem(String.valueOf(addGroup.getText()));
                                                   movieList2.add(movie2);
                                                   mAdapter2.notifyDataSetChanged();
                                                   recyclerView2.scrollBy(1000000000, 1000000000);
                                                   addGroup.setText("");


                                               }
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
        presenter.viewIsReady(mode.equals("1"),Integer.valueOf(folder));
        Log.v("1231231231231sda","1");

        DBWorker dbWorker = new DBWorker();
        tagAdapter = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, dbWorker.getTag());
       groupAdapter= new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, dbWorker.getGroup());


        addTag.setAdapter(tagAdapter);
        addGroup.setAdapter(groupAdapter);
        addTag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(addTag.getText().length()==0){


                    addTag.showDropDown();}
                return false;
            }
        });
        addGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(addGroup.getText().length()==0){
                addGroup.showDropDown();}
                return false;
            }
        });
        if(mode.equals("1")){
         addpass.setVisibility(View.GONE);
        }else{
            refreshl.setVisibility(View.GONE);
            editandclose.setVisibility(View.GONE);
            copyurl.setVisibility(View.GONE);
            copylogin.setVisibility(View.GONE);
            copypassword.setVisibility(View.GONE);
            acept.setVisibility(View.GONE);
            setEnabled(true);
        }

    }

    private boolean addItemInRV(String text, List<RecyclerItem> list) {
        for(int i=0;i<list.size();i++){
            if(list.get(i).getName().equals(text)){
                return false;
            }

        }
        return true;
    }
    private boolean OpenFlyWindow(){

        if(Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this) ){

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1234);


            return false;
        }
        else
        {
            startService(new Intent(this,Fly.class));
            Log.v("123444","1");
        }
        finish();
        return true;
    }
    private void setEnabled(boolean b){
             etlog.setEnabled(b);
            etname.setEnabled(b);
        etPassword.setEnabled(b);
           etLogin.setEnabled(b);
             etUrl.setEnabled(b);
            addTag.setEnabled(b);
           addGroup.setEnabled(b);
    }
    public void loadDefault() {
        if(mode.equals("1")){


            Log.v("123sda1223", String.valueOf(folder));
            GetPass_Item item= dbWorker.getPass(folder);
            name=item.getName();
            url=item.getUrl();
            login=item.getLogin();
            log=item.getDescription();
            password=dbWorker.getPassword(folder);
            if(!item.getGroups().get(0).equals("")) {
                List<String> nameG=new ArrayList<>();
                nameG.addAll(dbWorker.getGroupName(item.getGroups()));
                for(int i=0;i<nameG.size();i++){
                    Log.v("123123axzcass",nameG.get(i));
                    movieList2.add(new RecyclerItem(nameG.get(i)));

                    movieList2D.add(new RecyclerItem(nameG.get(i)));
                }
            }
            if(!item.getTags().get(0).equals("")) {
                List<String> nameT=new ArrayList<>();
                nameT.addAll(dbWorker.getTagName(item.getTags()));
                for(int i=0;i<nameT.size();i++){
                    Log.v("123123axzcass",nameT.get(i));
                    movieList.add(new RecyclerItem(nameT.get(i)));

                    movieListD.add(new RecyclerItem(nameT.get(i)));
                }
            }}else{
            mAdapter2.switchMode();
            mAdapter.switchMode();
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
         addGroup.setEnabled(i);
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
            addGroup.setText("");
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

        setResult(RESULT_OK, intent);
        finish();
    }

    public void loadPass(String pass) {

        refreshl.setVisibility(View.GONE);
        password=pass;
        etPassword.setText(pass);

    }

    public void callError(String s) {
        refreshl.setVisibility(View.GONE);
        if(!s.equals("")) {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
    }


    public void setRefreshStatus() {
        setResult(RESULT_OK, intent);
    }
}

