package com.example.dikiy.passwordmain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dikiy.passwordmain.Adapters.Get.GetFolder_Item;
import com.example.dikiy.passwordmain.DBase.DBWorker;
import com.example.dikiy.passwordmain.Model.FolderModel;
import com.example.dikiy.passwordmain.Presenters.FolderPresenter;
import com.example.dikiy.passwordmain.RecyclerView.TagOrGroupRecyclerAdapter;
import com.example.dikiy.passwordmain.Adapters.Model.TagOrGroupRecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 23.03.2018.
 */

public class FolderActivity extends AppCompatActivity {
    ImageView iEdit,iAccept,iAddgrup,iAddtag;
    EditText etName,etLog;
    AutoCompleteTextView etAddgroup,etAddtag;
    private Button bAccept;


    private RecyclerView rvTag, rvGroup;
    private TagOrGroupRecyclerAdapter adapterTag, adapterGroup;
    private List<String> listTag = new ArrayList<>();
    private List<String> listGroup = new ArrayList<>();
    private List<String> listTagD = new ArrayList<>();
    private List<String> listGroupD = new ArrayList<>();
    private TagOrGroupRecyclerItem movieGroup, movieTag;
    FolderPresenter presenter;
    private int thisId=0;
    private int mode=0;
    final Boolean tag=false,group=true;
    Intent intent = new Intent();
    ArrayAdapter tagAdapter,groupAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(0,R.anim.flye);
        setContentView(R.layout.create_folder_activity);
        bAccept= findViewById(R.id.accept);
        intent=getIntent();

        thisId= Integer.parseInt(intent.getStringExtra("folder"));
        mode =Integer.parseInt(intent.getStringExtra("mode"));
        init();
    }
    private void setMode(){
        if(mode==0){

        }else{

            bAccept.setVisibility(View.GONE);
//            setEnable(false);
            DBWorker dbWorker=new DBWorker(this);
            GetFolder_Item item=dbWorker.getFolder(thisId);
               etName.setText(item.getName());
            Log.v("123asdasdaszx",item.getGroups().toString());
            if(!item.getGroups().get(0).equals("")) {
                List<String> nameG=new ArrayList<>();
                nameG.addAll(dbWorker.getGroupName(item.getGroups()));
                for(int i=0;i<nameG.size();i++){
                    Log.v("123123axzcass",nameG.get(i));
                    listGroup.add(nameG.get(i));

                    listGroupD.add(nameG.get(i));
                }
            }
            if(!item.getTags().get(0).equals("")) {
                List<String> nameT=new ArrayList<>();
                nameT.addAll(dbWorker.getTagName(item.getTags()));
                for(int i=0;i<nameT.size();i++){
                    Log.v("123123axzcass",nameT.get(i));
                    listTag.add(nameT.get(i));

                    listTagD.add(nameT.get(i));
                }
            }

        }
    }
    private void setEnable(boolean b){
        etName.setEnabled(b);
        etAddtag.setEnabled(b);
        etAddgroup.setEnabled(b);
        etLog.setEnabled(b);
        iAddtag.setEnabled(b);
        iAddgrup.setEnabled(b);

    }
    private void init() {

        iEdit= findViewById(R.id.editandclose);
        iAccept= findViewById(R.id.acept);
        iAddgrup= findViewById(R.id.addgrupb);
        iAddtag= findViewById(R.id.addtagb);
        etName= findViewById(R.id.etname);
        etLog = findViewById(R.id.etlog);
        etAddgroup = findViewById(R.id.addgrup);
        etAddtag=findViewById(R.id.addtag);






        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> tagList = new ArrayList<>();
                List<String> groupList = new ArrayList<>();
                for (int i = 0; i < listTag.size(); i++) {
                    tagList.add(listTag.get(i));
                }
                for (int i = 0; i < listGroup.size(); i++) {
                    groupList.add(listGroup.get(i));
                }
               presenter.createFolder(etName.getText().toString(),thisId,tagList,groupList);
               bAccept.setEnabled(false);
            }
        });

        rvGroup= findViewById(R.id.rv2);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvGroup.setLayoutManager(layoutManager);
        adapterGroup = new TagOrGroupRecyclerAdapter(listGroup, new TagOrGroupRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                if(mode==1) {
                    presenter.removeTagOrGroup(s, thisId, group);
                }
                groupAdapter.add(s);
            }
        });
        adapterGroup.modeEdit(true);
        rvGroup.setAdapter(adapterGroup);






        //init rvTag
        rvTag= findViewById(R.id.rv);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvGroup.setLayoutManager(layoutManager2);
        rvTag.setLayoutManager(layoutManager);
        adapterTag = new TagOrGroupRecyclerAdapter(listTag, new TagOrGroupRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                if(mode==1) {
                    presenter.removeTagOrGroup(s, thisId, tag);
                }
                tagAdapter.add(s);
            }
        });
        adapterTag.modeEdit(true);
        rvTag.setAdapter(adapterTag);

        //add item in rvTag
        iAddtag.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(etAddtag.getText().length()!=0) {
                                                if( addItemInRV(etAddtag.getText().toString(),listTag)) {
                                                    if(mode==1) {
                                                        presenter.addTagOrGroup(tag, etAddtag.getText().toString(), thisId);
                                                    }
                                                    tagAdapter.remove(etAddtag.getText().toString());
                                                    listTag.add(etAddtag.getText().toString());
                                                    adapterTag.notifyDataSetChanged();
                                                    rvTag.scrollBy(1000000000, 1000000000);
                                                    etAddtag.setText("");
                                                }}
                                        }
                                    }
        );
        iAddgrup.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if(etAddgroup.getText().length()!=0) {
                                               if( addItemInRV(etAddgroup.getText().toString(), listGroup)) {
                                                   if(mode==1) {
                                                       presenter.addTagOrGroup(group, etAddgroup.getText().toString(), thisId);
                                                   }
                                                   groupAdapter.remove(etAddgroup.getText().toString());
                                                   listGroup.add(etAddgroup.getText().toString());
                                                   adapterGroup.notifyDataSetChanged();
                                                   rvGroup.scrollBy(1000000000, 1000000000);
                                                   etAddgroup.setText("");
                                               }}
                                       }
                                   }
        );
        FolderModel usersModel = new FolderModel();
        presenter = new FolderPresenter(usersModel);
        presenter.attachView(this);
        presenter.viewIsReady();


        DBWorker dbWorker = new DBWorker(this);
        tagAdapter = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, dbWorker.getTag());
        groupAdapter= new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, dbWorker.getGroup());


        etAddtag.setAdapter(tagAdapter);
        etAddgroup.setAdapter(groupAdapter);
        etAddtag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(etAddtag.getText().length()==0){


                    etAddtag.showDropDown();}
                return false;
            }
        });
        etAddgroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(etAddgroup.getText().length()==0){
                    etAddgroup.showDropDown();}
                return false;
            }
        });
        setMode();



    }
    private boolean addItemInRV(String text, List<String> list) {
        for(int i=0;i<list.size();i++){
            if(list.get(i).equals(text)){
                return false;
            }

        }
        return true;
    }
    public void execute() {
        setResult(RESULT_OK, intent);
        finish();
    }
    public void setRefreshStatus() {
        setResult(RESULT_OK, intent);
    }
}
