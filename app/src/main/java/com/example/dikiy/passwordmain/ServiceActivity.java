package com.example.dikiy.passwordmain;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dikiy.passwordmain.Adapters.Get.GetService_Items;
import com.example.dikiy.passwordmain.RecyclerView.RecyclerItemClickListener;
import com.example.dikiy.passwordmain.Model.ServiceModel;
import com.example.dikiy.passwordmain.Presenters.ServicePresenter;
import com.example.dikiy.passwordmain.ServiceRecycler.RvServiceAdapter;

import java.util.ArrayList;
import java.util.List;

public class ServiceActivity extends AppCompatActivity {
    RecyclerView serviceRv;
    ImageView addService;
    RvServiceAdapter adapter;
    List<GetService_Items> list=new ArrayList<>();
    ServicePresenter presenter;
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity);


        init();
    }

    private void init() {
        refreshLayout=findViewById(R.id.swipeRefreshS);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getService();
            }
        });
        serviceRv=findViewById(R.id.serviceRv);
        addService=findViewById(R.id.addService);
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ServiceActivity.this,AddServiceActivity.class),1234);
            }
        });
        RecyclerView.LayoutManager ll = new LinearLayoutManager(this);
        serviceRv.setLayoutManager(ll);
        serviceRv.setHasFixedSize(true);

        adapter = new RvServiceAdapter(list);
        serviceRv.setAdapter(adapter);

        serviceRv.addOnItemTouchListener(
                new RecyclerItemClickListener(this,serviceRv, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent=new Intent(ServiceActivity.this,ServiceCommandActivity.class);
                        intent.putExtra("id",String.valueOf(list.get(position).getId()));
                        startActivityForResult(intent,1234);

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

        ServiceModel model = new ServiceModel();
        presenter = new ServicePresenter(model);
        presenter.attachView(this);
        presenter.viewIsReady();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==-1){
        presenter.getService();
        }
    }
    public void refresh(List<GetService_Items> list){
        this.list.clear();
        this.list.addAll(list);
            refreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();

    }

    public void fail() {
    refreshLayout.setRefreshing(false);
        Toast.makeText(this,"server fail",Toast.LENGTH_SHORT).show();
    }


    public void error() {
        refreshLayout.setRefreshing(false);
        Toast.makeText(this,"server error",Toast.LENGTH_SHORT).show();
    }
}
