package com.example.dikiy.passwordmain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dikiy.passwordmain.RecyclerView.RecyclerAdapter;
import com.example.dikiy.passwordmain.RecyclerView.RecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 04.04.2018.
 */

public class UserActivity extends AppCompatActivity {
    ImageView iAccept,iClose,iRole;
    EditText etRole,etLog,etName;
    private RecyclerView rvTag, rvRole;
    private RecyclerAdapter adapterTag, adapterRole;
    private List<RecyclerItem> listTag = new ArrayList<>();
    private List<RecyclerItem> listRole = new ArrayList<>();
    private List<RecyclerItem> listTagD = new ArrayList<>();
    private List<RecyclerItem> listGrupD = new ArrayList<>();
    private RecyclerItem movieGrup, movieTag;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(0,R.anim.flye);
        setContentView(R.layout.create_user_activity);
        init();
    }

    private void init() {
        iAccept=findViewById(R.id.acept);
        iClose=findViewById(R.id.editandclose);
        iRole=findViewById(R.id.irole);
        etRole=findViewById(R.id.etrole);
        etLog=findViewById(R.id.etlog);
        etName=findViewById(R.id.etname);


        rvRole=findViewById(R.id.rv);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvRole.setLayoutManager(layoutManager);
        adapterRole = new RecyclerAdapter(listRole);
        adapterRole.switchMode();
        rvRole.setAdapter(adapterRole);

        iRole.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(etRole.getText().length()!=0) {
                                                movieGrup = new RecyclerItem(String.valueOf(etRole.getText()));
                                                listRole.add(movieGrup);
                                                adapterRole.notifyDataSetChanged();
                                                rvRole.scrollBy(1000000000, 1000000000);
                                                etRole.setText("");
                                            }
                                        }
                                    }
        );
    }
}
