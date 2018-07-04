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
    private SearchView searchView;
    private TextView wayTextView, selectTextView, pasteTextView;
    private ArrayList<MainItem> mainItems = new ArrayList<>();
    private MainActivityRecyclerViewAdapter mainRVAdapter;
    private RVSearchAdapter searchRVAdapter;
    private FloatingActionButton createFab;
    private SwipeRefreshLayout refreshLayout;
    private MainPresenter presenter;
    private RelativeLayout toolbarSelect;
    private RelativeLayout cloakView, toolbarPaste;
    private List<SearchItem> searchItems = new ArrayList<>();
    private FrameLayout progressL;
    private FlyVItem flyItem = null;
    private ArrayList<CutItem> pasteItems = new ArrayList<>();
    private ProfileDrawerItem profileDrawerItem;
    private LinearLayout fabLayout, toolbarSearch;
    private AccountHeader headerResult;
    private Toolbar toolbar;
    private DBWorker dbWorker;
    private Context context;
    private boolean modeSelect = false;
    private boolean modePaste = false;
    private final String MODE_EDIT = "1";
    private final String MODE_CREATE = "0";
    private int toolbarStat = 0;
    Drawer result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.main_activity);
        if (getIntent().getData() != null)
            if (getIntent().getData().getQueryParameter("masterPass") != null) {
                editMasterPassQuestion(Uri.decode(getIntent().getData().getQueryParameter("masterPass")));
            } else if (getIntent().getData().getQueryParameter("cryptoMasterPass") != null) {
                editCryptoMasterPassQuestion(Uri.decode(getIntent().getData().getQueryParameter("cryptoMasterPass")));
            }
        if (savedInstanceState != null)
            mainItems = savedInstanceState.getParcelableArrayList("mainItems");
        init();
        if (savedInstanceState != null)
            restoreActivityState(savedInstanceState);
        else
            presenter.refreshList();
        setToolbarMode(toolbarStat);
    }

    private void restoreActivityState(Bundle savedInstanceState) {
        pasteItems = savedInstanceState.getParcelableArrayList("pasteItems");
        toolbarStat = savedInstanceState.getInt("toolbarStat");
        presenter = savedInstanceState.getParcelable("presenter");
        assert presenter != null;
        presenter.attachView(this);
        wayTextView.setText(savedInstanceState.getString("wayTitle"));
        presenter.setWay(savedInstanceState.getIntegerArrayList("way"));
        mainRVAdapter.setSizeSelect(savedInstanceState.getInt("sizeSelect"));
        mainRVAdapter.notifyDataSetChanged();
        if(savedInstanceState.getBoolean("drawerState"))
            result.openDrawer();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("drawerState",result.isDrawerOpen());
        outState.putParcelable("presenter", presenter);
        outState.putParcelableArrayList("mainItems", mainItems);
        outState.putInt("toolbarStat", toolbarStat);
        outState.putParcelableArrayList("pasteItems", pasteItems);
        outState.putInt("sizeSelect", mainRVAdapter.getSizeSelect());
        outState.putIntegerArrayList("way", presenter.getWay());
        outState.putString("wayTitle", wayTextView.getText().toString());
    }


    @Override
    protected void onStop() {
        if (flyItem != null) {
            Intent intent = new Intent(MainActivity.this, Fly.class);
            intent.putExtra("pass", flyItem.getPass());
            intent.putExtra("login", flyItem.getLogin());
            startService(intent);
            flyItem = null;
        }
        super.onStop();
    }


    private void init() {
        context = this;
        dbWorker = new DBWorker(this);
        toolbarSearch = findViewById(R.id.searchLayout);
        fabLayout = findViewById(R.id.statFabBar);
        pasteTextView = findViewById(R.id.pasteStat);
        ImageView closePasteButton = findViewById(R.id.closeP);
        selectTextView = findViewById(R.id.sizeselect);
        ImageView closeSelect = findViewById(R.id.closeselect);
        wayTextView = findViewById(R.id.placev);
        ImageView pasteButton = findViewById(R.id.paste);
        toolbarPaste = findViewById(R.id.pasteLayout);
        createFab = findViewById(R.id.floatingActionButton);
        FloatingActionButton folderFab = findViewById(R.id.floatingActionButton3);
        FloatingActionButton passwordFab = findViewById(R.id.fbaddpassword);
        ImageView menuSelect = findViewById(R.id.menus);
        RecyclerView searchRecycler = findViewById(R.id.searchRV);
        progressL = findViewById(R.id.progressl);
        toolbarSelect = findViewById(R.id.selectedLayout);
        searchView = findViewById(R.id.mainserch);
        cloakView = findViewById(R.id.cloak);
        refreshLayout = findViewById(R.id.refresh);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView.LayoutManager llmS = new LinearLayoutManager(this);
        searchRecycler.setLayoutManager(llmS);
        RecyclerView mainRecycler = findViewById(R.id.rv);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        mainRecycler.setLayoutManager(llm);
        closePasteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToolbarMode(0);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 1) {
                    searchItems.clear();
                    searchItems.addAll(dbWorker.getSomePass(newText));
                    searchRVAdapter.notifyDataSetChanged();
                } else {
                    searchItems.clear();
                    searchRVAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        pasteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.moveItem(pasteItems, presenter.getWayPosition());
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
        createFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickFabB();
            }
        });
        //folder
        folderFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FolderActivity.class);
                intent.putExtra("folder", String.valueOf(presenter.getWayPosition()));
                intent.putExtra("mode", MODE_CREATE);
                startActivityForResult(intent, 1);
            }
        });
        passwordFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
                intent.putExtra("folder", String.valueOf(presenter.getWayPosition()));
                intent.putExtra("mode", MODE_CREATE);
                startActivityForResult(intent, 1);
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openSearch();
                } else {
                    closeSearch();
                }
            }
        });
        cloakView.setOnClickListener(new View.OnClickListener() {
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
        searchRVAdapter = new RVSearchAdapter(searchItems, new RVSearchAdapter.SearchClick() {
            @Override
            public void click(int id) {
                if (id != -1) {
                    Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
                    intent.putExtra("folder", String.valueOf(searchItems.get(id).getId()));
                    intent.putExtra("mode", MODE_EDIT);
                    startActivityForResult(intent, 1234);
                }
            }
        });
        searchRecycler.setAdapter(searchRVAdapter);
        mainRVAdapter = new MainActivityRecyclerViewAdapter(this, mainItems, new MainActivityRecyclerViewAdapter.MainClick() {
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
                        if (mainItems.get(id).getType()) {
                            presenter.nextWay(mainItems.get(id).getId(), mainItems.get(id).getName());
                        } else {
                            showPopupMenu(v, mainItems.get(id).getId());
                        }
                    }
                }
            }
        }, dbWorker);
        mainRecycler.setAdapter(mainRVAdapter);
        MainModel usersModel = new MainModel();
        presenter = new MainPresenter(usersModel);
        presenter.attachView(this);
        wayTextView.setText("");
        createLeftMenu();
    }

    public void selectItem(int position) {
        int b = mainRVAdapter.selectItem(position);
        if (b == 0) {
            modeSelect = false;
            setToolbarMode(0);
        } else {
            modeSelect = true;
            setToolbarMode(1);
        }
        selectTextView.setText("Выбранно " + b + " элемент(ов)");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            refreshLayout.setRefreshing(true);
            presenter.refreshList();
        }
    }


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
        if (mainRVAdapter.getSizeSelect() == 1) {
            popupMenu.getMenu().add(1, 1, 1, "изменить");
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mD:
                        List<DeleteItems> deleteItems = new ArrayList<>();
                        for (int i = 0; i < mainItems.size(); i++) {
                            if (mainItems.get(i).getStat()) {
                                deleteItems.add(new DeleteItems(mainItems.get(i).getId(), mainItems.get(i).getType()));
                                mainItems.get(i).switchStat();
                            }
                        }
                        presenter.deleteItem(deleteItems);
                        mainRVAdapter.setSizeSelect(0);
                        modeSelect = false;
                        setToolbarMode(0);
                        mainRVAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.mM:
                        pasteItems = new ArrayList<>();
                        for (int i = 0; i < mainItems.size(); i++) {
                            if (mainItems.get(i).getStat()) {
                                pasteItems.add(new CutItem(mainItems.get(i).getId(), mainItems.get(i).getType(), mainItems.get(i).getStorageId()));
                                mainItems.get(i).switchStat();
                            }
                        }
                        mainRVAdapter.setSizeSelect(0);
                        modeSelect = false;
                        setToolbarMode(2);
                        mainRVAdapter.notifyDataSetChanged();

                        return true;
                    case R.id.mS:
                        List<MainItem> shareItems = new ArrayList<>();
                        for (int i = 0; i < mainItems.size(); i++) {
                            if (mainItems.get(i).getStat()) {
                                shareItems.add(new MainItem(mainItems.get(i).getId(), mainItems.get(i).getType()));
                                mainItems.get(i).switchStat();
                            }
                        }
                        shareItemsMessage(shareItems);
                        return true;
                    case 1:
                        int itemId = 0;
                        boolean itemType = false;
                        for (int i = 0; i < mainItems.size(); i++) {
                            if (mainItems.get(i).getStat()) {
                                itemId = mainItems.get(i).getId();
                                itemType = mainItems.get(i).getType();
                                mainItems.get(i).switchStat();
                                mainRVAdapter.setSizeSelect(0);
                                modeSelect = false;
                                setToolbarMode(0);
                                mainRVAdapter.notifyDataSetChanged();
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
                        intent.putExtra("mode", MODE_EDIT);
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
        mainRVAdapter.setSizeSelect(0);
        modeSelect = false;
        setToolbarMode(0);
        mainRVAdapter.notifyDataSetChanged();
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
                                flyItem = new FlyVItem();
                                flyItem.setUrl(getPass_item.getUrl());
                                flyItem.setPass(NewAes.decrypt(getPass_item.getPass(), NewAes.decrypt(getPass_item.getClue(), ((AplicationListner) getApplication()).getMasterPass())));
                                flyItem.setLogin(getPass_item.getLogin());
                                if (OpenFlyWindow(flyItem)) {
                                    Uri address = Uri.parse("http://" + flyItem.getUrl());
                                    Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                                    startActivity(openlink);
                                    onStop();
                                }
                                return true;
                            case R.id.menu2:
                                Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
                                intent.putExtra("folder", String.valueOf(p));
                                intent.putExtra("mode", MODE_EDIT);
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
    protected void onStart() {
        super.onStart();
    }

    public void refreshClose() {
        setToolbarMode(0);
        progressL.setVisibility(View.GONE);
    }

    public void refreshStart() {
        progressL.setVisibility(View.VISIBLE);
    }

    public void setToolbarMode(int toolbarStat) {
        this.toolbarStat = toolbarStat;
        switch (toolbarStat) {
            case 0:
                //search
                modeSelect = false;
                modePaste = false;
                toolbarSelect.setVisibility(View.GONE);
                toolbarPaste.setVisibility(View.GONE);
                toolbarSearch.setVisibility(View.VISIBLE);
                pasteItems.clear();
                mainRVAdapter.closeSelect();
                mainRVAdapter.notifyDataSetChanged();
                break;
            case 1:
                //selected
                selectTextView.setText("Выбранно " + mainRVAdapter.getSizeSelect() + " элемент(ов)");
                modeSelect = true;
                toolbarSearch.setVisibility(View.GONE);
                toolbarSelect.setVisibility(View.VISIBLE);
                toolbarPaste.setVisibility(View.GONE);
                break;
            case 2:
                //cut
                modePaste = true;
                toolbarSelect.setVisibility(View.GONE);
                toolbarPaste.setVisibility(View.VISIBLE);
                toolbarSearch.setVisibility(View.GONE);
                pasteTextView.setText("Выбранно " + pasteItems.size() + " элемент(ов)");
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
        searchView.onActionViewCollapsed();
        wayTextView.setVisibility(View.VISIBLE);
        cloakView.setVisibility(View.GONE);

    }

    private void openSearch() {

        wayTextView.setVisibility(View.GONE);
        cloakView.setVisibility(View.VISIBLE);
    }

    private List<SearchItem> LoadPasswordList(String s) {
        return dbWorker.getSomePass(s);
    }

    @SuppressLint("SetTextI18n")
    public void changeWay(String s) {
        wayTextView.setText(wayTextView.getText() + "/" + s);
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

        String s = wayTextView.getText().toString();
        s = s.substring(0, s.lastIndexOf('/'));
        wayTextView.setText(s);
        Log.d("way", String.valueOf(presenter.getWayPosition()));
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
        if (fabLayout.getVisibility() == View.VISIBLE) {
            Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.rotate_m45);
            Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.ftexte);
            anim2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    fabLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            fabLayout.startAnimation(anim2);
            createFab.startAnimation(anim1);
        } else {
            Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.rotate_45);
            Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.ftexts);
            anim1.setFillAfter(true);
            fabLayout.setVisibility(View.VISIBLE);
            createFab.startAnimation(anim1);
            fabLayout.startAnimation(anim2);
        }

    }

    @Override
    public void onBackPressed() {
        if (modeSelect) {
            modeSelect = false;
            mainRVAdapter.setSizeSelect(0);
            setToolbarMode(0);
        } else {
            if (searchView.isFocused()) {
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
        progressL.setVisibility(View.GONE);
        refreshLayout.setRefreshing(false);
        mainItems.clear();
        if(toolbarStat!=2)
        setToolbarMode(0);
        mainItems.addAll(dbWorker.loadData(presenter.getWayPosition()));
        mainRVAdapter.notifyDataSetChanged();
    }


    public void setUserInfo() {
        profileDrawerItem.withEmail(LoadText.getText(this, "email"));
        headerResult.updateProfile(profileDrawerItem);
    }

    public void setRefresh(boolean b) {
        refreshLayout.setRefreshing(false);
        progressL.setVisibility(View.GONE);
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