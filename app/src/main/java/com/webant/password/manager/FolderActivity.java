package com.webant.password.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;


import com.webant.password.manager.Adapters.Get.GetFolder_Item;
import com.webant.password.manager.DBase.DBWorker;
import com.webant.password.manager.Model.FolderModel;
import com.webant.password.manager.Presenters.FolderPresenter;
import com.webant.password.manager.RecyclerView.TagOrGroupRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 23.03.2018.
 */

public class FolderActivity extends AppCompatActivity {
    TextInputLayout nameTIL;
    ImageView iEdit, iAccept, iAddtag, iBack;
    EditText name;
    AutoCompleteTextView etAddtag;
    private Button addFolder;

    FrameLayout progressL;
    private RecyclerView rvTag;
    private TagOrGroupRecyclerAdapter adapterTag;
    private List<String> tagsList = new ArrayList<>();
    private List<String> tagsListEdit = new ArrayList<>();
    private List<Integer> tagsListEditMode = new ArrayList<>();
    FolderPresenter presenter;
    private int thisId = 0;
    private int mode = 0;
    final Boolean tag = false;
    Intent intent = new Intent();
    ArrayAdapter<String> tagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(0, R.anim.flye);
        setContentView(R.layout.create_folder_activity);
        intent = getIntent();

        thisId = Integer.parseInt(intent.getStringExtra("folder"));
        mode = Integer.parseInt(intent.getStringExtra("mode"));
        init();
    }

    private void setMode() {
        if (mode == 0) {
            iAccept.setVisibility(View.GONE);
        } else {
            addFolder.setVisibility(View.GONE);
            DBWorker dbWorker = new DBWorker(this);
            GetFolder_Item item = dbWorker.getFolder(thisId);
            name.setText(item.getName());

            if (!item.getTags().get(0).equals("")) {
                List<String> nameT = new ArrayList<>();
                nameT.addAll(dbWorker.getTagName(item.getTags()));
                for (int i = 0; i < nameT.size(); i++) {
                    tagAdapter.remove(nameT.get(i));
                    tagsList.add(nameT.get(i));
                    tagsListEditMode.add(0);
                }
                tagsListEdit.addAll(tagsList);
            }

        }
    }

    public void cancelRefesh() {
        progressL.setVisibility(View.GONE);
        addFolder.setEnabled(true);
    }

    private void init() {
        progressL = findViewById(R.id.progressl);
        iAccept = findViewById(R.id.accept);
        addFolder = findViewById(R.id.addFolder);
        iBack = findViewById(R.id.backImageView);
        iAddtag = findViewById(R.id.addtagb);
        name = findViewById(R.id.name);
        nameTIL = findViewById(R.id.nameTIL);
        etAddtag = findViewById(R.id.addtag);
        iAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressL.setVisibility(View.VISIBLE);
                addFolder.setEnabled(false);
                presenter.updateFolder(thisId, name.getText().toString(), tagsListEdit, tagsListEditMode);
            }
        });
        iBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressL.setVisibility(View.VISIBLE);
                addFolder.setEnabled(false);
                presenter.createFolder(name.getText().toString(), thisId, tagsList);

            }
        });

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        //init rvTag
        rvTag = findViewById(R.id.rv);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTag.setLayoutManager(layoutManager);
        adapterTag = new TagOrGroupRecyclerAdapter(tagsList, new TagOrGroupRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                if (mode == 1) {
                    tagsListEditMode.set(tagsListEdit.indexOf(s), tagsListEditMode.get(tagsListEdit.indexOf(s)) - 1);
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
                                           if (etAddtag.getText().length() != 0) {
                                               if (addItemInRV(etAddtag.getText().toString(), tagsList)) {
                                                   String s = etAddtag.getText().toString();
                                                   if (mode == 1) {
                                                       if (tagsListEdit.indexOf(s) == -1) {
                                                           tagsListEdit.add(s);
                                                           tagsListEditMode.add(1);
                                                       } else {
                                                           tagsListEditMode.set(tagsListEdit.indexOf(s), tagsListEditMode.get(tagsListEdit.indexOf(s)) + 1);
                                                       }
                                                   }
                                                   tagAdapter.remove(s);
                                                   tagsList.add(s);
                                                   adapterTag.notifyDataSetChanged();
                                                   rvTag.scrollBy(1000000000, 1000000000);
                                                   etAddtag.setText("");
                                               }
                                           }
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


        etAddtag.setAdapter(tagAdapter);
        etAddtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAddtag.getText().length() == 0) {


                    etAddtag.showDropDown();
                }
            }
        });
        setMode();


    }

    private boolean addItemInRV(String text, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(text)) {
                return false;
            }

        }
        return true;
    }

    public void execute() {
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setError(String error) {
        nameTIL.setError(error);
    }
}
