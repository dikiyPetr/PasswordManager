package com.webant.password.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.webant.password.manager.DBase.LoadText;
import com.webant.password.manager.crypto.CryptoUtils;
import com.webant.password.manager.crypto.NewAes;
import com.webant.password.manager.fingerprint.FingerprintUtils;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

/**
 * Created by dikiy on 09.03.2018.
 */

public class LockActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String PIN = "pin";
    TextView actionTextView, tvb0, tvb1, tvb2, tvb3, tvb4, tvb5, tvb6, tvb7, tvb8, tvb9, tvbLogout, tvbClear;
    int mode = 0;
    String pin = "", doublePin = "";
    List<TextView> stars = new ArrayList<>();
    private FingerprintHelper mFingerprintHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, R.anim.flye);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_activity);
        if (LoadText.getText(this, "access_token").isEmpty()) {
            Toast.makeText(this, R.string.sign_in_first, Toast.LENGTH_LONG).show();
            logout();
        }
        init();
        if (savedInstanceState != null)
            restoreActivityState(savedInstanceState);
        setTitle();
    }

    private void restoreActivityState(Bundle savedInstanceState) {
        mode = savedInstanceState.getInt("mode");
        pin = savedInstanceState.getString("pin");
        doublePin = savedInstanceState.getString("doublePin");
        for (int i = 0; i < pin.length(); i++)
            stars.get(i).setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mode);
        outState.putString("pin", pin);
        outState.putString("doublePin", doublePin);

    }

    private void init() {
        actionTextView = findViewById(R.id.actionTextView);
        stars.add((TextView) findViewById(R.id.star1));
        stars.add((TextView) findViewById(R.id.star2));
        stars.add((TextView) findViewById(R.id.star3));
        stars.add((TextView) findViewById(R.id.star4));
        tvbClear = findViewById(R.id.tvbClear);
        tvbLogout = findViewById(R.id.tvbLogout);
        tvb0 = findViewById(R.id.tvb0);
        tvb1 = findViewById(R.id.tvb1);
        tvb2 = findViewById(R.id.tvb2);
        tvb3 = findViewById(R.id.tvb3);
        tvb4 = findViewById(R.id.tvb4);
        tvb5 = findViewById(R.id.tvb5);
        tvb6 = findViewById(R.id.tvb6);
        tvb7 = findViewById(R.id.tvb7);
        tvb8 = findViewById(R.id.tvb8);
        tvb9 = findViewById(R.id.tvb9);
        tvb0.setOnClickListener(this);
        tvb1.setOnClickListener(this);
        tvb2.setOnClickListener(this);
        tvb3.setOnClickListener(this);
        tvb4.setOnClickListener(this);
        tvb5.setOnClickListener(this);
        tvb6.setOnClickListener(this);
        tvb7.setOnClickListener(this);
        tvb8.setOnClickListener(this);
        tvb9.setOnClickListener(this);
        tvbClear.setOnClickListener(this);
        if (!LoadText.getMasterPass(this).isEmpty()) {
            mode = 2;
        }
        tvbLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    public void editPin(int namber) {
        if (mode == 1 && namber == -1 && pin.length() == 0) {
            mode = 0;
            actionTextView.setText(R.string.create_pin);
        } else if (namber == -1) {
            pin = "";
            stars.get(0).setTextColor(ContextCompat.getColor(this, R.color.black));
            stars.get(1).setTextColor(ContextCompat.getColor(this, R.color.black));
            stars.get(2).setTextColor(ContextCompat.getColor(this, R.color.black));
            stars.get(3).setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (pin.length() <= 3) {

            pin += namber;
            stars.get(pin.length() - 1).setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            if (pin.length() == 4) {
                checkPin();
            }

        }
    }

    private void setTitle() {
        switch (mode) {
            case 0:
                actionTextView.setText(R.string.create_pin);
                break;
            case 1:
                actionTextView.setText(R.string.confirm_pin);
                break;
            case 2:
                actionTextView.setText(R.string.write_pin);
                break;
            case 3:
                setMasterPassDialog();
                break;
            case 4:
                sendMasterPassDialog();
                break;
        }
    }

    public void checkPin() {
        switch (mode) {
            case 0:
                doublePin = pin;
                editPin(-1);
                mode = 1;
                setTitle();
                break;
            case 1:
                if (pin.equals(doublePin)) {
                    mode = 3;
                    setMasterPassDialog();
                } else {
                    editPin(-1);
                    mode = 0;
                    setTitle();
                }
                break;
            case 2:

                if (((AplicationListner) getApplication()).setPin(pin)) {
                    login();
                } else {
                    editPin(-1);
                    Toast.makeText(this, R.string.incorect_pin, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvb0:
                editPin(0);
                break;
            case R.id.tvb1:
                editPin(1);
                break;
            case R.id.tvb2:
                editPin(2);
                break;
            case R.id.tvb3:
                editPin(3);
                break;
            case R.id.tvb4:
                editPin(4);
                break;
            case R.id.tvb5:
                editPin(5);
                break;
            case R.id.tvb6:
                editPin(6);
                break;
            case R.id.tvb7:
                editPin(7);
                break;
            case R.id.tvb8:
                editPin(8);
                break;
            case R.id.tvb9:
                editPin(9);
                break;
            case R.id.tvbClear:
                editPin(-1);
                break;
        }
    }

    private void logout() {
        Intent intent = new Intent(LockActivity.this, LoginActivity.class);
        LoadText.setNull(this);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!LoadText.getMasterPass(this).isEmpty()) {
            prepareSensor();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mFingerprintHelper != null) {
            mFingerprintHelper.cancel();
        }
    }

    private void login() {
        Intent intent;
        if (getIntent().getData() != null && getIntent().getData().getPath().equals("/pass/")) {
            intent = new Intent(this, ShareGetActivity.class);
            intent.setData(getIntent().getData());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            intent = new Intent(this, MainActivity.class);
            if (getIntent().getData() != null) {
                intent.setData(getIntent().getData());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            }
        }
        startActivity(intent);
        finish();
    }

    private void prepareSensor() {
        if (FingerprintUtils.isSensorStateAt(FingerprintUtils.mSensorState.READY, this)) {
            FingerprintManagerCompat.CryptoObject cryptoObject = CryptoUtils.getCryptoObject();
            if (cryptoObject != null) {
                findViewById(R.id.fingerRelativeLayout).setVisibility(View.VISIBLE);
                mFingerprintHelper = new FingerprintHelper(this);
                mFingerprintHelper.startAuth(cryptoObject);
            } else {
                LoadText.setText(this, PIN, null);
                Toast.makeText(this, R.string.action_write_pin, Toast.LENGTH_SHORT).show();
            }

        }
    }


    public class FingerprintHelper extends FingerprintManagerCompat.AuthenticationCallback {
        private Context mContext;
        private CancellationSignal mCancellationSignal;

        FingerprintHelper(Context context) {
            mContext = context;
        }

        void startAuth(FingerprintManagerCompat.CryptoObject cryptoObject) {
            mCancellationSignal = new CancellationSignal();
            FingerprintManagerCompat manager = FingerprintManagerCompat.from(mContext);
            manager.authenticate(cryptoObject, 0, mCancellationSignal, this, null);
        }

        void cancel() {
            if (mCancellationSignal != null) {
                mCancellationSignal.cancel();
            }
        }

        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {

        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {

        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            Cipher cipher = result.getCryptoObject().getCipher();
            String encoded = LoadText.getText(LockActivity.this, PIN);
            String decoded = CryptoUtils.decode(encoded, cipher);
            if (((AplicationListner) getApplication()).setPin(decoded))
                login();
        }

        @Override
        public void onAuthenticationFailed() {
            Toast.makeText(mContext, R.string.try_again, Toast.LENGTH_SHORT).show();
        }

    }

    private void savePin(String pin) {
        if (FingerprintUtils.isSensorStateAt(FingerprintUtils.mSensorState.READY, this)) {
            String encoded = CryptoUtils.encode(pin);
            LoadText.setText(this, PIN, encoded);
        }
    }

    private void sendMasterPassDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.select_storage_method_main_pass);
        alertDialog.setMessage(getString(R.string.send_main_pass_description));
        alertDialog.setNeutralButton(R.string.action_no_send, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                login();
            }
        });
        alertDialog.setPositiveButton(R.string.action_encrypt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                login();
                sendMesageToUser(true);
            }
        });
        alertDialog.setNegativeButton(R.string.action_no_encrypt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                login();
                sendMesageToUser(false);
            }
        });
        alertDialog.setIcon(R.drawable.key);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void setMasterPassDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.write_main_pass);
        alertDialog.setMessage(getString(R.string.main_pass_description));
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            input.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        alertDialog.setNeutralButton(R.string.action_edit_pin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mode = 0;
                editPin(-1);
                actionTextView.setText("Создайте PIN");
            }
        });
        alertDialog.setPositiveButton(R.string.action_write, null);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.key);
        alertDialog.setCancelable(false);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        Button b = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().length() <= 4)
                    Toast.makeText(LockActivity.this, "Слишком короткий", Toast.LENGTH_SHORT).show();
                else {
                    mode = 4;
                    LoadText.setMasterPass(LockActivity.this, NewAes.encrypt(input.getText().toString(), pin));
                    ((AplicationListner) getApplication()).setPin(pin);
                    savePin(pin);
                    dialog.dismiss();
                    sendMasterPassDialog();
                }
            }
        });
    }

    public void sendMesageToUser(boolean mode) {
        String key = "";
        if (mode) {
            key = LoadText.getMasterPass(this);
        } else {
            key = ((AplicationListner) getApplication()).getMasterPass();
        }
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                LoadText.getText(this, "email"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "MainPass");
        String reqest;
        if (mode)
            reqest = "/?cryptoMasterPass=" + Uri.encode(key);
        else
            reqest = "/?masterPass=" + Uri.encode(key);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "pass.add.com" + reqest);
        this.startActivity(Intent.createChooser(emailIntent,
                "Сохранение MasterPass(a)"));
    }
}
