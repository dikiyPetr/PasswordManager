package com.example.dikiy.passwordmain.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.dikiy.passwordmain.DBase.DBWorker;
import com.example.dikiy.passwordmain.ItemModel.MainItem;
import com.example.dikiy.passwordmain.R;
import com.example.dikiy.passwordmain.ScrollingTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SuppressWarnings("Convert2streamapi")
public class MainActivityRecyclerViewAdapter extends RecyclerView.Adapter<MainActivityRecyclerViewAdapter.PersonViewHolder> {


    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView namepass;
        ScrollingTextView tag;
        TextView data;
        ImageView photo, st;
        ScrollingTextView scrollingTextView;

        PersonViewHolder(View itemView) {


            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            namepass = itemView.findViewById(R.id.cvpass);
            tag = itemView.findViewById(R.id.cvtg);
            data = itemView.findViewById(R.id.cvdata);
            photo = itemView.findViewById(R.id.photo);
            st = itemView.findViewById(R.id.cvst);
            scrollingTextView = itemView.findViewById(R.id.cvtg);


        }


    }


    public static OnItemTouchListener listener;

    public interface OnItemTouchListener {
        void onItemClick(int i);
    }

    List<MainItem> mainItems = new ArrayList<>();
    int selectItems;
    Context context;

    public MainActivityRecyclerViewAdapter(Context context, List<MainItem> items) {
        this.context = context;
        this.mainItems = items;


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {


        super.onAttachedToRecyclerView(recyclerView);


    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.v("123457", String.valueOf(i));

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
            if(!mainItems.get(i).getTag().equals("")) {
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
            personViewHolder.tag.setText(mainItems.get(i).getTag());
            personViewHolder.tag.setTextColor(Color.parseColor("#eb919191"));
            personViewHolder.photo.setImageResource(R.drawable.keyb);
        }
        if (mainItems.get(i).getStat()) {
            personViewHolder.itemView.setBackgroundResource(R.color.pngBlue);

        } else {
            personViewHolder.itemView.setBackgroundResource(R.color.pngPng);
        }


    }

    @Override
    public int getItemCount() {
        return mainItems.size();
    }

    public int getSizeSelect() {
        return selectItems;
    }

    public void setSizeSelect(int size) {
        selectItems = size;
    }

    public int selectItem(int id) {

        if (mainItems.get(id).switchStat()) {
            selectItems++;
        } else {
            selectItems--;
        }
        notifyDataSetChanged();
        Log.v("1233333333333333", String.valueOf(selectItems));
        return selectItems;
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

//    public void addItem(String name,String categ) {
//       Log.v("123457623242", String.valueOf(getItemCount()));
//    this.mainItems.add(getItemCount(),new MainItem(name,categ,getItemCount()));
//
//        super.notifyItemInserted(getItemCount());
//    }

//
//    public boolean deleteItem(String s) {
//        for(int l=0;l<=getItemCount();l++) {
//            if(this.mainItems.get(l).getName() ==s){
//                Log.v("322222","delete"+ mainItems.get(l).getName());
//                this.mainItems.remove(l);
//                super.notifyItemRemoved(l);
//                return true;
//            }
//
//        }
//
//
//        return false;
//    }
}
