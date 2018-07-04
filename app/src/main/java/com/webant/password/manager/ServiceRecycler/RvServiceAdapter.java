package com.webant.password.manager.ServiceRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webant.password.manager.Adapters.Get.GetService_Items;
import com.webant.password.manager.R;
import com.webant.password.manager.ServiceCommandRecycler.RvServiceCommandAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 13.04.2018.
 */

public class RvServiceAdapter extends RecyclerView.Adapter<RvServiceAdapter.PersonViewHolder> {
    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView name, url;

        PersonViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.serviceName);
            url = itemView.findViewById(R.id.serviceUrl);
        }
    }

    ArrayList<GetService_Items> mainItems;
    ServiceClick listner;
    public interface ServiceClick {
        void longClick(View v,int id);
        void click(View v,int id);
    }
    public RvServiceAdapter(ArrayList<GetService_Items> list, ServiceClick listner) {
        this.listner=listner;
        this.mainItems = list;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, final int i) {
        personViewHolder.name.setText(mainItems.get(i).getName());
        personViewHolder.url.setText(mainItems.get(i).getUrl());
        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.click(v,i);
            }
        });
        personViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listner.longClick(v,i);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainItems.size();
    }

}

