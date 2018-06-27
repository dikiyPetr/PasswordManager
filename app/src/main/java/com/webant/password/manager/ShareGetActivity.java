package com.webant.password.manager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.webant.password.manager.Adapters.Get.GetPass_Item;
import com.webant.password.manager.Adapters.Model.ShareGetItems;
import com.webant.password.manager.DBase.DBWorker;
import com.webant.password.manager.Model.ShareGetModel;
import com.webant.password.manager.Presenters.ShareGetPresenter;
import com.webant.password.manager.RecyclerView.ShareGetRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShareGetActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ShareGetRecyclerViewAdapter adapter;
    List<ShareGetItems> items = new ArrayList<>();
    ShareGetPresenter presenter;
    List<String> clueList = new ArrayList<>();
    ImageView back;
    SwipeRefreshLayout refreshLayout;
    List<String> idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_get);

        init();
    }

    private void init() {
        refreshLayout = findViewById(R.id.refresh);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShareGetActivity.this, MainActivity.class));
                finish();
            }
        });
        recyclerView = findViewById(R.id.shareGetRecyclerView);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        adapter = new ShareGetRecyclerViewAdapter(items, this, new ShareGetRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                presenter.addPassword(items.get(position), position);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                items.clear();
                presenter.viewIsReady(idList);
            }
        });
        recyclerView.setAdapter(adapter);
        Uri uri = getIntent().getData();
        ShareGetModel usersModel = new ShareGetModel();
        presenter = new ShareGetPresenter(usersModel);
        presenter.attachView(this);
        if (uri != null && uri.getQueryParameters("id") != null && uri.getQueryParameters("clue") != null) {
            idList = uri.getQueryParameters("id");
            clueList = uri.getQueryParameters("clue");
            presenter.viewIsReady(idList);
        }


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    public void addCard(GetPass_Item item, int id) {
        refreshLayout.setRefreshing(false);
        DBWorker dbWorker = new DBWorker(this);
        if (!dbWorker.findPass(item.getId())) {
            items.add(new ShareGetItems(item.getId(), item.getName(), item.getUrl(), item.getLogin(), Uri.decode(clueList.get(id))));
            adapter.notifyDataSetChanged();
        }
    }

    public void deleteCart(int id) {
        refreshLayout.setRefreshing(false);
        items.remove(id);
        adapter.notifyDataSetChanged();
    }

    public void error() {
        refreshLayout.setRefreshing(false);
    }

    public void fail() {
        refreshLayout.setRefreshing(false);
    }

    public void setRefresh(boolean refresh) {
        refreshLayout.setRefreshing(refresh);
    }
}
