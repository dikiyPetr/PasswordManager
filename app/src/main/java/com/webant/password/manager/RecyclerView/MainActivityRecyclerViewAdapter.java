package com.webant.password.manager.RecyclerView;

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
    int sizeSelected;
    Context context;
    MainClick listner;

    public interface MainClick {
        void longClick(View v, int id);

        void click(View v, int id);
    }

    public MainActivityRecyclerViewAdapter(Context context, List<MainItem> items, MainClick listner) {
        this.listner = listner;
        this.context = context;
        this.mainItems = items;
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
    public void onBindViewHolder(final PersonViewHolder personViewHolder, final int i) {
        DBWorker dbWorker = new DBWorker(context);
        personViewHolder.namepass.setText(mainItems.get(i).getName());
        if (mainItems.get(i).getType()) {
            personViewHolder.photo.setImageResource(R.drawable.folder);
            if (mainItems.get(i).getAmountParent() != 0){
                personViewHolder.folderContent.setText(String.valueOf(mainItems.get(i).getAmountParent()));
                personViewHolder.folderContent.setVisibility(View.VISIBLE);}
            if (!mainItems.get(i).getTag().equals("")) {
                List<String> list = dbWorker.getTagName(Arrays.asList(mainItems.get(i).getTag().split(",")));
                List<String> tags = Arrays.asList(list.toString().substring(1, list.toString().length() - 1).split(","));
                String stringTag = "";
                for (int i1 = 0; i1 < tags.size(); i1++) {
                    if (!tags.get(i1).isEmpty())
                        stringTag += "#" + tags.get(i1) + " ";
                }
                personViewHolder.tag.setText(stringTag);
            }
        } else {
            personViewHolder.folderContent.setVisibility(View.GONE);
            personViewHolder.tag.setText(mainItems.get(i).getTag());
            personViewHolder.tag.setTextColor(Color.parseColor("#eb919191"));
            personViewHolder.photo.setImageResource(R.drawable.keyb);
        }
        if (mainItems.get(i).getStat()) {
            personViewHolder.itemView.setBackgroundResource(R.color.pngBlue);

        } else {
            personViewHolder.itemView.setBackgroundResource(R.color.pngPng);
        }
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
        }
    }

    public List<MainItem> getItems() {
        return mainItems;
    }
}
