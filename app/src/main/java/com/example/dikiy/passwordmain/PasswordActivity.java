package com.example.dikiy.passwordmain;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.DBase.DBWorker;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.ItemModel.PasswordList;
import com.example.dikiy.passwordmain.Model.PasswordModel;

import com.example.dikiy.passwordmain.Presenters.PasswordPresenter;
import com.example.dikiy.passwordmain.RecyclerView.TagOrGroupRecyclerAdapter;
import com.example.dikiy.passwordmain.Adapters.Model.TagOrGroupRecyclerItem;
import com.example.dikiy.passwordmain.crypto.Aes256Class;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 16.02.2018.
 */

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private List<String> movieList = new ArrayList<>();
    TextInputLayout nameTIL, urlTIL, loginTIL, passwordTIL, commentTIL;
    private List<String> movieList2 = new ArrayList<>();
    private RecyclerView recyclerView, recyclerView2;
    private TagOrGroupRecyclerAdapter mAdapter, mAdapter2;
    private TagOrGroupRecyclerItem movie, movie2;
    EditText etUrl, etLogin, etPassword, etname, etlog;
    FrameLayout refreshl;
    AutoCompleteTextView addTag, addGroup;
    ImageView addTagB, addTagB2, editandclose, acept, copyurl, copylogin, copypassword, backImageView,shareImageView;
    boolean stat = false;
    String name = "";
    String url = "";
    String login = "";
    final Boolean tag = false, group = true;
    String password = "";
    String mode = "";
    int folder = 0;
    Button addpass;
    String log = "";
    PasswordModel model;
    PasswordPresenter presenter;
    Intent thisIntent;
    DBWorker dbWorker;
    Intent intent = new Intent();
    ArrayAdapter<String> tagAdapter;
    ArrayAdapter<String> groupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, R.anim.flye);
        setContentView(R.layout.create_password_activity);
        thisIntent = this.getIntent();
        dbWorker = new DBWorker(this);
        Log.v("123sda1223", thisIntent.getStringExtra("folder"));
        folder = Integer.parseInt(thisIntent.getStringExtra("folder"));
        mode = thisIntent.getStringExtra("mode");
        init();
        loadDefault();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shareImageView=findViewById(R.id.shareImageView);
        backImageView = findViewById(R.id.backImageView);
        nameTIL = findViewById(R.id.nameTextInputLayout);
        urlTIL = findViewById(R.id.urlTextInputLayout);
        loginTIL = findViewById(R.id.loginTextInputLayout);
        passwordTIL = findViewById(R.id.passwordTextInputLayout);
        commentTIL = findViewById(R.id.commentTextInputLayout);
        recyclerView2 = findViewById(R.id.rv2);
        recyclerView = findViewById(R.id.rv);
        editandclose = findViewById(R.id.editandclose);
        acept = findViewById(R.id.acept);
        copyurl = findViewById(R.id.copyUrl);
        copylogin = findViewById(R.id.copyLogin);
        copypassword = findViewById(R.id.copyPassword);
        addpass = findViewById(R.id.addpass);
        etUrl = findViewById(R.id.eturl);
        etLogin = findViewById(R.id.etlogin);
        etPassword = findViewById(R.id.etpassword);
        etname = findViewById(R.id.etname);
        etlog = findViewById(R.id.etcomment);
        refreshl = findViewById(R.id.progressl);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpass.setEnabled(false);
                List<String> tagList = new ArrayList<>();
                List<String> groupList = new ArrayList<>();
                for (int i = 0; i < movieList.size(); i++) {
                    tagList.add(movieList.get(i));
                }
                for (int i = 0; i < movieList2.size(); i++) {
                    groupList.add(movieList2.get(i));
                }
                presenter.createPass(etname.getText().toString(), folder, etUrl.getText().toString(), etPassword.getText().toString(), etLogin.getText().toString(), etlog.getText().toString(), tagList, groupList);
            }
        });
        editandclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeEdit(1);
            }
        });
        acept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.equals(etPassword.getText().toString()) ||
                        url.equals(etUrl.getText().toString()) ||
                        name.equals(etname.getText().toString()) ||
                        login.equals(etLogin.getText().toString()) ||
                        log.equals(etlog.getText().toString())) {
                    refreshl.setVisibility(View.VISIBLE);
                    presenter.updatePass(Integer.valueOf(folder), etname.getText().toString(), etUrl.getText().toString(), etLogin.getText().toString(), etPassword.getText().toString());
                }
            }
        });
        copyurl.setOnClickListener(this);
        copylogin.setOnClickListener(this);
        copypassword.setOnClickListener(this);
        mAdapter = new TagOrGroupRecyclerAdapter(movieList, new TagOrGroupRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                if (mode.equals("1")) {
                    presenter.removeTagOrGroup(s, folder, tag);
                }
                tagAdapter.add(s);
                tagAdapter.notifyDataSetChanged();
                addTag.setAdapter(tagAdapter);
            }
        });
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        addTag = findViewById(R.id.addtag);
        addTagB = findViewById(R.id.addtagb);
        addTagB.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (addTag.getText().length() != 0) {
                                               if (addItemInRV(addTag.getText().toString(), movieList)) {
                                                   if (mode.equals("1")) {
                                                       presenter.addTagOrGroup(tag, addTag.getText().toString(), folder);
                                                   }
                                                   tagAdapter.remove(addTag.getText().toString());
                                                   movieList.add(addTag.getText().toString());
                                                   mAdapter.notifyDataSetChanged();
                                                   recyclerView.scrollBy(1000000000, 1000000000);
                                                   addTag.setText("");
                                               }
                                           }
                                       }
                                   }
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter2 = new TagOrGroupRecyclerAdapter(movieList2, new TagOrGroupRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                if (mode.equals("1")) {
                    presenter.removeTagOrGroup(s, folder, group);
                }
                groupAdapter.add(s);
                groupAdapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        addGroup = findViewById(R.id.addgrup);
        addTagB2 = findViewById(R.id.addgrupb);
        addTagB2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (addGroup.getText().length() != 0) {
                                                if (addItemInRV(addGroup.getText().toString(), movieList2)) {
                                                    if (mode.equals("1")) {
                                                        presenter.addTagOrGroup(group, addGroup.getText().toString(), folder);
                                                    }
                                                    groupAdapter.remove(addGroup.getText().toString());

                                                    movieList2.add(addGroup.getText().toString());
                                                    mAdapter2.notifyDataSetChanged();
                                                    recyclerView2.scrollBy(1000000000, 1000000000);
                                                    addGroup.setText("");


                                                }
                                            }
                                        }
                                    }
        );
        shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               popupMesage();
            }
        });
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(mAdapter2);
        setEnabled(false);
        PasswordModel usersModel = new PasswordModel();
        presenter = new PasswordPresenter(usersModel);
        presenter.attachView(this);
        presenter.viewIsReady(mode.equals("1"), folder);
        DBWorker dbWorker = new DBWorker(this);
        tagAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dbWorker.getTag());
        groupAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dbWorker.getGroup());
        addTag.setAdapter(tagAdapter);
        addGroup.setAdapter(groupAdapter);
        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addTag.getText().length() == 0) {
                    addTag.showDropDown();
                }
            }
        });
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addTag.getText().length() == 0) {
                    addTag.showDropDown();
                }
            }
        });

        if (mode.equals("1")) {
            addpass.setVisibility(View.GONE);
        } else {
            refreshl.setVisibility(View.GONE);
            editandclose.setVisibility(View.GONE);
            copyurl.setVisibility(View.GONE);
            copylogin.setVisibility(View.GONE);
            copypassword.setVisibility(View.GONE);
            acept.setVisibility(View.GONE);
            setEnabled(true);
        }

    }

    private boolean addItemInRV(String text, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(text)) {
                return false;
            }

        }
        return true;
    }


    private void setEnabled(boolean b) {
        commentTIL.setEnabled(b);
        nameTIL.setEnabled(b);
        passwordTIL.setEnabled(b);
        loginTIL.setEnabled(b);
        urlTIL.setEnabled(b);
        addTag.setEnabled(b);
        addGroup.setEnabled(b);
    }

    public void loadDefault() {
        shareImageView.setVisibility(View.VISIBLE);
        if (mode.equals("1")) {
            Log.v("123sda1223", String.valueOf(folder));
            GetPass_Item item = dbWorker.getPass(folder);
            name = item.getName();
            url = item.getUrl();
            login = item.getLogin();
            log = item.getDescription();
            password = Aes256Class.decode(item.getPass(), Aes256Class.decode(item.getClue(), LoadText.getMasterPass(this)));
            if (!item.getGroups().get(0).equals("")) {
                List<String> nameG = new ArrayList<>();
                nameG.addAll(dbWorker.getGroupName(item.getGroups()));
                for (int i = 0; i < nameG.size(); i++) {
                    Log.v("123123axzcass", nameG.get(i));
                    movieList2.add((nameG.get(i)));
                }
            }
            if (!item.getTags().get(0).equals("")) {
                List<String> nameT = new ArrayList<>();
                nameT.addAll(dbWorker.getTagName(item.getTags()));
                for (int i = 0; i < nameT.size(); i++) {
                    Log.v("123123axzcass", nameT.get(i));
                    movieList.add(nameT.get(i));

                }
            }
        } else {
            mAdapter2.modeEdit(false);
            mAdapter.modeEdit(false);
        }
        etname.setText(name);
        etUrl.setText(url);
        etLogin.setText(login);
        etPassword.setText(password);
        etlog.setText(log);
//        movieList2.clear();
//        movieList2.addAll(movieList2D);
//        movieList.clear();
//        movieList.addAll(movieListD);
//        mAdapter2.notifyDataSetChanged();
//        mAdapter.notifyDataSetChanged();
    }

    public void saveDefault() {
//        movieList2D.clear();
//        movieList2D.addAll(movieList2);
//        movieListD.clear();
//        movieListD.addAll(movieList);

    }

    private void SwitchEdit(boolean i) {
        etlog.setEnabled(i);
        etname.setEnabled(i);
        etPassword.setEnabled(i);
        etLogin.setEnabled(i);
        etUrl.setEnabled(i);
        addTag.setEnabled(i);
        addGroup.setEnabled(i);
        addTagB.setEnabled(i);
        addTagB2.setEnabled(i);
    }

    public void modeEdit(int i) {
        refreshl.setVisibility(View.GONE);
        if (!stat) {
            acept.setVisibility(View.VISIBLE);
            editandclose.setImageResource(android.R.drawable.ic_delete);
            mAdapter.modeEdit(true);
            mAdapter2.modeEdit(true);
            mAdapter.notifyDataSetChanged();
            mAdapter2.notifyDataSetChanged();
            SwitchEdit(true);
            stat = true;
        } else {
            acept.setVisibility(View.GONE);
            editandclose.setImageResource(android.R.drawable.ic_menu_edit);
            mAdapter.modeEdit(false);
            mAdapter2.modeEdit(false);
            mAdapter.notifyDataSetChanged();
            mAdapter2.notifyDataSetChanged();
            SwitchEdit(false);
            addTag.setText("");
            addGroup.setText("");
            if (i == 1) {
                loadDefault();
            } else {
                name = String.valueOf(etname.getText());
                url = String.valueOf(etUrl.getText());
                login = String.valueOf(etLogin.getText());
                password = String.valueOf(etPassword.getText());
                mAdapter.notifyDataSetChanged();
                mAdapter2.notifyDataSetChanged();
                saveDefault();
            }
            stat = false;
        }
    }

    public void copy(String s) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("123", s);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "copy", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copyurl:
                copy(String.valueOf(etUrl.getText()));
                break;
            case R.id.copylogin:
                copy(String.valueOf(etLogin.getText()));
                break;
            case R.id.copypassword:
                copy(String.valueOf(etPassword.getText()));
                break;
        }


    }

    public void showlist1(PasswordList list) {

    }

    public void showlist2(PasswordList list) {

    }


    public void execute() {

        setResult(RESULT_OK, intent);
        finish();
    }

    public void loadPass(String pass) {

        refreshl.setVisibility(View.GONE);
        password = pass;
        etPassword.setText(pass);

    }

    public void callError(String s) {
        refreshl.setVisibility(View.GONE);
        if (s != null)
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        addpass.setEnabled(true);
    }


    public void setRefreshStatus() {
        setResult(RESULT_OK, intent);
    }

    public void sendMesageToUser() {
        String id= String.valueOf(folder);
        String  key= dbWorker.getPass(folder).getClue();
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                new String[]{LoadText.getText(this, "email")});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "MainPass");
        String clue=Aes256Class.decode(key,LoadText.getMasterPass(this));
        String reqest="/?id="+id+"&id="+id+"&clue="+clue+"1"+"&clue="+clue;

        Log.v("12312test","pass.add.com"+ reqest);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "pass.add.com"+ Uri.parse(reqest));
        this.startActivity(Intent.createChooser(emailIntent,
                "Отправка письма..."));
    }
    public void popupMesage() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Введить почту кому хотите передать пароль");
            final EditText input = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            alertDialog.setView(input);
            alertDialog.setPositiveButton("Изменить",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           presenter.givePass(folder,input.getText().toString());
                        }
                    });
            alertDialog.setNegativeButton("Отмена",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            alertDialog.show();

    }

    public void fail() {
        Toast.makeText(this,"server error",Toast.LENGTH_SHORT).show();
    }

    public void error(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}

