package com.webant.password.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.webant.password.manager.Adapters.Get.GetService_Items_Commands;
import com.webant.password.manager.Model.ServiceCommandModel;
import com.webant.password.manager.Presenters.ServiceCommandPresenter;
import com.webant.password.manager.ServiceCommandRecycler.RvServiceCommandAdapter;

import java.util.ArrayList;
import java.util.List;

public class ServiceCommandActivity extends AppCompatActivity {
    ImageView createCommand;
    SwipeRefreshLayout refreshLayout;
    RecyclerView commandRv;
    List<GetService_Items_Commands> list = new ArrayList<>();
    RvServiceCommandAdapter adapter;
    ServiceCommandPresenter presenter;
    TextView title;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_command);
        init();
    }

    private void init() {
        back=findViewById(R.id.backImageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });
        createCommand = findViewById(R.id.addServiceCommand);
        refreshLayout = findViewById(R.id.swipeRefreshSC);
        commandRv = findViewById(R.id.serviceCommandRv);
        title = findViewById(R.id.titleService);
        title.setText(getIntent().getStringExtra("name"));
        createCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceCommandActivity.this, AddServiceCommandActivity.class);
                intent.putExtra("command_id", getIntent().getStringExtra("id"));
                startActivityForResult(intent, 1234);

            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadCommand(getIntent().getStringExtra("id"));
            }
        });
        RecyclerView.LayoutManager ll = new LinearLayoutManager(this);
        commandRv.setLayoutManager(ll);
        commandRv.setHasFixedSize(true);

        adapter = new RvServiceCommandAdapter(list, new RvServiceCommandAdapter.CommandClick() {
            @Override
            public void longClick(View v,int id) {
                refreshLayout.setRefreshing(true);
                showPopupMenu(v, list.get(id).getId());
            }

            @Override
            public void click(View v,int id) {
                Intent intent = new Intent(ServiceCommandActivity.this, AddServiceCommandActivity.class);
                intent.putExtra("command_id", getIntent().getStringExtra("id"));
                intent.putExtra("id", String.valueOf(list.get(id).getId()));
                startActivityForResult(intent, 1234);
            }
        });
        commandRv.setAdapter(adapter);
        ServiceCommandModel model = new ServiceCommandModel();
        presenter = new ServiceCommandPresenter(model);
        presenter.attachView(this);
        presenter.loadCommand(getIntent().getStringExtra("id"));
    }

    public void load(List<GetService_Items_Commands> list) {
        refreshLayout.setRefreshing(false);
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            refreshLayout.setRefreshing(true);
            presenter.loadCommand(getIntent().getStringExtra("id"));
        }
    }

    private void showPopupMenu(View v, final int p) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.deletemenu); // Для Android 4.0
        setRefresh(false);
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                presenter.delete(p);

                                return true;
                            default:
                                return false;
                        }
                    }
                });
        popupMenu.show();
    }

    public void fail() {
        refreshLayout.setRefreshing(false);
    }

    public void deleteError() {
        refreshLayout.setRefreshing(false);
    }

    public void setRefresh(boolean b) {
        refreshLayout.setRefreshing(b);
    }

    public void error() {
        refreshLayout.setRefreshing(false);
    }
}
