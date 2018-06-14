package com.example.dikiy.passwordmain;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.Adapters.Model.ShareGetItems;
import com.example.dikiy.passwordmain.Model.ShareGetModel;
import com.example.dikiy.passwordmain.Presenters.ShareGetPresenter;
import com.example.dikiy.passwordmain.RecyclerView.MainActivityRecyclerViewAdapter;
import com.example.dikiy.passwordmain.RecyclerView.ShareGetRecyclerViewAdapter;
import com.example.dikiy.passwordmain.SearchRecycler.RVSearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShareGetActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ShareGetRecyclerViewAdapter adapter;
    List<ShareGetItems> items = new ArrayList<>();
    ShareGetPresenter presenter;
    List<String> clueList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_get);

        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.shareGetRecyclerView);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        adapter = new ShareGetRecyclerViewAdapter(items, this, new ShareGetRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.v("testLog",items.get(position).getName()+"\n"+
                items.get(position).getUrl()+"\n"+
                items.get(position).getLogin()+"\n"+
                items.get(position).getId()+"\n"+
                items.get(position).getWay()+"\n"+
                items.get(position).getClue());
                presenter.addPassword(items.get(position),position);
            }
        });
        recyclerView.setAdapter(adapter);
        Uri uri = getIntent().getData();
        List<String> idList;
        ShareGetModel usersModel = new ShareGetModel();
        presenter = new ShareGetPresenter(usersModel);
        presenter.attachView(this);
        if (uri != null && uri.getQueryParameters("id") != null && uri.getQueryParameters("clue") != null) {
            Log.v("123312asasd", uri.getQueryParameters("id").toString());
            Log.v("123312asasd", uri.getQueryParameters("clue").toString());
            idList = uri.getQueryParameters("id");
            clueList = uri.getQueryParameters("clue");
            presenter.viewIsReady(idList);
        }


    }

    public void addCard(GetPass_Item item, int id) {
        items.add(new ShareGetItems(item.getId(),item.getName(),item.getUrl(),item.getLogin(),clueList.get(id)));
        adapter.notifyDataSetChanged();
    }

    public void deleteCart(int id) {
        items.remove(id);
        adapter.notifyDataSetChanged();
    }
}
