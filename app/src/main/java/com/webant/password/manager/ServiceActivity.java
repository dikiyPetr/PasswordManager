package com.webant.password.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.webant.password.manager.Adapters.Get.GetService_Items;
import com.webant.password.manager.DBase.DBWorker;
import com.webant.password.manager.DBase.LoadText;
import com.webant.password.manager.Model.ServiceModel;
import com.webant.password.manager.Presenters.ServicePresenter;
import com.webant.password.manager.ServiceRecycler.RvServiceAdapter;

import java.util.ArrayList;
import java.util.List;

public class ServiceActivity extends AppCompatActivity {
    RecyclerView serviceRv;
    ImageView addService;
    RvServiceAdapter adapter;
    List<GetService_Items> list = new ArrayList<>();
    ServicePresenter presenter;
    SwipeRefreshLayout refreshLayout;
    Toolbar toolbar;
    private ProfileDrawerItem profileDrawerItem;
    private Drawer result;
    private AccountHeader headerResult;
    DBWorker dbWorker;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity);
        init();
        createLeftMenu();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }

    private void init() {
        dbWorker = new DBWorker(this);
        toolbar = findViewById(R.id.toolbar);
        context = this;
        refreshLayout = findViewById(R.id.swipeRefreshS);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getService();
            }
        });
        serviceRv = findViewById(R.id.serviceRv);
        addService = findViewById(R.id.addService);
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ServiceActivity.this, AddServiceActivity.class), 1234);
            }
        });
        RecyclerView.LayoutManager ll = new LinearLayoutManager(this);
        serviceRv.setLayoutManager(ll);
        serviceRv.setHasFixedSize(true);
        adapter = new RvServiceAdapter(list, new RvServiceAdapter.ServiceClick() {
            @Override
            public void longClick(View v, int id) {
                refreshLayout.setRefreshing(true);
                showPopupMenu(v, list.get(id).getId());
            }

            @Override
            public void click(View v, int id) {
                Intent intent = new Intent(ServiceActivity.this, ServiceCommandActivity.class);
                intent.putExtra("id", String.valueOf(list.get(id).getId()));
                intent.putExtra("name", String.valueOf(list.get(id).getName()));
                startActivityForResult(intent, 1234);
            }
        });
        serviceRv.setAdapter(adapter);
        ServiceModel model = new ServiceModel();
        presenter = new ServicePresenter(model);
        presenter.attachView(this);
        presenter.viewIsReady();

    }

    private void showPopupMenu(View v, final int p) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.deletemenu);
        setRefresh(false);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuEdit:
                        Intent intent = new Intent(ServiceActivity.this, AddServiceActivity.class);
                        intent.putExtra("id", String.valueOf(p));
                        startActivityForResult(intent, 1234);
                        return true;
                    case R.id.menuDelete:
                        presenter.delete(p);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
        createLeftMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            presenter.getService();
        }
    }

    public void refresh(List<GetService_Items> list) {
        this.list.clear();
        this.list.addAll(list);
        refreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }


    public void fail() {
        refreshLayout.setRefreshing(false);
    }

    public void error() {
        refreshLayout.setRefreshing(false);
    }

    public void deleteError() {
        refreshLayout.setRefreshing(false);
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
                        new SecondaryDrawerItem().withIdentifier(1).withName(R.string.main_page).withSelectable(false),
                        new SecondaryDrawerItem().withIdentifier(2).withName(R.string.service_page).withSelectable(false),
                        new SecondaryDrawerItem().withIdentifier(3).withName(R.string.setting_page).withSelectable(false),
                        new SecondaryDrawerItem().withIdentifier(4).withName(R.string.action_exit).withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:
                                finish();
                                break;
                            case 3:
                                startActivity(new Intent(context, SettingActivity.class));
                                finish();
                                break;
                            case 4:
                                DialogWindows.exit(context);
                                break;
                        }
                        return false;
                    }
                })
                .build();
        result.setSelection(2);
    }

    public void setRefresh(boolean b) {
        refreshLayout.setRefreshing(b);
    }
}
