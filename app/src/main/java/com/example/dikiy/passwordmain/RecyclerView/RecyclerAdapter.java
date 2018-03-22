package com.example.dikiy.passwordmain.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dikiy.passwordmain.R;

import java.util.List;

/**
 * Created by dikiy on 12.02.2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<RecyclerItem> mDataset;
    boolean modeEdit=false;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public ImageView imageView;
        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textView2);
            imageView = v.findViewById(R.id.imageView);
        }
    }

    public RecyclerAdapter(List<RecyclerItem> moviesList) {
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
        RecyclerItem movie = mDataset.get(position);
        holder.mTextView.setText(movie.getName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataset.remove(position);
               notifyDataSetChanged();
//               holder.imageView.setVisibility(View.GONE);
            }
        });
        if(!modeEdit){
            holder.imageView.setVisibility(View.GONE);
        }else{
            holder.imageView.setVisibility(View.VISIBLE);
        }
    }

    public boolean switchMode(){

        if(modeEdit){
            modeEdit =false;
            return false;
        }else {
            modeEdit =true;
            return true;}
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

