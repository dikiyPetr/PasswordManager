package com.example.dikiy.passwordmain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dikiy.passwordmain.RecyclerView.RecyclerAdapter;
import com.example.dikiy.passwordmain.RecyclerView.RecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 23.03.2018.
 */

public class FolderActivity extends AppCompatActivity {
    ImageView iEdit,iAccept,iAddgrup,iAddtag;
    EditText etName,etLog,etAddgrup,etAddtag;
    Button bAccept;

    boolean mode=false;

    private RecyclerView rvTag, rvGrup;
    private RecyclerAdapter adapterTag, adapterGrup;
    private List<RecyclerItem> listTag = new ArrayList<>();
    private List<RecyclerItem> listGrup = new ArrayList<>();
    private List<RecyclerItem> listTagD = new ArrayList<>();
    private List<RecyclerItem> listGrupD = new ArrayList<>();
    private RecyclerItem movieGrup, movieTag;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(0,R.anim.flye);
        setContentView(R.layout.create_folder_activity);
        init();
    }
    private void setMode(){
        if(mode){




        }else{
            iEdit.setVisibility(View.GONE);
            iAccept.setVisibility(View.GONE);




        }
    }
    private void init() {

        iEdit= findViewById(R.id.editandclose);
        iAccept= findViewById(R.id.acept);
        iAddgrup= findViewById(R.id.addgrupb);
        iAddtag= findViewById(R.id.addtagb);
        etName= findViewById(R.id.etname);
        etLog = findViewById(R.id.etlog);
        etAddgrup= findViewById(R.id.addgrup);
        etAddtag=findViewById(R.id.addtag);
        bAccept= findViewById(R.id.addpass);







        //init rvGrup
        rvGrup= findViewById(R.id.rv2);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvGrup.setLayoutManager(layoutManager);
        adapterGrup = new RecyclerAdapter(listGrup);
        adapterGrup.switchMode();
        rvGrup.setAdapter(adapterGrup);

        //add item in rvGrup
        iAddgrup.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if(etAddgrup.getText().length()!=0) {
                                               movieGrup = new RecyclerItem(String.valueOf(etAddgrup.getText()));
                                               listGrup.add(movieGrup);
                                               adapterGrup.notifyDataSetChanged();
                                               rvGrup.scrollBy(1000000000, 1000000000);
                                               etAddgrup.setText("");
                                           }
                                       }
                                   }
        );



        //init rvTag
        rvTag= findViewById(R.id.rv);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvGrup.setLayoutManager(layoutManager2);
        rvTag.setLayoutManager(layoutManager);
        adapterTag = new RecyclerAdapter(listTag);
        adapterTag.switchMode();
        rvTag.setAdapter(adapterTag);

        //add item in rvTag
        iAddtag.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(etAddtag.getText().length()!=0) {
                                                movieTag = new RecyclerItem(String.valueOf(etAddtag.getText()));
                                                listTag.add(movieTag);
                                                adapterTag.notifyDataSetChanged();
                                                rvTag.scrollBy(1000000000, 1000000000);
                                                etAddtag.setText("");
                                            }
                                        }
                                    }
        );





































    }
}
