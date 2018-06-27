package com.webant.password.manager.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.webant.password.manager.Adapters.Model.ParamsItem;
import com.webant.password.manager.R;
import com.webant.password.manager.Adapters.Model.ParamsItem;
import com.webant.password.manager.R;

import java.util.List;

public class ParamsRecyclerViewAdapter extends RecyclerView.Adapter<ParamsRecyclerViewAdapter.ViewHolder> {
    private List<ParamsItem> items;
    private Context context;
    private OnItemClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        EditText value;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
           value=v.findViewById(R.id.value);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ParamsRecyclerViewAdapter(List<ParamsItem> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.params_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ParamsItem item = items.get(position);
        holder.name.setText(item.getName());
        holder.value.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                items.get(position).setValue(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
