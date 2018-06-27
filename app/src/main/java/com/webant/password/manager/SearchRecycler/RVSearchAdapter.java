package com.webant.password.manager.SearchRecycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.webant.password.manager.Adapters.Get.GetService_Items;
import com.webant.password.manager.Adapters.Model.SearchItem;
import com.webant.password.manager.R;
import com.webant.password.manager.RecyclerView.MainActivityRecyclerViewAdapter;
import com.webant.password.manager.ServiceRecycler.RvServiceAdapter;

import java.util.List;

/**
 * Created by dikiy on 13.04.2018.
 */

public class RVSearchAdapter extends RecyclerView.Adapter<RVSearchAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        PersonViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvSearchItem);
        }
    }

    List<SearchItem> mainItems;
    SearchClick listner;
    public interface SearchClick {
        void click(int id);
    }
    public RVSearchAdapter(List<SearchItem> mainItems,SearchClick listner) {
        this.listner=listner;
        this.mainItems = mainItems;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, final int i) {
        personViewHolder.name.setText(mainItems.get(i).getName());
        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.click(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainItems.size();
    }

}

