package com.webant.password.manager.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.webant.password.manager.R;

import java.util.List;

/**
 * Created by dikiy on 12.02.2018.
 */

public class TagOrGroupRecyclerAdapter extends RecyclerView.Adapter<TagOrGroupRecyclerAdapter.ViewHolder> {
    List<String> mDataset;
    boolean modeEdit=false;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public ImageView imageView;
        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textView2);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(String s);
    }


    private final OnItemClickListener listener;
    public TagOrGroupRecyclerAdapter(List<String> moviesList, OnItemClickListener listener) {

        this.listener=listener;
        this.mDataset = moviesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.create_password_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String movie = mDataset.get(position);
        holder.mTextView.setText(movie);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modeEdit){
                listener.onItemClick(movie);
                mDataset.remove(position);
               notifyDataSetChanged();
//               holder.imageView.setVisibility(View.GONE);
            }
            }
        });

    }

    public boolean modeEdit(boolean stat){
        modeEdit =stat;
        return modeEdit;
    }
    @Override
    public int getItemCount() {
        if(mDataset!=null) {
            return mDataset.size();
        }else{
            return 0;
        }
    }
}

