package com.webant.password.manager.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.webant.password.manager.DBase.DBWorker;
import com.webant.password.manager.ItemModel.MainItem;
import com.webant.password.manager.R;
import com.webant.password.manager.ScrollingTextView;
import com.webant.password.manager.ServiceCommandRecycler.RvServiceCommandAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivityRecyclerViewAdapter extends RecyclerView.Adapter<MainActivityRecyclerViewAdapter.PersonViewHolder> {
    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView namepass, folderContent;
        ScrollingTextView tag;
        ImageView photo, st;
        ScrollingTextView scrollingTextView;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            namepass = itemView.findViewById(R.id.cvpass);
            tag = itemView.findViewById(R.id.cvtg);
            folderContent = itemView.findViewById(R.id.folderСontent);
            photo = itemView.findViewById(R.id.photo);
            st = itemView.findViewById(R.id.cvst);
            scrollingTextView = itemView.findViewById(R.id.cvtg);
        }
    }

    List<MainItem> mainItems = new ArrayList<>();
    DBWorker dbWorker;
    int sizeSelected;
    Context context;
    MainClick listner;

    public interface MainClick {
        void longClick(View v, int id);

        void click(View v, int id);
    }

    public MainActivityRecyclerViewAdapter(Context context, List<MainItem> items, MainClick listner, DBWorker dbWorker) {
        this.listner = listner;
        this.context = context;
        this.mainItems = items;
        this.dbWorker = dbWorker;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mainitem, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonViewHolder personViewHolder, @SuppressLint("RecyclerView") final int i) {
        MainItem item =mainItems.get(i);
        personViewHolder.namepass.setText(item.getName());
        if (item.getType()) {
            personViewHolder.photo.setImageResource(R.drawable.folder);
            if (item.getAmountParent() != 0) {
                personViewHolder.folderContent.setText(String.valueOf(item.getAmountParent()));
                personViewHolder.folderContent.setVisibility(View.VISIBLE);
            }
                personViewHolder.tag.setText(item.getTag());
        } else {
            personViewHolder.folderContent.setVisibility(View.GONE);
            personViewHolder.tag.setText(item.getTag());
            personViewHolder.tag.setTextColor(Color.parseColor("#eb919191"));
            personViewHolder.photo.setImageResource(R.drawable.keyb);
        }
        if (item.getStat())
            personViewHolder.itemView.setBackgroundResource(R.color.pngBlue);
        else
            personViewHolder.itemView.setBackgroundResource(R.color.pngPng);
        personViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listner.longClick(v, i);
                return false;
            }
        });
        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.click(v, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainItems.size();
    }

    public int getSizeSelect() {
        return sizeSelected;
    }

    public void setSizeSelect(int size) {
        sizeSelected = size;
    }

    public int selectItem(int id) {
        if (mainItems.get(id).switchStat()) {
            sizeSelected++;
        } else {
            sizeSelected--;
        }
        notifyDataSetChanged();
        return sizeSelected;
    }

    public void closeSelect() {
        if (mainItems != null) {
            for (int i = 0; i < mainItems.size(); i++) {
                mainItems.get(i).setStat(false);
            }
            sizeSelected = 0;
        }
    }

    public List<MainItem> getItems() {
        return mainItems;
    }
}
