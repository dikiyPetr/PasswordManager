package com.example.dikiy.passwordmain.Old;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dikiy.passwordmain.CreatePassword;
import com.example.dikiy.passwordmain.Fly;
import com.example.dikiy.passwordmain.MainRecycler.MainItem;
import com.example.dikiy.passwordmain.MainRecycler.RecyclerItemClickListener;
import com.example.dikiy.passwordmain.MainRecycler.RecyclerViewAdapter;
import com.example.dikiy.passwordmain.R;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnFocusChangeListener, SearchView.OnQueryTextListener, View.OnClickListener {
    SearchView headersearchView;
    TextView placeV;
    DrawerLayout drawer;
    RecyclerView recyclerView;
    List<MainItem> items;
    RecyclerViewAdapter recyclerViewAdapter;
    FloatingActionButton fButton1,fButton2,fButton3,fButton4;
    ImageView cloak;
    SwipeRefreshLayout refreshLayout;
    private static final String place ="C/123/...";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        headersearchView = findViewById(R.id.mainserch);
        placeV = findViewById(R.id.placev);
        fButton1= findViewById(R.id.floatingActionButton);
        fButton2= findViewById(R.id.floatingActionButton2);
        fButton3= findViewById(R.id.floatingActionButton3);
        fButton4= findViewById(R.id.fbaddpassword);
        cloak=findViewById(R.id.cloak);
        refreshLayout=findViewById(R.id.refresh);

        fButton1.setOnClickListener(this);
        fButton2.setOnClickListener(this);
        fButton3.setOnClickListener(this);
        fButton4.setOnClickListener(this);
        cloak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          closeSearch();}
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });

        headersearchView.setOnQueryTextListener(this);
        headersearchView.setOnQueryTextFocusChangeListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView=(RecyclerView)findViewById(R.id.rv);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, MotionEvent event) {
                        Log.v("1234555",position+" "+event.getAction());
                        if(position!=0) {
                            if(OpenFlyWindow()) {
                                Uri address = Uri.parse("http://yandex.ru");
                                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                                startActivity(openlink);
                                Main2Activity.super.finish();
                            }
                }
        }
    }));
    initializeData();

}
    private void initializeData(){
            items = new ArrayList<>();
            items.add(new MainItem("folder","","",0,R.drawable.folder));
            items.add(new MainItem("folder","","",0,R.drawable.folder));
            items.add(new MainItem("folder","","",0,R.drawable.folder));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));





        recyclerViewAdapter = new RecyclerViewAdapter(items);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    private void closeSearch(){
        headersearchView.onActionViewCollapsed();
        placeV.setText(place);
        cloak.setVisibility(View.GONE);
    }
    private void openSearch(){
        placeV.setText("");
        cloak.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    private boolean OpenFlyWindow(){

        if(Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(Main2Activity.this) ){

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1234);


            return false;
        }
        else
        {
            startService(new Intent(Main2Activity.this,Fly.class));
            Log.v("123444","1");
        }
        finish();
        return true;
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus){
            closeSearch();
        }else{
            openSearch();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onBackPressed() {


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(headersearchView.isFocused()) {
            closeSearch();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingActionButton:
                if(fButton2.getVisibility()==View.GONE) {
                    fButton2.setVisibility(View.VISIBLE);
                    fButton3.setVisibility(View.VISIBLE);
                    fButton4.setVisibility(View.VISIBLE);
                    Animation anim = AnimationUtils.loadAnimation(this, R.anim.fbuttons);
                    fButton2.startAnimation(anim);

                    fButton3.startAnimation(anim);

                    fButton4.startAnimation(anim);


                }else{

                    Animation anim = AnimationUtils.loadAnimation(this, R.anim.fbuttone);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            fButton2.setVisibility(View.GONE);
                            fButton3.setVisibility(View.GONE);
                            fButton4.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    fButton2.startAnimation(anim);
                    fButton3.startAnimation(anim);
                    fButton4.startAnimation(anim);
                }
                break;
            case R.id.floatingActionButton2:
                break;
            case R.id.floatingActionButton3:
                break;
            case R.id.fbaddpassword:
                startActivity(new Intent(this,CreatePassword.class));
                break;
        }
    }
}
