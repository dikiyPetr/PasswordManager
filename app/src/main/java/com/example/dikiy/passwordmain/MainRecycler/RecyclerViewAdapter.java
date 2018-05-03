package com.example.dikiy.passwordmain.MainRecycler;

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
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PersonViewHolder>
{




    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView namepass;
        ScrollingTextView tag;
        TextView data;
        ImageView photo,st;
        ScrollingTextView scrollingTextView;

        PersonViewHolder(View itemView) {


            super(itemView);
            cv =  itemView.findViewById(R.id.cv);
            namepass =  itemView.findViewById(R.id.cvpass);
            tag =  itemView.findViewById(R.id.cvtg);
            data = itemView.findViewById(R.id.cvdata);
            photo=itemView.findViewById(R.id.photo);
            st=itemView.findViewById(R.id.cvst);
            scrollingTextView=itemView.findViewById(R.id.cvtg);


        }


    }


    public static OnItemTouchListener listener;
    public interface OnItemTouchListener {
        void onItemClick(int i);
    }
    List<MainItem> mainItems=new ArrayList<>();
    int selectItems;

    public RecyclerViewAdapter(List<MainItem> mainItems) {


        this.mainItems = mainItems;


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

        DBWorker dbWorker=new DBWorker();
       List<String> list= dbWorker.getTagName(Arrays.asList(mainItems.get(i).getTag().split(",")));

       personViewHolder.scrollingTextView.setText(list.toString().substring(1,list.toString().length()-1));
        personViewHolder.namepass.setText(mainItems.get(i).getName()+" id="+mainItems.get(i).getId());
        if(mainItems.get(i).getType()) {
            personViewHolder.photo.setImageResource(R.drawable.folder);
        }else {
            personViewHolder.photo.setImageResource(R.drawable.keyb);
        }
        if(mainItems.get(i).getStat()){
            personViewHolder.itemView.setBackgroundResource(R.color.pngBlue);

        }else{
            personViewHolder.itemView.setBackgroundResource(R.color.pngPng);
        }



    }

    @Override
    public int getItemCount() {
        return mainItems.size();
    }
    public int getSizeSelect(){
        return selectItems;
    }
    public void setSizeSelect(int size){
        selectItems=size;
    }
    public int selectItem(int id){

  if(mainItems.get(id).switchStat()){
      selectItems++;
  }else{
      selectItems--;
  }
    notifyDataSetChanged();
    Log.v("1233333333333333", String.valueOf(selectItems));
   return selectItems;
    }
    public void closeSelect() {
        if(mainItems!=null){
        for(int i=0;i<mainItems.size();i++){
            mainItems.get(i).setStat(false);
        }}
    }
    public List<MainItem> getItems(){
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
