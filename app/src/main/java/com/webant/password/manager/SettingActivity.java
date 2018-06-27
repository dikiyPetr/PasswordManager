package com.webant.password.manager;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.webant.password.manager.DBase.DBWorker;
import com.webant.password.manager.DBase.LoadText;
import com.webant.password.manager.crypto.CryptoUtils;
import com.webant.password.manager.crypto.NewAes;

public class SettingActivity extends AppCompatActivity {
    private ProfileDrawerItem profileDrawerItem;
    private Drawer result;
    private AccountHeader headerResult;
    Toolbar toolbar;
    Context context;
    DBWorker dbWorker;
    TextView editPin, editMP, sendMPtoEmail, editPass;
    LinearLayout editPinL, editMPL, sendMPtoEmailL, editPassL;
    TextInputLayout oldPinTIL, newPinTIL, newMPTIL, oldPassTIL, newPassTIL, pinTIL;
    EditText oldPin, newPin, newMP, oldPass, newPass, pin;
    Button newPinB, newMPB, sendMP, sendCryptoMP, newPassB;
    int selectedItemId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }

    private void init() {
        context = this;
        dbWorker = new DBWorker(context);
        editPinL = findViewById(R.id.editPinL);
        editMPL = findViewById(R.id.editMPL);
        sendMPtoEmailL = findViewById(R.id.sendMPtoEmailL);
        editPassL = findViewById(R.id.editPassL);
        toolbar = findViewById(R.id.toolbar);
        editPin = findViewById(R.id.editPin);
        editMP = findViewById(R.id.editMP);
        sendMPtoEmail = findViewById(R.id.sendMPtoEmail);
        editPass = findViewById(R.id.editPass);
        oldPinTIL = findViewById(R.id.oldPinTIL);
        newPinTIL = findViewById(R.id.newPinTIL);
        newMPTIL = findViewById(R.id.newMPTIL);
        oldPassTIL = findViewById(R.id.oldPassTIL);
        newPassTIL = findViewById(R.id.newPassTIL);
        pinTIL = findViewById(R.id.pinTIL);
        oldPin = findViewById(R.id.oldPin);
        newPin = findViewById(R.id.newPin);
        newMP = findViewById(R.id.newMP);
        newMPTIL = findViewById(R.id.newMPTIL);
        oldPass = findViewById(R.id.oldPass);
        newPass = findViewById(R.id.newPass);
        pin = findViewById(R.id.pin);
        newPinB = findViewById(R.id.newPinB);
        newMPB = findViewById(R.id.newMPB);
        sendMP = findViewById(R.id.sendMP);
        sendCryptoMP = findViewById(R.id.sendCryptoMP);
        newPassB = findViewById(R.id.newPassB);
        editPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibleItem(editPinL, (TextView) v);
            }
        });
        editMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibleItem(editMPL, (TextView) v);
            }
        });
        sendMPtoEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibleItem(sendMPtoEmailL, (TextView) v);
            }
        });
        editPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibleItem(editPassL, (TextView) v);
            }
        });
        newPinB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidPin(oldPin, oldPinTIL) && checkValidPin(newPin, newPinTIL) && checkPin(oldPin, oldPinTIL)) {
                    //editPin
                    String pin = newPin.getText().toString();
                    ((AplicationListner) getApplication()).setMasterPass(pin);
                    ((AplicationListner) getApplication()).setPin(pin);
                    String encoded = CryptoUtils.encode(pin);
                    LoadText.setText(context, "pin", encoded);
                    oldPin.setText("");
                    newPin.setText("");
                    setVisibleItem(editPinL, editPin);
                    Toast toast = Toast.makeText(context, R.string.edit_pin_ok, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        newMPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidPin(pin, pinTIL) && checkMPValid(newMP, newMPTIL) && checkPin(pin, pinTIL)) {
                    //editMainPass
                    LoadText.setMasterPass(context, NewAes.encrypt(newMP.getText().toString(), pin.getText().toString()));
                    pin.setText("");
                    newMP.setText("");
                    setVisibleItem(editMPL, editMP);
                    Toast toast = Toast.makeText(context, R.string.edit_main_pass_ok, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        sendMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendMainPass
                DialogWindows.sendMesageToUser(context,false);
                setVisibleItem(sendMPtoEmailL, sendMPtoEmail);
            }
        });
        sendCryptoMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendMainPass
                DialogWindows.sendMesageToUser(context,true);
                setVisibleItem(sendMPtoEmailL, sendMPtoEmail);
            }
        });
        newPassB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPassValid(oldPass, oldPassTIL) && checkPassValid(newPass, newPassTIL)) {

                }
            }
        });
        createLeftMenu();
    }

    private boolean checkPin(EditText v, TextInputLayout l) {
        l.setError(null);
        if (v.getText().toString().equals(((AplicationListner) getApplication()).getPin())) {
            return true;
        } else {
            l.setError(getString(R.string.incorect_pin));
            return false;
        }
    }

    private boolean checkPassValid(EditText v, TextInputLayout l) {
        l.setError(null);
        if (v.getText().toString().length() != 0) {
            return true;
        } else {
            l.setError(getString(R.string.short_password));
            return false;
        }
    }

    private boolean checkMPValid(EditText v, TextInputLayout l) {
        l.setError(null);
        if (v.getText().toString().length() > 4) {
            return true;
        } else {
            l.setError(getString(R.string.short_main_pass));
            return false;
        }
    }

    public boolean checkValidPin(EditText v, TextInputLayout l) {
        l.setError(null);
        if (v.getText().toString().length() == 4) {
            return true;
        } else {
            l.setError(getString(R.string.short_pin));
            return false;
        }
    }

    public void setVisibleItem(View v, TextView v1) {
        editPin.setTextColor(ContextCompat.getColor(context, R.color.black));
        editMP.setTextColor(ContextCompat.getColor(context, R.color.black));
        sendMPtoEmail.setTextColor(ContextCompat.getColor(context, R.color.black));
        editPass.setTextColor(ContextCompat.getColor(context, R.color.black));
        editPinL.setVisibility(View.GONE);
        editMPL.setVisibility(View.GONE);
        sendMPtoEmailL.setVisibility(View.GONE);
        editPassL.setVisibility(View.GONE);
        if (selectedItemId != v.getId()) {
            v1.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            v.setVisibility(View.VISIBLE);
            selectedItemId = v.getId();
        } else {
            selectedItemId = 0;
        }
    }

    public void createLeftMenu() {
        profileDrawerItem = new ProfileDrawerItem().withIdentifier(1)
                .withEmail(LoadText.getText(this, "email"));
        new DrawerBuilder().withActivity(this).build();
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.gradient_left_menu)
                .withSelectionListEnabled(false)
                .withProfileImagesVisible(false)
                .addProfiles(profileDrawerItem)
                .build();
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSliderBackgroundColorRes(R.color.white)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new SecondaryDrawerItem().withIdentifier(1).withName(R.string.main_page).withSelectable(false),
                        new SecondaryDrawerItem().withIdentifier(2).withName(R.string.service_page).withSelectable(false),
                        new SecondaryDrawerItem().withIdentifier(3).withName(R.string.setting_page).withSelectable(false),
                        new SecondaryDrawerItem().withIdentifier(4).withName(R.string.action_exit).withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:
                                finish();
                                break;
                            case 2:
                                startActivity(new Intent(context, ServiceActivity.class));
                                finish();
                                break;
                            case 4:
                                DialogWindows.exit(context);
                                break;
                        }
                        return false;
                    }
                })
                .build();
        result.setSelection(3);
    }
}
