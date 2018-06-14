package com.example.dikiy.passwordmain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.fingerprint.FingerprintUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 09.03.2018.
 */

public class LockActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String PIN = "pin";
    TextView actionTextView, tvb0, tvb1, tvb2, tvb3, tvb4, tvb5, tvb6, tvb7, tvb8, tvb9, tvbLogout, tvbClear;
    int mode = 0;
    String pin = "", checkPin = "";
    List<TextView> stars = new ArrayList<>();

    private SharedPreferences mPreferences;
    private FingerprintHelper mFingerprintHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, R.anim.flye);
        super.onCreate(savedInstanceState);
        if (LoadText.getText(this, "access_token").isEmpty()) {
            Toast.makeText(this, "сначало войдите", Toast.LENGTH_LONG);
            logout();
        }
        setContentView(R.layout.lock_activity);
        init();
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
        if (!LoadText.getText(this, "pin").isEmpty()) {
            actionTextView.setText("Введите PIN");
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
            actionTextView.setText("Создайте PIN");
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

    public void checkPin() {
        switch (mode) {
            case 0:
                actionTextView.setText("Потвердите PIN");
                checkPin = pin;
                editPin(-1);
                mode = 1;
                break;
            case 1:
                if (pin.equals(checkPin)) {
                    LoadText.setText(this, "pin", pin);
                    login();
                    finish();
                } else {
                    editPin(-1);
                    actionTextView.setText("Создайте PIN");
                    mode = 0;
                }
                break;
            case 2:
                if (pin.equals(LoadText.getText(this, "pin"))) {
                    login();
                } else {
                    editPin(-1);
                    Toast.makeText(this, "Неверный PIN", Toast.LENGTH_SHORT).show();
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
        if (!LoadText.getText(this, "pin").isEmpty()) {
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
        if (getIntent().getData() != null) {
            intent = new Intent(this, ShareGetActivity.class);
            intent.setData(getIntent().getData());
        } else
            intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void prepareSensor() {
        Log.v("testLog", String.valueOf(FingerprintUtils.checkSensorState(this)));
        if (FingerprintUtils.checkSensorState(this) == FingerprintUtils.mSensorState.READY) {
            RelativeLayout fingerRelativeLayout = findViewById(R.id.fingerRelativeLayout);
            fingerRelativeLayout.setVisibility(View.VISIBLE);
            mFingerprintHelper = new FingerprintHelper(this);
            mFingerprintHelper.startAuth();
        }
    }


    public class FingerprintHelper extends FingerprintManagerCompat.AuthenticationCallback {
        private Context mContext;
        private CancellationSignal mCancellationSignal;

        FingerprintHelper(Context context) {
            mContext = context;
        }

        void startAuth() {
            FingerprintManagerCompat manager = FingerprintManagerCompat.from(mContext);
            manager.authenticate(null, 0, mCancellationSignal, this, null);
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
            login();
        }

        @Override
        public void onAuthenticationFailed() {
            Toast.makeText(mContext, "Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
        }

    }

}
