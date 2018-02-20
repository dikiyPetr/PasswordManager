package com.example.dikiy.passwordmain;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dikiy.passwordmain.RecyclerView.RecyclerAdapter;
import com.example.dikiy.passwordmain.RecyclerView.RecyclerItem;

import java.util.ArrayList;
import java.util.List;

public class CreatePassword extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private List<RecyclerItem> movieList = new ArrayList<>();
    private List<RecyclerItem> movieList2 = new ArrayList<>();
    private RecyclerView recyclerView,recyclerView2;
    private RecyclerAdapter mAdapter,mAdapter2;
    private RecyclerItem movie,movie2;
    EditText addTag,addTag2,etUrl,etLogin,etPassword,etname,etlog;
    ImageView addTagB,addTagB2,changeIc,acceptIc,copyurl,copylogin,copypassword;
    boolean stat=false;
    private DrawerLayout drawer;
    String name="name";
    String url="url";
    String login="login";
    String password="password";
    private List<RecyclerItem> movieListD = new ArrayList<>();
    private List<RecyclerItem> movieList2D = new ArrayList<>();
    String log="log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_password_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView2= findViewById(R.id.rv2);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        changeIc = findViewById(R.id.editandclose);
        acceptIc = findViewById(R.id.acept);
        copyurl = findViewById(R.id.copyurl);
        copylogin= findViewById(R.id.copylogin);
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
                                           if(addTag.getText().length()!=0) {
                                               movie = new RecyclerItem(String.valueOf(addTag.getText()));
                                               movieList.add(movie);
                                               mAdapter.notifyDataSetChanged();
                                               recyclerView.scrollBy(1000000000, 1000000000);
                                               addTag.setText("");
                                           }                                   }
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
                                           }                                   }
                                   }
        );
        movie2 = new RecyclerItem(String.valueOf("tag"));
        movieList2.add(movie2);
        movieList.add(movie2);
        movieList2D.add(movie2);
        movieListD.add(movie2);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(mAdapter2);
        etlog.setEnabled(false);
        etname.setEnabled(false);
        etPassword.setEnabled(false);
        etLogin.setEnabled(false);
        etUrl.setEnabled(false);
        addTag.setEnabled(false);
        addTag2.setEnabled(false);
        loadDefault();

    }
    public void loadDefault(){
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
    public void modeEdit(int i){
        if(!stat){
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

            stat=true;
        }else{

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
if(i==R.id.editandclose){
    loadDefault();

}else{
    name= String.valueOf(etname.getText());
    url = String.valueOf(etUrl.getText());
    login= String.valueOf(etLogin.getText());
    password= String.valueOf(etPassword.getText());
//    movieListD=movieList;
//    movieList2D=movieList2;
    mAdapter.notifyDataSetChanged();
    mAdapter2.notifyDataSetChanged();

}
            stat=false;
        }
    }
    public void copy(String s){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("123", s);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this,"copy",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {
      switch (v.getId()){
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}