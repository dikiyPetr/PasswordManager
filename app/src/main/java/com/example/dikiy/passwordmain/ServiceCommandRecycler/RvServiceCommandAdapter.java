package com.example.dikiy.passwordmain.ServiceCommandRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dikiy.passwordmain.Adapters.Get.GetService_Items_Commands;
import com.example.dikiy.passwordmain.R;

import java.util.List;

/**
 * Created by dikiy on 13.04.2018.
 */

public class RvServiceCommandAdapter extends RecyclerView.Adapter<RvServiceCommandAdapter.PersonViewHolder> {




public static class PersonViewHolder extends RecyclerView.ViewHolder {

   TextView name,url,size;


    PersonViewHolder(View itemView) {


        super(itemView);
        name =  itemView.findViewById(R.id.serviceName);
        url = itemView.findViewById(R.id.serviceUrl);
        size =itemView.findViewById(R.id.serviceSize);


    }


}


    List<GetService_Items_Commands> mainItems;

    public RvServiceCommandAdapter(List<GetService_Items_Commands> list) {


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
        personViewHolder.url.setText(mainItems.get(i).getMethod());
        personViewHolder.size.setText("");



    }

    @Override
    public int getItemCount() {
        return mainItems.size();
    }

}

