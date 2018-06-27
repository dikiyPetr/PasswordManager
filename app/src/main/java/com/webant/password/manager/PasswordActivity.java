package com.webant.password.manager;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.webant.password.manager.Adapters.Get.GetPass_Item;
import com.webant.password.manager.Adapters.Get.GetService_Items;
import com.webant.password.manager.Adapters.Get.GetService_Items_Commands;
import com.webant.password.manager.Adapters.Model.ParamsItem;
import com.webant.password.manager.DBase.DBWorker;
import com.webant.password.manager.ItemModel.PasswordList;
import com.webant.password.manager.Model.PasswordModel;
import com.webant.password.manager.Presenters.PasswordPresenter;
import com.webant.password.manager.RecyclerView.ParamsRecyclerViewAdapter;
import com.webant.password.manager.RecyclerView.TagOrGroupRecyclerAdapter;
import com.webant.password.manager.crypto.NewAes;

import java.util.ArrayList;
import java.util.List;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private List<String> tagsList = new ArrayList<>();
    TextInputLayout nameTIL, urlTIL, loginTIL, passwordTIL, commentTIL;
    private List<String> tagsListEdit = new ArrayList<>();
    private List<Integer> tagsListEditMode = new ArrayList<>();
    private RecyclerView recyclerView, rvUrl, rvBody;
    private TagOrGroupRecyclerAdapter adapter;
    List<GetService_Items> serviceList;
    List<GetService_Items_Commands> commandList;
    EditText etUrl, etLogin, etPassword, etName, etComment;
    FrameLayout refreshL;
    AutoCompleteTextView addTag;
    ImageView addTagB, editOrAccept, close, copyUrl, copyLogin, copyPassword, backImageView, shareImageView;
    boolean stat = false;
    String name = "";
    String url = "";
    String login = "";
    final Boolean tag = false, group = true;
    String password = "";
    String mode = "";
    int folder = 0;
    Button addPass;
    String log = "";
    List<String> tags = new ArrayList<>();
    PasswordModel model;
    PasswordPresenter presenter;
    Intent thisIntent;
    DBWorker dbWorker;
    Intent intent = new Intent();
    ArrayAdapter<String> tagAdapter;
    ArrayAdapter<String> groupAdapter;
    LinearLayout serviceLL, commandLL, paramsLL;
    Spinner serviceS, commandS;
    int commandId = 0;
    List<ParamsItem> bodyItems = new ArrayList<>();
    List<ParamsItem> urlItems = new ArrayList<>();
    ParamsRecyclerViewAdapter bodyAdapter, urlAdapter;
    TextView sizeComment;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, R.anim.flye);
        setContentView(R.layout.create_password_activity);
        thisIntent = this.getIntent();
        dbWorker = new DBWorker(this);
        folder = Integer.parseInt(thisIntent.getStringExtra("folder"));
        mode = thisIntent.getStringExtra("mode");
        init();
        loadDefault();
    }

    public int getCommandId() {
        return commandId;
    }

    public List<ParamsItem> getBodyItems() {
        return bodyItems;
    }

    public List<ParamsItem> getUrlItems() {
        return urlItems;
    }

    private void init() {
        context = this;
        sizeComment = findViewById(R.id.sizeComment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        serviceLL = findViewById(R.id.serviceLenearL);
        commandLL = findViewById(R.id.commandLinearL);
        paramsLL = findViewById(R.id.parametrsLinearL);
        rvBody = findViewById(R.id.rvBody);
        rvUrl = findViewById(R.id.rvUrl);
        serviceS = findViewById(R.id.serviceSpinner);
        commandS = findViewById(R.id.commandSpinner);
        shareImageView = findViewById(R.id.share);
        backImageView = findViewById(R.id.backImageView);
        nameTIL = findViewById(R.id.nameTextInputLayout);
        urlTIL = findViewById(R.id.urlTextInputLayout);
        loginTIL = findViewById(R.id.loginTextInputLayout);
        passwordTIL = findViewById(R.id.passwordTextInputLayout);
        commentTIL = findViewById(R.id.commentTextInputLayout);
        recyclerView = findViewById(R.id.rv);
        editOrAccept = findViewById(R.id.editOrAccept);
        close = findViewById(R.id.close);
        copyUrl = findViewById(R.id.copyUrl);
        copyLogin = findViewById(R.id.copyLogin);
        copyPassword = findViewById(R.id.copyPassword);
        addPass = findViewById(R.id.addpass);
        etUrl = findViewById(R.id.eturl);
        etLogin = findViewById(R.id.etlogin);
        etPassword = findViewById(R.id.etpassword);
        etName = findViewById(R.id.etname);
        etComment = findViewById(R.id.etcomment);
        refreshL = findViewById(R.id.progressl);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPass.setEnabled(false);
                List<String> tagList = new ArrayList<>();
                List<String> groupList = new ArrayList<>();
                for (int i = 0; i < tagsList.size(); i++) {
                    tagList.add(tagsList.get(i));
                }
                for (int i = 0; i < tagsListEdit.size(); i++) {
                    groupList.add(tagsListEdit.get(i));
                }
                closeKeyBoard();
                presenter.createPass(etName.getText().toString(), folder, etUrl.getText().toString(),
                        etPassword.getText().toString(), etLogin.getText().toString(),
                        etComment.getText().toString(), tagList, groupList);
            }
        });
        editOrAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stat) {
                    if (password.equals(etPassword.getText().toString()) ||
                            url.equals(etUrl.getText().toString()) ||
                            name.equals(etName.getText().toString()) ||
                            login.equals(etLogin.getText().toString()) ||
                            log.equals(etComment.getText().toString())) {
                        refreshL.setVisibility(View.VISIBLE);
                        presenter.updatePass(Integer.valueOf(folder), etName.getText().toString(),
                                etUrl.getText().toString(), etLogin.getText().toString(),
                                etPassword.getText().toString(), tagsListEdit, tagsListEditMode);
                    }
                } else {
                    modeEdit(0);
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnDefault();
                modeEdit(1);
            }
        });
        copyUrl.setOnClickListener(this);
        copyLogin.setOnClickListener(this);
        copyPassword.setOnClickListener(this);
        adapter = new TagOrGroupRecyclerAdapter(tagsList, new TagOrGroupRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                if (mode.equals("1")) {
                    tagsListEditMode.set(tagsListEdit.indexOf(s), tagsListEditMode.get(tagsListEdit.indexOf(s)) - 1);
                }
                tagAdapter.add(s);
                tagAdapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        addTag = findViewById(R.id.addtag);
        addTagB = findViewById(R.id.addtagb);
        addTagB.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (addTag.getText().length() != 0 &&
                                                   addItemInRV(addTag.getText().toString(), tagsList)) {
                                               String s = addTag.getText().toString();
                                               if (mode.equals("1")) {
                                                   if (tagsListEdit.indexOf(s) == -1) {
                                                       tagsListEdit.add(s);
                                                       tagsListEditMode.add(1);
                                                   } else {
                                                       tagsListEditMode.set(tagsListEdit.indexOf(s),
                                                               tagsListEditMode.get(tagsListEdit.indexOf(s)) + 1);
                                                   }
                                               }
                                               tagAdapter.remove(s);
                                               tagsList.add(s);
                                               adapter.notifyDataSetChanged();
                                               recyclerView.scrollToPosition(adapter.getItemCount()-1);
                                               addTag.setText("");
                                           }
                                       }
                                   }
        );
        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sizeComment.setText(String.valueOf(s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMesage();
            }
        });
        setEnabled(false);
        PasswordModel usersModel = new PasswordModel();
        presenter = new PasswordPresenter(usersModel);
        presenter.attachView(this);
        DBWorker dbWorker = new DBWorker(this);
        tagAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dbWorker.getTag());
        groupAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dbWorker.getGroup());
        addTag.setAdapter(tagAdapter);
        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addTag.getText().length() == 0) {
                    addTag.showDropDown();
                }
            }
        });
        LinearLayoutManager urlLM
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager bodyLM
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvUrl.setLayoutManager(urlLM);
        rvBody.setLayoutManager(bodyLM);
        bodyAdapter = new ParamsRecyclerViewAdapter(bodyItems);
        urlAdapter = new ParamsRecyclerViewAdapter(urlItems);
        rvUrl.setAdapter(urlAdapter);
        rvBody.setAdapter(bodyAdapter);
        if (mode.equals("1")) {
            addPass.setVisibility(View.GONE);
            adapter.modeEdit(false);
        } else {
            serviceLL.setVisibility(View.VISIBLE);
            presenter.loadService();
            shareImageView.setVisibility(View.GONE);
            refreshL.setVisibility(View.GONE);
            editOrAccept.setVisibility(View.GONE);
            copyUrl.setVisibility(View.GONE);
            copyLogin.setVisibility(View.GONE);
            copyPassword.setVisibility(View.GONE);
            close.setVisibility(View.GONE);
            adapter.modeEdit(false);
            setEnabled(true);
        }

    }

    private void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        etPassword.setEnabled(b);
        loginTIL.setEnabled(b);
        urlTIL.setEnabled(b);
        addTag.setEnabled(b);
        adapter.modeEdit(true);
    }

    public void loadDefault() {
        if (mode.equals("1")) {
            GetPass_Item item = dbWorker.getPass(folder);
            name = item.getName();
            url = item.getUrl();
            login = item.getLogin();
            log = item.getDescription();
            password = NewAes.decrypt(item.getPass(), NewAes.decrypt(item.getClue(), ((AplicationListner) getApplication()).getMasterPass()));
            tags = item.getTags();
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
            adapter.notifyDataSetChanged();
        } else {

        }
        etName.setText(name);
        etUrl.setText(url);
        etLogin.setText(login);
        etPassword.setText(password);
        etComment.setText(log);

    }

    public void returnDefault() {
        etName.setText(name);
        etUrl.setText(url);
        etLogin.setText(login);
        etPassword.setText(password);
        etComment.setText(log);
        tagsList.clear();
        tagAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dbWorker.getTag());
        if (!tags.get(0).equals("")) {
            List<String> nameT = new ArrayList<>();
            nameT.addAll(dbWorker.getTagName(tags));
            for (int i = 0; i < nameT.size(); i++) {
                tagAdapter.remove(nameT.get(i));
                tagsList.add(nameT.get(i));
            }
            tagsListEdit.addAll(tagsList);
        }
        clearEdit();
        adapter.notifyDataSetChanged();
    }

    private void SwitchEdit(boolean i) {
        etComment.setEnabled(i);
        etName.setEnabled(i);
        etPassword.setEnabled(i);
        etLogin.setEnabled(i);
        etUrl.setEnabled(i);
        addTag.setEnabled(i);
        addTagB.setEnabled(i);

    }

    public void modeEdit(int i) {
        refreshL.setVisibility(View.GONE);
        if (!stat) {
            close.setVisibility(View.VISIBLE);
            editOrAccept.setImageResource(R.drawable.accept);
            adapter.modeEdit(true);

            adapter.notifyDataSetChanged();

            SwitchEdit(true);
            stat = true;
        } else {
            close.setVisibility(View.GONE);
            editOrAccept.setImageResource(R.drawable.edit);
            adapter.modeEdit(false);

            adapter.notifyDataSetChanged();

            SwitchEdit(false);
            addTag.setText("");

            if (i == 0) {
                name = String.valueOf(etName.getText());
                url = String.valueOf(etUrl.getText());
                login = String.valueOf(etLogin.getText());
                password = String.valueOf(etPassword.getText());
                clearEdit();
                adapter.notifyDataSetChanged();
            }
            stat = false;
        }
    }

    public void clearEdit() {
        tagsListEdit.clear();
        tagsListEditMode.clear();
        for (int a = 0; a < tagsList.size(); a++) {
            tagsListEdit.add(tagsList.get(a));
            tagsListEditMode.add(0);
        }
    }

    public void copy(String s) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("PasswordData", s);
        clipboard.setPrimaryClip(clip);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copyUrl:
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

    public void execute() {
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setRefresh() {
        refreshL.setVisibility(View.GONE);
        addPass.setEnabled(true);
    }


    public void sendMesageToUser(String email) {
        String id = String.valueOf(folder);
        String key = dbWorker.getPass(folder).getClue();
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "MainPass");
        String clue = NewAes.decrypt(key, ((AplicationListner) getApplication()).getMasterPass());
        String reqest = "/?id=" + id + "&clue=" + Uri.encode(clue);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "pass.add.com/pass" + reqest);
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
        alertDialog.setPositiveButton(R.string.send_password,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.givePass(folder, input.getText().toString());
                    }
                });
        alertDialog.setNegativeButton(R.string.action_cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();

    }



    public void loadService(final List<GetService_Items> list) {
        serviceLL.setVisibility(View.VISIBLE);
        serviceList = list;
        final List<String> serviceList = new ArrayList<>();
        serviceList.add("-");
        for (int i = 0; i < list.size(); i++)
            serviceList.add(list.get(i).getName());
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, serviceList);
        serviceS.setAdapter(serviceAdapter);
        serviceS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectId, long selectedPosit) {
                if (selectId == 0) {
                    commandLL.setVisibility(View.GONE);
                    urlItems.clear();
                    bodyItems.clear();
                } else {
                    presenter.loadCommand(list.get(selectId - 1).getId());
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void loadCommand(final List<GetService_Items_Commands> list) {
        commandLL.setVisibility(View.VISIBLE);
        final List<String> commandList = new ArrayList<>();
        commandList.add("-");
        for (int i = 0; i < list.size(); i++)
            commandList.add(list.get(i).getName());
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, commandList);
        commandS.setAdapter(serviceAdapter);
        commandS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectId, long selectedPosit) {
                if (selectId == 0) {
                    paramsLL.setVisibility(View.GONE);
                    commandId = 0;
                    urlItems.clear();
                    bodyItems.clear();
                } else {
                    commandId = list.get(selectId - 1).getId();
                    paramsLL.setVisibility(View.VISIBLE);
                    loadParams(list.get(selectId - 1));
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadParams(GetService_Items_Commands id) {
        commandId = id.getId();
        urlItems.addAll(id.getParamsUrl());
        urlAdapter.notifyDataSetChanged();
        bodyItems.addAll(id.getParamsBody());
        bodyAdapter.notifyDataSetChanged();
    }

    public void setExecute() {
        modeEdit(0);
        setResult(RESULT_OK, intent);
    }

    public void setError(String error) {
        if(!error.isEmpty() && error.length()!=0)
    nameTIL.setError(error);
    }
}

