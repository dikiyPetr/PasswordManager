package com.webant.password.manager.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.webant.password.manager.Adapters.Model.ShareGetFolder;
import com.webant.password.manager.Adapters.Model.ShareGetItems;
import com.webant.password.manager.DBase.DBWorker;
import com.webant.password.manager.R;

import java.util.ArrayList;
import java.util.List;

public class ShareGetRecyclerViewAdapter extends RecyclerView.Adapter<ShareGetRecyclerViewAdapter.ViewHolder> {
    private List<ShareGetItems> items;
    private Context context;
    private DBWorker dbWorker;
    private ArrayAdapter<String> foldersAdapter;
    private List<ShareGetFolder> folders = new ArrayList<>();
    private OnItemClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, url, login, way;
        Spinner selectWay;
        Button addPasss;
        ImageView back;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.nameTextView);
            url = v.findViewById(R.id.urlTextView);
            login = v.findViewById(R.id.loginTextView);
            selectWay = v.findViewById(R.id.selectWay);
            addPasss = v.findViewById(R.id.addPasswordButton);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ShareGetRecyclerViewAdapter(List<ShareGetItems> items, Context context, OnItemClickListener listener) {
        this.items = items;
        this.context = context;
        this.listener=listener;
        dbWorker = new DBWorker(context);
        dbWorker.getFolders();
        folders.addAll(dbWorker.getFolders());
        List<String> list = new ArrayList<>();
        list.add("\"В корень\"");
        for (int i = 0; i < folders.size(); i++)
            list.add(folders.get(i).getFolder());
        foldersAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_share_get, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ShareGetItems item = items.get(position);
        holder.name.setText(item.getName());
        holder.url.setText(item.getUrl());
        holder.login.setText(item.getLogin());
        holder.selectWay.setAdapter(foldersAdapter);
        holder.selectWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos == 0) {
                    items.get(position).setWay(0);
                } else {
                    items.get(position).setWay(folders.get(pos - 1).getId());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        holder.addPasss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });

    }

    private List<String> getFolderName(List<ShareGetFolder> folderThisId) {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < folderThisId.size(); i++)
            list.add(folderThisId.get(i).getFolder());
        return list;
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        } else {
            return 0;
        }
    }
}
