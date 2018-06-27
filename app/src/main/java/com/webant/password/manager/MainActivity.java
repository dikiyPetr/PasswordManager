package com.webant.password.manager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.webant.password.manager.Adapters.Get.GetPass_Item;
import com.webant.password.manager.Adapters.Model.CutItem;
import com.webant.password.manager.Adapters.Model.DeleteItems;
import com.webant.password.manager.Adapters.Model.FlyVItem;
import com.webant.password.manager.Adapters.Model.SearchItem;
import com.webant.password.manager.Adapters.Model.SelectedShareItems;
import com.webant.password.manager.DBase.DBWorker;
import com.webant.password.manager.DBase.LoadText;
import com.webant.password.manager.ItemModel.MainItem;
import com.webant.password.manager.Model.MainModel;
import com.webant.password.manager.Presenters.MainPresenter;
import com.webant.password.manager.RecyclerView.MainActivityRecyclerViewAdapter;
import com.webant.password.manager.SearchRecycler.RVSearchAdapter;
import com.webant.password.manager.crypto.NewAes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 16.02.2018.
 */
public class MainActivity extends AppCompatActivity {
    private SearchView headersearchView;
    private TextView placeV, sizeselect, pasteState;
    private DrawerLayout drawer;
    private RecyclerView recyclerView, rvSearch;
    private ArrayList<MainItem> items = new ArrayList<>();
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
    private RelativeLayout fLayout, cloak, pasteL;
    private List<SearchItem> searchItems = new ArrayList<>();
    private FrameLayout progressl;
    FlyVItem flyVItem = null;
    private List<CutItem> cutItems = new ArrayList<>();
    private ProfileDrawerItem profileDrawerItem;
    private Drawer result;
    LinearLayout statFabBar, searchLayout;
    private AccountHeader headerResult;
    Toolbar toolbar;
    DBWorker dbWorker;
    Context context;
    boolean modePaste = false;
    int toolbarStat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.main_activity);
        init();
        dbWorker = new DBWorker(this);

        if (getIntent().getData() != null) {
            if (getIntent().getData().getQueryParameter("masterPass") != null) {
                editMasterPassQuestion(Uri.decode(getIntent().getData().getQueryParameter("masterPass")));
            } else if (getIntent().getData().getQueryParameter("cryptoMasterPass") != null) {
                editCryptoMasterPassQuestion(Uri.decode(getIntent().getData().getQueryParameter("cryptoMasterPass")));
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        context = this;
        searchLayout = findViewById(R.id.searchLayout);
        statFabBar = findViewById(R.id.statFabBar);
        pasteState = findViewById(R.id.pasteStat);
        closeP = findViewById(R.id.closeP);
        sizeselect = findViewById(R.id.sizeselect);
        closeSelect = findViewById(R.id.closeselect);
        placeV = findViewById(R.id.placev);
        pasteI = findViewById(R.id.paste);
        pasteL = findViewById(R.id.pasteLayout);
        fButton1 = findViewById(R.id.floatingActionButton);
        fButton2 = findViewById(R.id.floatingActionButton3);
        fButton4 = findViewById(R.id.fbaddpassword);
        menuSelect = findViewById(R.id.menus);
        rvSearch = findViewById(R.id.searchRV);
        progressl = findViewById(R.id.progressl);
        rlSelect = findViewById(R.id.selectedLayout);
        headersearchView = findViewById(R.id.mainserch);
        cloak = findViewById(R.id.cloak);
        refreshLayout = findViewById(R.id.refresh);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager llmS = new LinearLayoutManager(this);
        rvSearch.setLayoutManager(llmS);
        rvSearch.setHasFixedSize(true);
        recyclerView = findViewById(R.id.rv);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        closeP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToolbarMode(0);
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
                    searchItems.clear();
                    searchItems.addAll(dbWorker.getSomePass(newText));
                    searchViewAdapter.notifyDataSetChanged();
                } else {
                    searchItems.clear();
                    searchViewAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        pasteI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.moveItem(cutItems, presenter.getWay());
                setToolbarMode(0);
            }
        });
        closeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToolbarMode(0);
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
        headersearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openSearch();
                } else {
                    closeSearch();
                }
            }
        });
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
        searchViewAdapter = new RVSearchAdapter(searchItems, new RVSearchAdapter.SearchClick() {
            @Override
            public void click(int id) {
                if (id != -1) {
                    Intent intent = new Intent(MainActivity.this, PasswordActivity.class);

                    intent.putExtra("folder", String.valueOf(searchItems.get(id).getId()));
                    intent.putExtra("mode", modeEdit);
                    startActivityForResult(intent, 1234);
                }
            }
        });
        rvSearch.setAdapter(searchViewAdapter);
        mainActivityRecyclerViewAdapter = new MainActivityRecyclerViewAdapter(this, items, new MainActivityRecyclerViewAdapter.MainClick() {
            @Override
            public void longClick(View v, int id) {
                selectItem(id);
            }

            @Override
            public void click(View v, int id) {
                if (id != -1) {
                    if (modeSelect) {
                        selectItem(id);
                    } else {
                        if (items.get(id).getType()) {
                            presenter.nextWay(items.get(id).getId(), items.get(id).getName());
                        } else {
                            showPopupMenu(v, items.get(id).getId());
                        }
                    }
                }
            }
        });
        recyclerView.setAdapter(mainActivityRecyclerViewAdapter);
        MainModel usersModel = new MainModel();
        presenter = new MainPresenter(usersModel);
        presenter.attachView(this);
        presenter.viewIsReady();
        placeV.setText("");
        createLeftMenu();
    }

    public void selectItem(int position) {
        int b = mainActivityRecyclerViewAdapter.selectItem(position);
        if (b == 0) {
            modeSelect = false;
            setToolbarMode(0);
        } else {
            modeSelect = true;
            setToolbarMode(1);
        }
        sizeselect.setText("Выбранно " + b + " элемент(ов)");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            refreshLayout.setRefreshing(true);
            presenter.refreshList();
        }
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        items = (ArrayList<MainItem>) savedInstanceState.get("key");
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putParcelableArrayList("key", (ArrayList) items);
//        super.onSaveInstanceState(outState);
//    }

    private void editMasterPassQuestion(final String masterPass) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Изменение MasterPass\n");
        alertDialog.setMessage("ВНИМАНИЕ\n" +
                "при смене MasterPass пароли зашифрованные прошлым MasterPass(ом) будут недоступны");
        alertDialog.setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setPositiveButton("Применить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setMasterPass(masterPass);
            }
        });
        alertDialog.setIcon(R.drawable.key);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    public void setMasterPass(String masterPass) {
        Toast toast = Toast.makeText(this, "MasterPass изменен на " + masterPass, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        ((AplicationListner) getApplication()).getPin();
        LoadText.setMasterPass(this, NewAes.encrypt(masterPass, ((AplicationListner) getApplication()).getPin()));
    }

    private void editCryptoMasterPassQuestion(final String cryptoMasterPass) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Изменение MasterPass\n");
        alertDialog.setMessage("ВНИМАНИЕ\n" +
                "при смене MasterPass пароли зашифрованные прошлым MasterPass(ом) будут недоступны\n" +
                "\n" +
                "Введите пин-код который существовал при создании/изменении присланного MasterPass");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        alertDialog.setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setPositiveButton("Применить", null);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.key);
        alertDialog.setCancelable(false);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        Button b = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().length() != 4)
                    Toast.makeText(context, "Неверный пин-код", Toast.LENGTH_SHORT).show();
                else if (NewAes.decrypt(cryptoMasterPass, input.getText().toString()).equals("")) {
                    Toast.makeText(context, "Не тот пин-код", Toast.LENGTH_SHORT).show();
                } else {
                    setMasterPass(NewAes.decrypt(cryptoMasterPass, input.getText().toString()));
                    dialog.dismiss();
                }
            }
        });
    }

    private void showToolbarMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
        popupMenu.inflate(R.menu.selectmenu);
        if (mainActivityRecyclerViewAdapter.getSizeSelect() == 1) {
            popupMenu.getMenu().add(1, 1, 1, "изменить");
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mD:
                        List<DeleteItems> deleteItems = new ArrayList<>();
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getStat()) {
                                deleteItems.add(new DeleteItems(items.get(i).getId(), items.get(i).getType()));
                                items.get(i).switchStat();
                            }
                        }
                        presenter.deleteItem(deleteItems);
                        mainActivityRecyclerViewAdapter.setSizeSelect(0);
                        modeSelect = false;
                        setToolbarMode(0);
                        mainActivityRecyclerViewAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.mM:
                        cutItems = new ArrayList<>();
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getStat()) {
                                cutItems.add(new CutItem(items.get(i).getId(), items.get(i).getType(), items.get(i).getStorageId()));
                                items.get(i).switchStat();
                            }
                        }
                        mainActivityRecyclerViewAdapter.setSizeSelect(0);
                        modeSelect = false;
                        setToolbarMode(2);
                        mainActivityRecyclerViewAdapter.notifyDataSetChanged();

                        return true;
                    case R.id.mS:
                        List<MainItem> shareItems = new ArrayList<>();
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getStat()) {
                                shareItems.add(new MainItem(items.get(i).getId(), items.get(i).getType()));
                                items.get(i).switchStat();
                            }
                        }
                        shareItemsMessage(shareItems);
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
                                setToolbarMode(0);
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

    private void shareItemsMessage(List<MainItem> items) {
        List<SelectedShareItems> selectedShareItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getType()) {
                selectedShareItems.addAll(dbWorker.getPasswordOfFolder(items.get(i).getId(), ((AplicationListner) this.getApplication()).getMasterPass()));
            } else {
                selectedShareItems.add(dbWorker.getShareParams(items.get(i).getId(), ((AplicationListner) this.getApplication()).getMasterPass()));
            }
        }
        sendPassMesage(selectedShareItems);
        mainActivityRecyclerViewAdapter.setSizeSelect(0);
        modeSelect = false;
        setToolbarMode(0);
        mainActivityRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void sendPassMesage(final List<SelectedShareItems> list) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Введить почту кому хотите передать пароль(и)");
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("Отправить",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.givePass(list, input.getText().toString());
                    }
                });
        alertDialog.setNegativeButton("Отмена",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();

    }

    private void showPopupMenu(View v, final int p) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        refreshClose();
        popupMenu.inflate(R.menu.popupmenu);
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                GetPass_Item getPass_item = dbWorker.getPass(p);
                                flyVItem = new FlyVItem();
                                flyVItem.setUrl(getPass_item.getUrl());
                                flyVItem.setPass(NewAes.decrypt(getPass_item.getPass(), NewAes.decrypt(getPass_item.getClue(), ((AplicationListner) getApplication()).getMasterPass())));
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
        setToolbarMode(0);
        progressl.setVisibility(View.GONE);
    }

    public void refreshStart() {
        progressl.setVisibility(View.VISIBLE);
    }

    public void setToolbarMode(int toolbarStat) {
        this.toolbarStat = toolbarStat;
        switch (toolbarStat) {
            case 0:
                //search
                modeSelect = false;
                modePaste = false;
                rlSelect.setVisibility(View.GONE);
                pasteL.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);
                cutItems.clear();
                mainActivityRecyclerViewAdapter.closeSelect();
                mainActivityRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case 1:
                //selected
                modeSelect = true;
                searchLayout.setVisibility(View.GONE);
                rlSelect.setVisibility(View.VISIBLE);
                pasteL.setVisibility(View.GONE);
                break;
            case 2:
                //cut
                modePaste = true;
                rlSelect.setVisibility(View.GONE);
                pasteL.setVisibility(View.VISIBLE);
                searchLayout.setVisibility(View.GONE);
                pasteState.setText("Выбранно " + cutItems.size() + " элемент(ов)");
                break;
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
        headersearchView.onActionViewCollapsed();
        placeV.setVisibility(View.VISIBLE);
        cloak.setVisibility(View.GONE);

    }

    private void openSearch() {

        placeV.setVisibility(View.GONE);
        cloak.setVisibility(View.VISIBLE);
    }

    private List<SearchItem> LoadPasswordList(String s) {
        return dbWorker.getSomePass(s);
    }

    @SuppressLint("SetTextI18n")
    public void changeWay(String s) {
        placeV.setText(placeV.getText() + "/" + s);
    }

    private void backWay() {
        List<String> id = new ArrayList<>();
        id.add("1");
        id.add("2");
        id.add("3");
        List<String> key = new ArrayList<>();
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
                        new SecondaryDrawerItem().withIdentifier(1).withName("Главная").withSelectable(false),
                        new SecondaryDrawerItem().withIdentifier(2).withName("Сервисы").withSelectable(false),
                        new SecondaryDrawerItem().withIdentifier(3).withName("Настройки").withSelectable(false),
                        new SecondaryDrawerItem().withIdentifier(4).withName("Выход").withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:

                                break;
                            case 2:

                                startActivity(new Intent(context, ServiceActivity.class));

                                break;
                            case 3:

                                startActivity(new Intent(context, SettingActivity.class));


                                break;
                            case 4:
                                popupMesage();
                                break;
                        }
                        return false;
                    }
                })
                .build();
        result.setSelection(1);
    }

    public void popupMesage() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Вы точно хотите выйти?")
                .setPositiveButton("выйти", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity.this, PreloaderActivity.class);
                        startActivity(intent);
                        finish();

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

    private void ClickFabB() {
        if (statFabBar.getVisibility() == View.VISIBLE) {
            Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.rotate_m45);
            Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.ftexte);
            anim2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    statFabBar.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            statFabBar.startAnimation(anim2);
            fButton1.startAnimation(anim1);
        } else {
            Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.rotate_45);
            Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.ftexts);
            anim1.setFillAfter(true);
            statFabBar.setVisibility(View.VISIBLE);
            fButton1.startAnimation(anim1);
            statFabBar.startAnimation(anim2);
        }

    }

    @Override
    public void onBackPressed() {
        if (modeSelect) {
            modeSelect = false;
            mainActivityRecyclerViewAdapter.setSizeSelect(0);
            setToolbarMode(0);
        } else {
            if (headersearchView.isFocused()) {
                closeSearch();
            } else if (presenter.backWay()) {
                backWay();
            } else if (modePaste) {
                modePaste = false;
                setToolbarMode(0);
            } else {
                super.onBackPressed();
            }
        }
    }

    public void showItems() {
        progressl.setVisibility(View.GONE);
        if (modeSelect)
            setToolbarMode(0);
        refreshLayout.setRefreshing(false);
        items.clear();
        items.addAll(dbWorker.loadData(presenter.getWay()));
        mainActivityRecyclerViewAdapter.notifyDataSetChanged();
    }


    public void setUserInfo() {
        profileDrawerItem.withEmail(LoadText.getText(this, "email"));
        headerResult.updateProfile(profileDrawerItem);
    }

    public void setRefresh(boolean b) {
        refreshLayout.setRefreshing(false);
        progressl.setVisibility(View.GONE);
    }


    public void sendPass(List<SelectedShareItems> list, String mail) {
        String id = "";
        String key = "";
        for (int i = 0; i < list.size(); i++) {
            id += "id=" + list.get(i).getId() + "&";
        }
        for (int i = 0; i < list.size(); i++) {
            key += "clue=" + Uri.encode(list.get(i).getKey()) + "&";
        }
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                mail);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "MainPass");
        String reqest = "/?" + id + key;
        emailIntent.putExtra(Intent.EXTRA_TEXT, "pass.add.com/pass" + reqest);
        this.startActivity(Intent.createChooser(emailIntent,
                "Отправка письма..."));
    }

}