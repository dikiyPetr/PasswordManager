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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dikiy.passwordmain.Adapters.Model.CutItem;
import com.example.dikiy.passwordmain.Adapters.Model.FlyVItem;
import com.example.dikiy.passwordmain.Adapters.Model.SearchItem;
import com.example.dikiy.passwordmain.DBase.DBWorker;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Model.MainModel;

import com.example.dikiy.passwordmain.Presenters.MainPresenter;
import com.example.dikiy.passwordmain.ItemModel.MainItem;
import com.example.dikiy.passwordmain.MainRecycler.RecyclerItemClickListener;
import com.example.dikiy.passwordmain.MainRecycler.RecyclerViewAdapter;
import com.example.dikiy.passwordmain.SearchRecycler.RVSearchAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 16.02.2018.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnFocusChangeListener {
    private android.widget.SearchView headersearchView;
    private TextView placeV,sizeselect,pasteState;
    private DrawerLayout drawer;
    private RecyclerView recyclerView,rvSearch;
    private List<MainItem> items = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;
    private RVSearchAdapter searchViewAdapter;
    private FloatingActionButton fButton1,fButton2,fButton3,fButton4;
    private ImageView  closeSelect;
    private SwipeRefreshLayout refreshLayout;
    final String    modeEdit="1";
    final String modeCreate="0";
    private MainPresenter presenter;
    private List<String> way =new ArrayList<>();
    private boolean modeSelect=false;
    private RelativeLayout rlSelect;
    private ImageView moveItems, menuSelect,pasteI,closeP;
    private RelativeLayout ll,fLayout,cloak,pasteL;
    private List<SearchItem> searchItems= new ArrayList<>();
    private FrameLayout progressl;
    FlyVItem flyVItem=null;
    private List<CutItem> cutItems=new ArrayList<>();
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
        pasteState=findViewById(R.id.pasteStat);
        closeP=findViewById(R.id.closeP);
        sizeselect=findViewById(R.id.sizeselect);
        closeSelect = findViewById(R.id.closeselect);
        placeV = findViewById(R.id.placev);
        pasteI= findViewById(R.id.paste);
        pasteL= findViewById(R.id.pl);
        fButton1= findViewById(R.id.floatingActionButton);
        fButton2= findViewById(R.id.floatingActionButton2);
        fButton3= findViewById(R.id.floatingActionButton3);
        fButton4= findViewById(R.id.fbaddpassword);

        menuSelect =findViewById(R.id.menus);
        ll=findViewById(R.id.ll);
        rvSearch=findViewById(R.id.searchRV);
        progressl=findViewById(R.id.progressl);
        fLayout=findViewById(R.id.fLayout);
        rlSelect=findViewById(R.id.rl);
        headersearchView = findViewById(R.id.mainserch);

        closeP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToolbar(0);
            }
        });
        headersearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>=1) {
                    searchItems = new ArrayList<>();
                    searchItems.addAll(LoadPasswordList(newText));
                    searchViewAdapter = new RVSearchAdapter(searchItems);
                    searchViewAdapter.notifyDataSetChanged();
                    rvSearch.setAdapter(searchViewAdapter);
                    Log.v("12zxcdadads", newText);
                }else{
                    searchItems = new ArrayList<>();
                    searchViewAdapter = new RVSearchAdapter(searchItems);
                    searchViewAdapter.notifyDataSetChanged();
                    rvSearch.setAdapter(searchViewAdapter);
                }  return false;
            }
        });
        pasteI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.moveItem(cutItems,presenter.getWay());
                switchToolbar(0);
                cutItems=new ArrayList<>();
            }
        });
//        moveItems.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cutItems=new ArrayList<>();
//                for(int i=0;i<items.size();i++){
//                    if(items.get(i).getStat()){
//                      cutItems.add(new CutItem(items.get(i).getId(),items.get(i).getType()));
//                        items.get(i).switchStat();
//                    }
//                }
//                recyclerViewAdapter.setSizeSelect(0);
//                modeSelect=false;
//                switchToolbar(2);
//                recyclerViewAdapter.notifyDataSetChanged();
//            }
//        });
        closeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("123sadadae","123");
                recyclerViewAdapter.setSizeSelect(0);
                modeSelect=false;
                switchToolbar(0);
            }
        });
        menuSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.inflate(R.menu.selectmenu);
                if(recyclerViewAdapter.getSizeSelect()==1){
                    popupMenu.getMenu().add(1,1,1,"edit");
                }
               // Для Android 4.0
                // для версии Android 3.0 нужно использовать длинный вариант
                // popupMenu.getMenuInflater().inflate(R.menu.popupmenu,
                // popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {

                                    case R.id.mD:
                                        for(int i=0;i<items.size();i++){
                                            if(items.get(i).getStat()){
                                                   presenter.deleteItem(items.get(i).getId(),items.get(i).getType());
                                                   items.get(i).switchStat();
                                               }
                                           }
                                           recyclerViewAdapter.setSizeSelect(0);
                                           modeSelect=false;
                                           switchToolbar(0);
                                           recyclerViewAdapter.notifyDataSetChanged();
                                        return true;
                                    case R.id.mM:
                                        cutItems=new ArrayList<>();
                                        for(int i=0;i<items.size();i++){
                                            if(items.get(i).getStat()){
                                                cutItems.add(new CutItem(items.get(i).getId(),items.get(i).getType()));
                                                items.get(i).switchStat();
                                            }
                                        }
                                        recyclerViewAdapter.setSizeSelect(0);
                                        modeSelect=false;
                                        switchToolbar(2);
                                        recyclerViewAdapter.notifyDataSetChanged();

                                        return true;
                                    case 1:
                                        int itemId=0;
                                        boolean itemType=false;
                                        for(int i=0;i<items.size();i++){
                                            if(items.get(i).getStat()){
                                               itemId=items.get(i).getId();
                                               itemType=items.get(i).getType();
                                               items.get(i).switchStat();
                                                recyclerViewAdapter.setSizeSelect(0);
                                                modeSelect=false;
                                                switchToolbar(0);
                                                recyclerViewAdapter.notifyDataSetChanged();
                                             break;
                                            }
                                        }
                                        Intent intent;
                                        Log.v("12312sadzcsd", String.valueOf(itemType));
                                        if(!itemType) {
                                            intent = new Intent(MainActivity.this, PasswordActivity.class);
                                        }else{
                                            intent = new Intent(MainActivity.this, FolderActivity.class);
                                        }
                                        Log.v("123sda1223", String.valueOf(itemId));
                                        intent.putExtra("folder",String.valueOf(itemId));
                                        intent.putExtra("mode",modeEdit);
                                        startActivityForResult(intent,1234);
                                        return true;
                                        default:
                                            return false;
                                }

                            }
                        });
                popupMenu.show();




//                for(int i=0;i<items.size();i++){
//                    if(items.get(i).getStat()){
//                        presenter.deleteItem(items.get(i).getId(),items.get(i).getType());
//                        items.get(i).switchStat();
//                    }
//                }
//                recyclerViewAdapter.setSizeSelect(0);
//                modeSelect=false;
//                switchToolbar(0);
//                recyclerViewAdapter.notifyDataSetChanged();
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


        RecyclerView.LayoutManager llmS = new LinearLayoutManager(this);
        rvSearch.setLayoutManager(llmS);
        rvSearch.setHasFixedSize(true);

        searchViewAdapter = new RVSearchAdapter(searchItems);
        rvSearch.setAdapter(searchViewAdapter);



        recyclerView=(RecyclerView)findViewById(R.id.rv);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        fButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickFabB();
            }
        });
        //folder
        fButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FolderActivity.class);
                Log.v("1231231231", String.valueOf(presenter.getWay()));
                intent.putExtra("folder",String.valueOf(presenter.getWay()));
                intent.putExtra("mode",modeCreate);
                startActivityForResult(intent,1);
            }
        });
        //user
        fButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,UserActivity.class);
                intent.putExtra("folder","1");
                intent.putExtra("mode",modeCreate);
                startActivityForResult(intent,1);
            }
        });
        //password
        fButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PasswordActivity.class);
                intent.putExtra("folder",String.valueOf(presenter.getWay()));
                intent.putExtra("mode",modeCreate);
                startActivityForResult(intent,1);
            }
        });
        headersearchView.setOnQueryTextFocusChangeListener(this);
        cloak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("313asas","1");
                closeSearch();}
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {presenter.refreshUsers();}
        });

        //корень
           placeV.setText("");

        MainModel usersModel = new MainModel();
        presenter = new MainPresenter(usersModel);
        presenter.attachView(this);
        presenter.viewIsReady();
        recyclerViewAdapter = new RecyclerViewAdapter(null);
        rvSearch.addOnItemTouchListener(new RecyclerItemClickListener(this,rvSearch, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.v("12zxcdadads", String.valueOf(position));
                        if(position!=-1){
                        Intent intent=new Intent(MainActivity.this,PasswordActivity.class);

                        intent.putExtra("folder",String.valueOf(searchItems.get(position).getId()));
                        intent.putExtra("mode",modeEdit);
                        startActivityForResult(intent,1234);
                    }}

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this,recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if(modeSelect){
                            int b=recyclerViewAdapter.selectItem(position);
                                if(b==0){
                                    modeSelect=false;
                                    switchToolbar(0);
                                }else{
                                    modeSelect=true;
                                }
                                Log.v("12312312sdsfs", String.valueOf(b));
                            sizeselect.setText("select "+b+" item(s)");

                        }else {

                            Log.v("itemClick", String.valueOf(position));
                            if (position != -1) {
                                if(items.get(position).getType()) {
                                    presenter.nextWay(items.get(position).getId());
                                    changeWay(items.get(position).getName());
                                }else{
                                    showPopupMenu(view,items.get(position).getId());

                                }
                            }
                        }
                        Log.d("way", String.valueOf(presenter.getWay()));
                    }

                    @Override
                    public void onLongClick(View view, int position) {
//                        showPopupMenu(view,position);
                        int b=recyclerViewAdapter.selectItem(position);
                        if(b==0){
                            modeSelect=false;
                            switchToolbar(0);
                        }else{
                            modeSelect=true;
                            switchToolbar(1);
                        }
                        sizeselect.setText("select "+b+" item(s)");

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

    private void showPopupMenu(View v, final int p) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu); // Для Android 4.0
        // для версии Android 3.0 нужно использовать длинный вариант
        // popupMenu.getMenuInflater().inflate(R.menu.popupmenu,
        // popupMenu.getMenu());

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Toast.makeText(PopupMenuDemoActivity.this,
                        // item.toString(), Toast.LENGTH_LONG).show();
                        // return true;
                        switch (item.getItemId()) {

                            case R.id.menu1:
                                DBWorker dbWorker = new DBWorker();
                                flyVItem=dbWorker.getDataToFlyV(p);

                                if(OpenFlyWindow(flyVItem)) {

                                    Uri address = Uri.parse("http://"+flyVItem.getUrl());
                                    Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                                    startActivity(openlink);
                                    onStop();
                                }
                                return true;
                            case R.id.menu2:

                                Intent intent=new Intent(MainActivity.this,PasswordActivity.class);
                                Log.v("123sda1223", String.valueOf(p));
                                intent.putExtra("folder",String.valueOf(p));
                                intent.putExtra("mode",modeEdit);
                                startActivityForResult(intent,1234);


//                                Intent intent=new Intent(MainActivity.this,UserActivity.class);
//                                intent.putExtra("folder","1");
//                                intent.putExtra("mode",modeCreate);
//                                startActivity(intent);
                                return true;
                            default:
                                return false;
                        }
                    }
                });


        popupMenu.show();
    }

    @Override
    protected void onStop() {
        if(flyVItem!=null) {
            Intent intent = new Intent(MainActivity.this, Fly.class);
            intent.putExtra("pass", flyVItem.getPass());
            intent.putExtra("login", flyVItem.getLogin());
            startService(intent);
            flyVItem=null;
        }
        super.onStop();


    }

    @Override
    protected void onStart() {
        closeSearch();
        super.onStart();

    }
    public void refreshClose(){
        recyclerViewAdapter.setSizeSelect(0);
        recyclerViewAdapter.notifyDataSetChanged();
        switchToolbar(0);
        progressl.setVisibility(View.GONE);
    }

    public void refreshStart(){

        progressl.setVisibility(View.VISIBLE);
    }

    @Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.v("12312asd1232",requestCode+" "+resultCode);
    if(resultCode==-1){
        refreshLayout.setRefreshing(true);
        presenter.refreshUsers();
    }
}
    public void switchToolbar(int stat){
        if(stat==1) {

            ll.setVisibility(View.GONE);
            rlSelect.setVisibility(View.VISIBLE);
            pasteL.setVisibility(View.GONE);
        }else if(stat==0){
            recyclerViewAdapter.closeSelect();
            recyclerViewAdapter.notifyDataSetChanged();
            ll.setVisibility(View.VISIBLE);
            rlSelect.setVisibility(View.GONE);
            pasteL.setVisibility(View.GONE);
        }else{
            pasteState.setText("select "+cutItems.size()+" items");
            ll.setVisibility(View.GONE);
            rlSelect.setVisibility(View.GONE);
            pasteL.setVisibility(View.VISIBLE);
        }
        }



    private boolean OpenFlyWindow(FlyVItem flyVItem){
        if(Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(MainActivity.this) ){

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            intent.putExtra("pass",flyVItem.getPass());
            intent.putExtra("login",flyVItem.getLogin());
            startActivityForResult(intent, 1234);


            return false;
        }

        return true;
    }

    private void closeSearch(){
        fLayout.setVisibility(View.VISIBLE);
        headersearchView.onActionViewCollapsed();
        placeV.setVisibility(View.VISIBLE);
        cloak.setVisibility(View.GONE);

    }
    private void openSearch(){
        fLayout.setVisibility(View.GONE);
        placeV.setVisibility(View.GONE);
        cloak.setVisibility(View.VISIBLE);
//        searchList.setAdapter();
//        LoadPasswordList();
    }

    private List<SearchItem> LoadPasswordList(String s) {
        DBWorker dbWorker=new DBWorker();

        return  dbWorker.getSomePass(s);
    }

    private void changeWay(String s){
     placeV.setText(placeV.getText()+"/"+s);
    }
    private void backWay(){
        Log.v("test","123");
        String s=placeV.getText().toString();
        s=s.substring(0,s.lastIndexOf('/'));
        placeV.setText(s);
        Log.d("way", String.valueOf(presenter.getWay()));
    }



    private void ClickFabB() {

        if(fButton2.getVisibility()==View.VISIBLE){
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
        int id = item.getItemId();

        switch (id){
            case R.id.exit:
                Intent intent=new Intent(MainActivity.this,PreloaderActivity.class);
                startActivity(intent);
                finish();
                LoadText.setNull();
                break;
            case R.id.service:

                startActivity(new Intent(MainActivity.this,ServiceActivity.class));

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
            List list=new ArrayList<>();


            recyclerViewAdapter = new RecyclerViewAdapter(items);

            recyclerView.setAdapter(recyclerViewAdapter);
        }else{
            items = new ArrayList<>();
            recyclerViewAdapter = new RecyclerViewAdapter(items);
            recyclerView.setAdapter(recyclerViewAdapter);
            Toast.makeText(this,"empty",Toast.LENGTH_SHORT).show();
        }

        if(recyclerViewAdapter.getSizeSelect()==0){
            modeSelect=false;
        }
            if(modeSelect){
            switchToolbar(0);
    }
    }

    public void deleteError() {
        Toast.makeText(this,"folder is not empty",Toast.LENGTH_SHORT).show();
    }

    public void failRefresh() {
        items = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(items);
        recyclerView.setAdapter(recyclerViewAdapter);
        refreshLayout.setRefreshing(false);
        Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
    }
}