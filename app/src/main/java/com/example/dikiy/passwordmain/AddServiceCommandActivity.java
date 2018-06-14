package com.example.dikiy.passwordmain;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dikiy.passwordmain.Adapters.Get.GetService_Items_Commands;
import com.example.dikiy.passwordmain.Model.AddServiceCommandModel;
import com.example.dikiy.passwordmain.Presenters.AddServiceCommandPresenter;
import com.example.dikiy.passwordmain.RecyclerView.TagOrGroupRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddServiceCommandActivity extends AppCompatActivity {
    TextInputLayout nameTil, commandTil, methodTil, templateBodyTil, templateUrlTil;
    EditText name, method, templateBody, templateUrl;
    AutoCompleteTextView addParamsBody, addParamsUrl;
    Button create;
    FrameLayout progress;
    AddServiceCommandPresenter presenter;
    private RecyclerView rvParamsBody, rvParamsUrl;
    private TagOrGroupRecyclerAdapter adapterParamsBody, adapterParamsUrl;
    private List<String> itemsBody = new ArrayList<>(), itemsUrl = new ArrayList<>();
    ImageView addParamsBodyB, addParamsUrlB, back, close, editOrAccept;
    GetService_Items_Commands item;
    boolean modeEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_command);
        init();
    }

    private void init() {
        close = findViewById(R.id.close);
        back = findViewById(R.id.backImageView);
        editOrAccept = findViewById(R.id.editOrAccept);
        templateBodyTil = findViewById(R.id.templatebodyTILSC);
        templateUrlTil = findViewById(R.id.templateurlTILSC);
        addParamsBody = findViewById(R.id.addParamsBody);
        addParamsUrl = findViewById(R.id.addParamsUrl);
        addParamsBodyB = findViewById(R.id.addParamsBodyImageView);
        addParamsUrlB = findViewById(R.id.addParamsUrlImageView);
        nameTil = findViewById(R.id.nameTILSC);
        methodTil = findViewById(R.id.methodTILSC);
        name = findViewById(R.id.nameSC);
        rvParamsBody = findViewById(R.id.rvParamsBody);
        rvParamsUrl = findViewById(R.id.rvParamsUrl);
        method = findViewById(R.id.methodSC);
        templateBody = findViewById(R.id.templatebodySC);
        templateUrl = findViewById(R.id.templateurlSC);
        create = findViewById(R.id.createSC);
        progress = findViewById(R.id.progresSC);


        LinearLayoutManager layoutManagerUrl
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManagerBody
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvParamsBody.setLayoutManager(layoutManagerBody);
        rvParamsUrl.setLayoutManager(layoutManagerUrl);
        adapterParamsBody = new TagOrGroupRecyclerAdapter(itemsBody, new TagOrGroupRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                adapterParamsBody.notifyDataSetChanged();
            }
        });
        adapterParamsUrl = new TagOrGroupRecyclerAdapter(itemsUrl, new TagOrGroupRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                adapterParamsUrl.notifyDataSetChanged();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         modeEdit(false);
                                         loadData(1);
                                     }
                                 }
        );
        editOrAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modeEdit)
                    presenter.edit(name.getText().toString(), getIntent().getStringExtra("command_id"), method.getText().toString(), templateBody.getText().toString(), templateUrl.getText().toString());
                else
                    loadData(2);
            }
        });
        rvParamsBody.setAdapter(adapterParamsBody);
        rvParamsUrl.setAdapter(adapterParamsUrl);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNameValid(name.getText().toString()) && isMethodValid(method.getText().toString())) {
                    View view = AddServiceCommandActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    progress.setVisibility(View.VISIBLE);
                    presenter.createCommand(name.getText().toString(), getIntent().getStringExtra("command_id"), method.getText().toString(), itemsBody, itemsUrl, templateBody.getText().toString(), templateUrl.getText().toString());
                }
            }
        });

        AddServiceCommandModel model = new AddServiceCommandModel();
        presenter = new AddServiceCommandPresenter(model);
        presenter.attachView(this);
        addParamsUrlB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addParamsUrl.getText().toString().length() != 0) {
                    itemsUrl.add(addParamsUrl.getText().toString());
                    adapterParamsUrl.notifyDataSetChanged();
                    addParamsUrl.setText("");
                }
            }
        });
        addParamsBodyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addParamsBody.getText().toString().length() != 0) {
                    itemsBody.add(addParamsBody.getText().toString());
                    adapterParamsBody.notifyDataSetChanged();
                    addParamsBody.setText("");
                }
            }
        });
        if (getIntent().getStringExtra("id") != null) {
            presenter.loadCommand(getIntent().getStringExtra("id"));
            progress.setVisibility(View.VISIBLE);
        } else
            loadData(0);
    }

    private void loadData(int mode) {
        switch (mode) {
            //create
            case 0:
                adapterParamsBody.modeEdit(true);
                adapterParamsUrl.modeEdit(true);
                modeEdit(true);
                break;
            //view
            case 1:
                itemsBody.clear();
                itemsUrl.clear();
                itemsBody.addAll(item.getParams_body());
                itemsUrl.addAll(item.getParams_url());
                Log.v("testLog", itemsBody.size() + " " + itemsUrl.size());
                adapterParamsUrl.notifyDataSetChanged();
                adapterParamsBody.notifyDataSetChanged();
                name.setText(item.getName());
                method.setText(item.getMethod());
                templateBody.setText(item.getTemplate_body());
                templateUrl.setText(item.getTemplate_url());
                adapterParamsBody.modeEdit(false);
                adapterParamsUrl.modeEdit(false);
                modeEdit(false);
                break;
            //update
            case 2:
                adapterParamsBody.modeEdit(true);
                adapterParamsUrl.modeEdit(true);
                modeEdit(true);
                break;
        }
    }

    public void modeEdit(boolean stat) {
        if (stat) {
            close.setVisibility(View.VISIBLE);
            editOrAccept.setImageResource(R.drawable.accept);
        }else{
            close.setVisibility(View.GONE);
            editOrAccept.setImageResource(R.drawable.edit);
        }
        modeEdit = stat;
        nameTil.setEnabled(stat);
        methodTil.setEnabled(stat);
        addParamsBody.setEnabled(stat);
        addParamsUrl.setEnabled(stat);
        templateUrlTil.setEnabled(stat);
        templateBodyTil.setEnabled(stat);
    }

    public boolean isNameValid(String s) {
        nameTil.setError(null);
        if (s.length() == 0) {
            nameTil.setError(getString(R.string.empty));
        }
        return s.length() != 0;
    }

    public boolean isCommandValid(String s) {
        commandTil.setError(null);
        if (s.length() == 0) {
            commandTil.setError(getString(R.string.empty));
        }
        return s.length() != 0;
    }

    public boolean isMethodValid(String s) {
        methodTil.setError(null);
        if (s.length() == 0) {
            methodTil.setError(getString(R.string.empty));
        }
        return s.length() != 0;
    }

    public void fail() {
        modeEdit(false);
        progress.setVisibility(View.GONE);
        Toast.makeText(this, "no connect to server", Toast.LENGTH_SHORT).show();
    }

    public void error() {
        modeEdit(false);
        progress.setVisibility(View.GONE);
        Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
    }

    public void execute() {
        modeEdit(false);
        progress.setVisibility(View.GONE);
        setResult(RESULT_OK, getIntent());
        finish();
    }

    public void setDefaultData(GetService_Items_Commands item) {
        this.item = item;
        modeEdit(false);
        progress.setVisibility(View.GONE);
        loadData(1);
    }
}
