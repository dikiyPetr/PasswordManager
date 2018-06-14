package com.example.dikiy.passwordmain;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.Adapters.Model.CutItem;
import com.example.dikiy.passwordmain.Adapters.Model.FlyVItem;
import com.example.dikiy.passwordmain.Adapters.Model.SearchItem;
import com.example.dikiy.passwordmain.DBase.DBWorker;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Model.MainModel;

import com.example.dikiy.passwordmain.Presenters.MainPresenter;
import com.example.dikiy.passwordmain.ItemModel.MainItem;
import com.example.dikiy.passwordmain.RecyclerView.RecyclerItemClickListener;
import com.example.dikiy.passwordmain.RecyclerView.MainActivityRecyclerViewAdapter;
import com.example.dikiy.passwordmain.SearchRecycler.RVSearchAdapter;
import com.example.dikiy.passwordmain.crypto.Rsa256Class;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.DimenHolder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 16.02.2018.
 */
public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    private android.widget.SearchView headersearchView;
    private TextView placeV, sizeselect, pasteState;
    private DrawerLayout drawer;
    private RecyclerView recyclerView, rvSearch;
    private List<MainItem> items = new ArrayList<>();
    private MainActivityRecyclerViewAdapter mainActivityRecyclerViewAdapter;
    private RVSearchAdapter searchViewAdapter;
    private FloatingActionButton fButton1, fButton2, fButton4;
    private ImageView closeSelect;
    private SwipeRefreshLayout refreshLayout;
    final String modeEdit = "1";
    final String modeCreate = "0";
    private MainPresenter presenter;
    private List<String> way = new ArrayList<>();
    private boolean modeSelect = false;
    private RelativeLayout rlSelect;
    private ImageView moveItems, menuSelect, pasteI, closeP;
    private RelativeLayout ll, fLayout, cloak, pasteL;
    private List<SearchItem> searchItems = new ArrayList<>();
    private FrameLayout progressl;
    FlyVItem flyVItem = null;
    private List<CutItem> cutItems = new ArrayList<>();
    private ProfileDrawerItem profileDrawerItem;
    private Drawer result;
    private AccountHeader headerResult;
    Toolbar toolbar;
    private final int MODE_EDIT_MAINPASS = 1, MODE_EXIT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.main_activity);
        init();
    }

    private void init() {
        pasteState = findViewById(R.id.pasteStat);
        closeP = findViewById(R.id.closeP);
        sizeselect = findViewById(R.id.sizeselect);
        closeSelect = findViewById(R.id.closeselect);
        placeV = findViewById(R.id.placev);
        pasteI = findViewById(R.id.paste);
        pasteL = findViewById(R.id.pl);
        fButton1 = findViewById(R.id.floatingActionButton);
        fButton2 = findViewById(R.id.floatingActionButton3);
        fButton4 = findViewById(R.id.fbaddpassword);
        menuSelect = findViewById(R.id.menus);
        ll = findViewById(R.id.ll);
        rvSearch = findViewById(R.id.searchRV);
        progressl = findViewById(R.id.progressl);
        fLayout = findViewById(R.id.fLayout);
        rlSelect = findViewById(R.id.rl);
        headersearchView = findViewById(R.id.mainserch);
        cloak = findViewById(R.id.cloak);
        refreshLayout = findViewById(R.id.refresh);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager llmS = new LinearLayoutManager(this);
        rvSearch.setLayoutManager(llmS);
        rvSearch.setHasFixedSize(true);
        searchViewAdapter = new RVSearchAdapter(searchItems);
        rvSearch.setAdapter(searchViewAdapter);
        recyclerView = findViewById(R.id.rv);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        mainActivityRecyclerViewAdapter = new MainActivityRecyclerViewAdapter(this, items);
        recyclerView.setAdapter(mainActivityRecyclerViewAdapter);
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
                if (newText.length() >= 1) {
                    searchItems = new ArrayList<>();
                    searchItems.addAll(LoadPasswordList(newText));
                    searchViewAdapter = new RVSearchAdapter(searchItems);
                    searchViewAdapter.notifyDataSetChanged();
                    rvSearch.setAdapter(searchViewAdapter);
                    Log.v("12zxcdadads", newText);
                } else {
                    searchItems = new ArrayList<>();
                    searchViewAdapter = new RVSearchAdapter(searchItems);
                    searchViewAdapter.notifyDataSetChanged();
                    rvSearch.setAdapter(searchViewAdapter);
                }
                return false;
            }
        });
        pasteI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.moveItem(cutItems, presenter.getWay());
                switchToolbar(0);
                cutItems = new ArrayList<>();
            }
        });
        closeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityRecyclerViewAdapter.setSizeSelect(0);
                modeSelect = false;
                switchToolbar(0);
            }
        });
        menuSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToolbarMenu(v);
            }
        });
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
                Intent intent = new Intent(MainActivity.this, FolderActivity.class);
                intent.putExtra("folder", String.valueOf(presenter.getWay()));
                intent.putExtra("mode", modeCreate);
                startActivityForResult(intent, 1);
            }
        });
        fButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
                intent.putExtra("folder", String.valueOf(presenter.getWay()));
                intent.putExtra("mode", modeCreate);
                startActivityForResult(intent, 1);
            }
        });
        headersearchView.setOnQueryTextFocusChangeListener(this);
        cloak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSearch();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshList();
            }
        });

        rvSearch.addOnItemTouchListener(new RecyclerItemClickListener(this, rvSearch, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.v("12zxcdadads", String.valueOf(position));
                if (position != -1) {
                    Intent intent = new Intent(MainActivity.this, PasswordActivity.class);

                    intent.putExtra("folder", String.valueOf(searchItems.get(position).getId()));
                    intent.putExtra("mode", modeEdit);
                    startActivityForResult(intent, 1234);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (modeSelect) {
                            int b = mainActivityRecyclerViewAdapter.selectItem(position);
                            if (b == 0) {
                                modeSelect = false;
                                switchToolbar(0);
                            } else {
                                modeSelect = true;
                            }
                            sizeselect.setText("select " + b + " item(s)");
                        } else {
                            if (position != -1) {
                                if (items.get(position).getType()) {
                                    presenter.nextWay(items.get(position).getId(), items.get(position).getName());

                                } else {
                                    showPopupMenu(view, items.get(position).getId());

                                }
                            }
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        int b = mainActivityRecyclerViewAdapter.selectItem(position);
                        if (b == 0) {
                            modeSelect = false;
                            switchToolbar(0);
                        } else {
                            modeSelect = true;
                            switchToolbar(1);
                        }
                        sizeselect.setText("select " + b + " item(s)");
                    }
                }));

        MainModel usersModel = new MainModel();
        presenter = new MainPresenter(usersModel);
        presenter.attachView(this);
        presenter.viewIsReady();
        placeV.setText("");
        createLeftMenu();
    }

    private void showToolbarMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
        popupMenu.inflate(R.menu.selectmenu);
        if (mainActivityRecyclerViewAdapter.getSizeSelect() == 1) {
            popupMenu.getMenu().add(1, 1, 1, "edit");
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mD:
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getStat()) {
                                presenter.deleteItem(items.get(i).getId(), items.get(i).getType());
                                items.get(i).switchStat();
                            }
                        }
                        mainActivityRecyclerViewAdapter.setSizeSelect(0);
                        modeSelect = false;
                        switchToolbar(0);
                        mainActivityRecyclerViewAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.mM:
                        cutItems = new ArrayList<>();
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getStat()) {
                                cutItems.add(new CutItem(items.get(i).getId(), items.get(i).getType()));
                                items.get(i).switchStat();
                            }
                        }
                        mainActivityRecyclerViewAdapter.setSizeSelect(0);
                        modeSelect = false;
                        switchToolbar(2);
                        mainActivityRecyclerViewAdapter.notifyDataSetChanged();

                        return true;
                    case 1:
                        int itemId = 0;
                        boolean itemType = false;
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getStat()) {
                                itemId = items.get(i).getId();
                                itemType = items.get(i).getType();
                                items.get(i).switchStat();
                                mainActivityRecyclerViewAdapter.setSizeSelect(0);
                                modeSelect = false;
                                switchToolbar(0);
                                mainActivityRecyclerViewAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                        Intent intent;
                        if (!itemType) {
                            intent = new Intent(MainActivity.this, PasswordActivity.class);
                        } else {
                            intent = new Intent(MainActivity.this, FolderActivity.class);
                        }
                        intent.putExtra("folder", String.valueOf(itemId));
                        intent.putExtra("mode", modeEdit);
                        startActivityForResult(intent, 1234);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    private void showPopupMenu(View v, final int p) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu); // Для Android 4.0
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                DBWorker dbWorker = new DBWorker(MainActivity.this);
                                GetPass_Item getPass_item = dbWorker.getPass(p);
                                flyVItem = new FlyVItem();
                                flyVItem.setUrl(getPass_item.getUrl());
                                flyVItem.setPass(Rsa256Class.decode(getPass_item.getPass(), getPass_item.getClue()));
                                flyVItem.setLogin(getPass_item.getLogin());
                                if (OpenFlyWindow(flyVItem)) {
                                    Uri address = Uri.parse("http://" + flyVItem.getUrl());
                                    Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                                    startActivity(openlink);
                                    onStop();
                                }
                                return true;
                            case R.id.menu2:
                                Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
                                intent.putExtra("folder", String.valueOf(p));
                                intent.putExtra("mode", modeEdit);
                                startActivityForResult(intent, 1234);
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
        if (flyVItem != null) {
            Intent intent = new Intent(MainActivity.this, Fly.class);
            intent.putExtra("pass", flyVItem.getPass());
            intent.putExtra("login", flyVItem.getLogin());
            startService(intent);
            flyVItem = null;
        }
        super.onStop();
    }

    @Override
    protected void onStart() {
        closeSearch();
        super.onStart();
    }

    public void refreshClose() {
        mainActivityRecyclerViewAdapter.setSizeSelect(0);
        mainActivityRecyclerViewAdapter.notifyDataSetChanged();
        switchToolbar(0);
        progressl.setVisibility(View.GONE);
    }

    public void refreshStart() {

        progressl.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            refreshLayout.setRefreshing(true);
            presenter.refreshList();
        }
    }

    public void switchToolbar(int stat) {
        if (stat == 1) {
            ll.setVisibility(View.GONE);
            rlSelect.setVisibility(View.VISIBLE);
            pasteL.setVisibility(View.GONE);
        } else if (stat == 0) {
            mainActivityRecyclerViewAdapter.closeSelect();
            mainActivityRecyclerViewAdapter.notifyDataSetChanged();
            ll.setVisibility(View.VISIBLE);
            rlSelect.setVisibility(View.GONE);
            pasteL.setVisibility(View.GONE);
        } else {
            pasteState.setText("select " + cutItems.size() + " items");
            ll.setVisibility(View.GONE);
            rlSelect.setVisibility(View.GONE);
            pasteL.setVisibility(View.VISIBLE);
        }
    }


    private boolean OpenFlyWindow(FlyVItem flyVItem) {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(MainActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            intent.putExtra("pass", flyVItem.getPass());
            intent.putExtra("login", flyVItem.getLogin());
            startActivityForResult(intent, 1234);
            return false;
        }
        return true;
    }

    private void closeSearch() {
        fLayout.setVisibility(View.VISIBLE);
        headersearchView.onActionViewCollapsed();
        placeV.setVisibility(View.VISIBLE);
        cloak.setVisibility(View.GONE);

    }

    private void openSearch() {
        fLayout.setVisibility(View.GONE);
        placeV.setVisibility(View.GONE);
        cloak.setVisibility(View.VISIBLE);
    }

    private List<SearchItem> LoadPasswordList(String s) {
        DBWorker dbWorker = new DBWorker(this);
        return dbWorker.getSomePass(s);
    }

    @SuppressLint("SetTextI18n")
    public void changeWay(String s) {
        placeV.setText(placeV.getText() + "/" + s);
    }

    private void backWay() {
        List<String> id =new ArrayList<>();
        id.add("1");
        id.add("2");
        id.add("3");
        List<String> key =new ArrayList<>();
        key.add("1dasa");
        key.add("2cxv");
        key.add("3asd");

        String s = placeV.getText().toString();
        s = s.substring(0, s.lastIndexOf('/'));
        placeV.setText(s);
        Log.d("way", String.valueOf(presenter.getWay()));
    }

    public void createLeftMenu() {
        profileDrawerItem = new ProfileDrawerItem().withIdentifier(1)
                .withEmail(LoadText.getText(this, "email"));
        new DrawerBuilder().withActivity(this).build();
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.gradient_left_menu)
                .withSelectionListEnabled(false)
                .withProfileImagesVisible(false)
                .addProfiles(profileDrawerItem)
                .build();
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSliderBackgroundColorRes(R.color.white)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new SecondaryDrawerItem().withIdentifier(2).withName("Сервисы").withSelectable(false),
                        new SecondaryDrawerItem().withIdentifier(3).withName("Изменить MainPass").withSelectable(false),
                        new SecondaryDrawerItem().withIdentifier(4).withName("Выход").withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:

                                startActivity(new Intent(MainActivity.this, ServiceActivity.class));
                                break;
                            case 2:

                                popupMesage(MODE_EDIT_MAINPASS);
                                break;
                            case 3:
                                popupMesage(MODE_EXIT);
                                break;
                        }
                        return false;
                    }
                })
                .build();
        result.setSelection(0);
    }

    public void popupMesage(int mode) {
        if (mode == MODE_EDIT_MAINPASS) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Изменить MainPass");
            final EditText input = new EditText(MainActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            input.setTransformationMethod(PasswordTransformationMethod.getInstance());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                input.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            alertDialog.setView(input);
            alertDialog.setIcon(R.drawable.key);
            alertDialog.setPositiveButton("Изменить",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            LoadText.setMasterPass(MainActivity.this, input.getText().toString());
                        }
                    });
            alertDialog.setNegativeButton("Отмена",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            alertDialog.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Вы точно хотите выйти?")
                    .setPositiveButton("выйти", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(MainActivity.this, PreloaderActivity.class);
                            startActivity(intent);
                            finish();
                            DBWorker dbWorker = new DBWorker(MainActivity.this);
                            dbWorker.setNull();
                            LoadText.setNull(MainActivity.this);
                        }
                    })
                    .setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            builder.create().show();
        }
    }

    private void ClickFabB() {
        if (fButton2.getVisibility() == View.VISIBLE) {
            Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.rotate_m45);
            anim1.setFillAfter(true);
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.fbuttone);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    fButton2.setVisibility(View.GONE);
                    fButton4.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            fButton1.startAnimation(anim1);
            fButton2.startAnimation(anim);
            fButton4.startAnimation(anim);
        } else {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.fbuttons);
            Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.rotate_45);
            anim1.setFillAfter(true);
            fButton2.setVisibility(View.VISIBLE);
            fButton4.setVisibility(View.VISIBLE);
            fButton1.startAnimation(anim1);
            fButton2.startAnimation(anim);
            fButton4.startAnimation(anim);
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            openSearch();
        } else {
            closeSearch();
        }
    }

    @Override
    public void onBackPressed() {
        if (headersearchView.isFocused()) {
            closeSearch();
        } else if (presenter.backWay()) {
            backWay();
        } else {
            super.onBackPressed();
        }
    }

    public void showUsers() {
        DBWorker dbWorker = new DBWorker(this);
        List<MainItem> users = new ArrayList<>();
        users.addAll(dbWorker.loadData(presenter.getWay()));
        refreshLayout.setRefreshing(false);
        if (users != null && users.size() != 0) {
            items = new ArrayList<>();
            items.addAll(users);
            List list = new ArrayList<>();
            mainActivityRecyclerViewAdapter = new MainActivityRecyclerViewAdapter(this, items);
            recyclerView.setAdapter(mainActivityRecyclerViewAdapter);
        } else {
            items = new ArrayList<>();
            mainActivityRecyclerViewAdapter = new MainActivityRecyclerViewAdapter(this, items);
            recyclerView.setAdapter(mainActivityRecyclerViewAdapter);
        }
        if (mainActivityRecyclerViewAdapter.getSizeSelect() == 0) {
            modeSelect = false;
        }
        if (modeSelect) {
            switchToolbar(0);
        }
    }

    public void failRefresh() {
        items = new ArrayList<>();
        mainActivityRecyclerViewAdapter = new MainActivityRecyclerViewAdapter(this, items);
        recyclerView.setAdapter(mainActivityRecyclerViewAdapter);
        refreshLayout.setRefreshing(false);
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }

    public void setUserInfo() {
        profileDrawerItem.withEmail(LoadText.getText(this, "email"));
        headerResult.updateProfile(profileDrawerItem);
    }


}