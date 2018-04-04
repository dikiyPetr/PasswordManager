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
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    final String modeEdit="1";
    final String modeCreate="0";
    private MainPresenter presenter;
    private List<String> way =new ArrayList<>();
    private boolean modeSelect=false;
    private RelativeLayout rlSelect;
    private ImageView moveItems,deleteItems;

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
        moveItems=findViewById(R.id.moveItems);
        deleteItems=findViewById(R.id.deleteItems);

        rlSelect=findViewById(R.id.rlselect);

        moveItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        deleteItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<items.size();i++){
                    if(items.get(i).getStat()){
                        presenter.deleteItem(items.get(i).getId(),items.get(i).getType());
                        items.get(i).switchStat();
                    }
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });


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
        //folder
        fButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FolderActivity.class);
                intent.putExtra("folder","1");
                intent.putExtra("mode",modeCreate);
                startActivity(intent);
            }
        });
        //user
        fButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,UserActivity.class);
                intent.putExtra("folder","1");
                intent.putExtra("mode",modeCreate);
                startActivity(intent);
            }
        });
        //password
        fButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PasswordActivity.class);
                intent.putExtra("folder","1");
                intent.putExtra("mode",modeCreate);
                startActivity(intent);}
        });
        headersearchView.setOnQueryTextFocusChangeListener(this);
        cloak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSearch();}
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {presenter.refreshUsers();}
        });

        //корень
           placeV.setText("C");

        MainModel usersModel = new MainModel();
        presenter = new MainPresenter(usersModel);
        presenter.attachView(this);
        presenter.viewIsReady();
        recyclerViewAdapter = new RecyclerViewAdapter(null);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this,recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if(modeSelect){
                            modeSelect=recyclerViewAdapter.selectItem(position);

                            switchToolbar();
                        }else {

                            Log.v("itemClick", String.valueOf(position));
                            if (position != -1 && items.get(position).getType()) {
                                presenter.nextWay(items.get(position).getId());
                                changeWay(items.get(position).getName());
                            }
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {
//                        showPopupMenu(view,position);

                        modeSelect=recyclerViewAdapter.selectItem(position);
                        switchToolbar();
                    }
                }));



//        recyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position, MotionEvent event) {
//                        Log.v("1234555",position+" "+event.getAction());
//                        if(position!=0) {
//                            if(OpenFlyWindow()) {
//                                Uri address = Uri.parse("http://yandex.ru");
//                                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
//                                startActivity(openlink);
//                                MainActivity.super.finish();
//                            }
//                        }
//                    }
//                }));
    }

//    private void showPopupMenu(View v, final int p) {
//        PopupMenu popupMenu = new PopupMenu(this, v);
//        popupMenu.inflate(R.menu.popupmenu); // Для Android 4.0
//        // для версии Android 3.0 нужно использовать длинный вариант
//        // popupMenu.getMenuInflater().inflate(R.menu.popupmenu,
//        // popupMenu.getMenu());
//
//        popupMenu
//                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        // Toast.makeText(PopupMenuDemoActivity.this,
//                        // item.toString(), Toast.LENGTH_LONG).show();
//                        // return true;
//                        switch (item.getItemId()) {
//
//                            case R.id.menu1:
//                                Toast.makeText(getApplicationContext(),
//                                        "delete item...",
//                                        Toast.LENGTH_SHORT).show();
//                                presenter.deleteItem(items.get(p).getId(),items.get(p).getType());
//                                return true;
//                            case R.id.menu2:
//                                Intent intent=new Intent(MainActivity.this,UserActivity.class);
//                                intent.putExtra("folder","1");
//                                intent.putExtra("mode",modeCreate);
//                                startActivity(intent);
//                                return true;
//                            default:
//                                return false;
//                        }
//                    }
//                });
//
//
//        popupMenu.show();
//    }
    public void switchToolbar(){
        if(modeSelect) {

            rlSelect.setVisibility(View.VISIBLE);
        }else{

            rlSelect.setVisibility(View.GONE);

        }
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
        placeV.setVisibility(View.VISIBLE);
        cloak.setVisibility(View.GONE);
        fButton1.setVisibility(View.VISIBLE);
    }
    private void openSearch(){
        placeV.setVisibility(View.GONE);
        cloak.setVisibility(View.VISIBLE);
        fButton1.setVisibility(View.GONE);
    }
    private void changeWay(String s){
     placeV.setText(placeV.getText()+"/"+s);
    }
    private void backWay(){
        Log.v("test","123");
        String s=placeV.getText().toString();
        s=s.substring(0,s.lastIndexOf('/'));
        placeV.setText(s);
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
        }else if(presenter.backWay())
        {
        backWay();
        }else{
            super.onBackPressed();
        }
    }
    public void fail(){

        refreshLayout.setRefreshing(false);
        Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
    }
    public void showUsers(List<MainItem> users) {
        refreshLayout.setRefreshing(false);


        if(users!=null && users.size()!=0) {

            items = new ArrayList<>();
            items.addAll(users);

            recyclerViewAdapter = new RecyclerViewAdapter(items);
            recyclerView.setAdapter(recyclerViewAdapter);
        }else{
            items = new ArrayList<>();
            recyclerViewAdapter = new RecyclerViewAdapter(items);
            recyclerView.setAdapter(recyclerViewAdapter);
            Toast.makeText(this,"empty",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteError() {
        Toast.makeText(this,"folder is not empty",Toast.LENGTH_SHORT).show();
    }
}