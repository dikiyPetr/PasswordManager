package com.example.dikiy.passwordmain;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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

import com.example.dikiy.passwordmain.Model.MainModel;
import com.example.dikiy.passwordmain.Presenters.MainPresenter;
import com.example.dikiy.passwordmain.ItemModel.MainItem;
import com.example.dikiy.passwordmain.MainRecycler.RecyclerItemClickListener;
import com.example.dikiy.passwordmain.MainRecycler.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 16.02.2018.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnFocusChangeListener {
    private SearchView headersearchView;
    private TextView placeV;
    private DrawerLayout drawer;
    private RecyclerView recyclerView;
    private List<MainItem> items = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;
    private FloatingActionButton fButton1,fButton2,fButton3,fButton4;
    private ImageView cloak;
    private SwipeRefreshLayout refreshLayout;
    private static final String place ="C/123/...";
    private MainPresenter presenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        overridePendingTransition(0,R.anim.flye);
        setContentView(R.layout.main_activity);
        Log.v("1231","123");
        init();
    }
    private void init(){
        Log.v("1231","123");
        headersearchView = findViewById(R.id.mainserch);
        placeV = findViewById(R.id.placev);
        fButton1= findViewById(R.id.floatingActionButton);
        fButton2= findViewById(R.id.floatingActionButton2);
        fButton3= findViewById(R.id.floatingActionButton3);
        fButton4= findViewById(R.id.fbaddpassword);
        cloak=findViewById(R.id.cloak);
        refreshLayout=findViewById(R.id.refresh);
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
        fButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickFabB(fButton2.getVisibility());
            }
        });
        fButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(new Intent(MainActivity.this,PasswordActivity.class));}
        });
        headersearchView.setOnQueryTextFocusChangeListener(this);
        cloak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSearch();}
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {refreshLayout.setRefreshing(false);}
        });
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, MotionEvent event) { }})
        );


        MainModel usersModel = new MainModel();
        presenter = new MainPresenter(usersModel);
        presenter.attachView(this);
        presenter.viewIsReady();

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
                                MainActivity.super.finish();
                            }
                        }
                    }
                }));
    }

    private boolean OpenFlyWindow(){

        if(Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(MainActivity.this) ){

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1234);


            return false;
        }
        else
        {
            startService(new Intent(MainActivity.this,Fly.class));
            Log.v("123444","1");
        }
//        finish();
        return true;
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

    private void ClickFabB(int i) {
        if(i==View.VISIBLE){
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
    }else{
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.fbuttons);
            fButton2.setVisibility(View.VISIBLE);
            fButton3.setVisibility(View.VISIBLE);
            fButton4.setVisibility(View.VISIBLE);
            fButton2.startAnimation(anim);
            fButton3.startAnimation(anim);
            fButton4.startAnimation(anim);

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            openSearch();
        }else{
            closeSearch();
        }
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

    public void showUsers(List<MainItem> users) {
        if(users!=null && users.size()!=0) {

            items = new ArrayList<>();
            items.addAll(users);

            recyclerViewAdapter = new RecyclerViewAdapter(items);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }
}