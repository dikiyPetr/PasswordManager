package com.example.dikiy.passwordmain;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by dikiy on 23.03.2018.
 */

public class FolderActivity extends AppCompatActivity {
    ImageView iEdit,iAccept,iAddgrup,iAddtag;
    EditText etName,etLog,etAddgrup,etAddtag;
    Button bAccept;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(0,R.anim.flye);
        setContentView(R.layout.create_folder_acrivity);
        init();
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






































    }
}
