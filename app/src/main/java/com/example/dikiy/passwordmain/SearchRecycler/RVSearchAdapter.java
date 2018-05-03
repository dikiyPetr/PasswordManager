package com.example.dikiy.passwordmain.SearchRecycler;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dikiy.passwordmain.Adapters.Model.SearchItem;
import com.example.dikiy.passwordmain.ItemModel.MainItem;
import com.example.dikiy.passwordmain.MainRecycler.RecyclerViewAdapter;
import com.example.dikiy.passwordmain.R;
import com.example.dikiy.passwordmain.ScrollingTextView;

import java.util.List;

/**
 * Created by dikiy on 13.04.2018.
 */

public class RVSearchAdapter extends RecyclerView.Adapter<RVSearchAdapter.PersonViewHolder> {




public static class PersonViewHolder extends RecyclerView.ViewHolder {

   TextView name;


    PersonViewHolder(View itemView) {


        super(itemView);
        name =  itemView.findViewById(R.id.tvSearchItem);




    }


}


    public static RecyclerViewAdapter.OnItemTouchListener listener;
public interface OnItemTouchListener {
    void onItemClick(int i);
}
    List<SearchItem> mainItems;
    int selectItems;

    public RVSearchAdapter(List<SearchItem> mainItems) {


        this.mainItems = mainItems;


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {


        super.onAttachedToRecyclerView(recyclerView);


    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.v("123457", String.valueOf(i));

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, viewGroup, false);
       PersonViewHolder pvh = new PersonViewHolder(v);


        return pvh;
    }


    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, final int i) {

        personViewHolder.name.setText(mainItems.get(i).getName());



    }

    @Override
    public int getItemCount() {
        return mainItems.size();
    }

}

