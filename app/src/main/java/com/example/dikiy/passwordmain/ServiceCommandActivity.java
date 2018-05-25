package com.example.dikiy.passwordmain;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.dikiy.passwordmain.Adapters.Get.GetService_Items_Commands;
import com.example.dikiy.passwordmain.Model.ServiceCommandModel;
import com.example.dikiy.passwordmain.Presenters.ServiceCommandPresenter;
import com.example.dikiy.passwordmain.ServiceCommandRecycler.RvServiceCommandAdapter;
import com.example.dikiy.passwordmain.ServiceRecycler.RvServiceAdapter;

import java.util.ArrayList;
import java.util.List;

public class ServiceCommandActivity extends AppCompatActivity {
    ImageView createCommand;
    SwipeRefreshLayout refreshLayout;
    RecyclerView  commandRv;
    List<GetService_Items_Commands> list=new ArrayList<>();
    RvServiceCommandAdapter adapter;
    ServiceCommandPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_command);
        init();
    }

    private void init() {
    createCommand=findViewById(R.id.addServiceCommand);
    refreshLayout=findViewById(R.id.swipeRefreshSC);
    commandRv=findViewById(R.id.serviceCommandRv);

    createCommand.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(ServiceCommandActivity.this,AddServiceCommandActivity.class);
            intent.putExtra("id",getIntent().getStringExtra("id"));
            startActivityForResult(intent,1234);

        }
    });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadCommand();
            }
        });
        RecyclerView.LayoutManager ll = new LinearLayoutManager(this);
        commandRv.setLayoutManager(ll);
        commandRv.setHasFixedSize(true);

        adapter = new RvServiceCommandAdapter(list);
        commandRv.setAdapter(adapter);

        ServiceCommandModel model = new ServiceCommandModel();
        presenter = new ServiceCommandPresenter(model);
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    public void load(List<GetService_Items_Commands> list) {
    refreshLayout.setRefreshing(false);
    this.list.clear();
    this.list.addAll(list);
    adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==-1){
            //refresh
        }
    }

    public void fail() {
    refreshLayout.setRefreshing(false);
    }
}
