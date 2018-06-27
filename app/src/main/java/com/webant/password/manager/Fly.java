package com.webant.password.manager;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by dikiy on 08.02.2018.
 */

public class Fly extends Service {

    WindowManager wm;
    LinearLayout ll;
    private String pass = "";
    private String login = "";

    @Override
    public IBinder onBind(Intent intent) {


        return null;


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        pass = intent.getExtras().getString("pass");
        login = intent.getExtras().getString("login");
        Log.v("asdaqweq123", pass);
        Log.v("asdaqweq123", login);
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate() {

        // TODO Auto-generated method stub
        super.onCreate();
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay();
        ll = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParameteres = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(layoutParameteres);
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        final WindowManager.LayoutParams parameters = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,

//                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//                WindowManager.LayoutParams.TYPE_PHONE
                LAYOUT_FLAG
                ,
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                       | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
//                       | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                ,

                PixelFormat.TRANSLUCENT
        );
        parameters.gravity = Gravity.RIGHT;
        parameters.x = 0;
        parameters.y = -300;

//        TextView stop = new TextView(this);
//        stop.setText("Все пиздец, перезагружай");
//        ViewGroup.LayoutParams btnParameters = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        stop.setLayoutParams(btnParameters);

        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.flyactivity
                        , ll, false);
        final FrameLayout copylogin = itemView.findViewById(R.id.copylogin);
        final FrameLayout copypassword = itemView.findViewById(R.id.copypassword);
        FrameLayout main = itemView.findViewById(R.id.main);

        final FrameLayout back = itemView.findViewById(R.id.back);
        final FrameLayout close = itemView.findViewById(R.id.closeP);
        copylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Fly.this, "copy login", Toast.LENGTH_SHORT).show();


                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("123", pass);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Fly.this, "copy password", Toast.LENGTH_SHORT).show();


            }
        });
        copypassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("123", login);
                clipboard.setPrimaryClip(clip);
                clipboard.getPrimaryClip();
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (copylogin.getVisibility() == View.VISIBLE) {
                    Animation anim = AnimationUtils.loadAnimation(Fly.this, R.anim.flye);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            copylogin.setVisibility(View.GONE);
                            copypassword.setVisibility(View.GONE);
                            close.setVisibility(View.GONE);
                            back.setVisibility(View.GONE);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    copylogin.startAnimation(anim);
                    copypassword.startAnimation(anim);
                    close.startAnimation(anim);
                    back.startAnimation(anim);
                } else {
                    Animation anim = AnimationUtils.loadAnimation(Fly.this, R.anim.flys);
                    copylogin.startAnimation(anim);
                    copypassword.startAnimation(anim);
                    close.startAnimation(anim);
                    back.startAnimation(anim);
                    copylogin.setVisibility(View.VISIBLE);
                    copypassword.setVisibility(View.VISIBLE);
                    close.setVisibility(View.VISIBLE);
                    back.setVisibility(View.VISIBLE);
                }

//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                ComponentName componentName = new ComponentName(
//                        "com.example.dikiy.passwordmain",
//                        "MainActivity");
//                intent.setComponent(componentName);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                wm.removeView(ll);
//                stopSelf();
//                System.exit(0);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(ll);
                stopSelf();

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                ComponentName componentName = new ComponentName(
                        "com.webant.password.manager","com.webant.password.manager.PreloaderActivity");
                intent.setComponent(componentName);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                wm.removeView(ll);
                stopSelf();
                System.exit(0);
            }
        });
        ll.addView(itemView);


        wm.addView(ll, parameters);

        main.setOnTouchListener(new View.OnTouchListener() {
            WindowManager.LayoutParams updatedParameters = parameters;
            double x;
            double y;
            double pressedX;
            double pressedY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

//                        x = updatedParameters.x;J
                        y = updatedParameters.y;

//                        pressedX = event.getRawX();
                        pressedY = event.getRawY();

                        break;

                    case MotionEvent.ACTION_MOVE:
//                        updatedParameters.x = (int) (x - (event.getRawX() - pressedX));
                        updatedParameters.y = (int) (y + (event.getRawY() - pressedY));

                        wm.updateViewLayout(ll, updatedParameters);

                    default:
                        break;
                }

                return false;
            }
        });


    }

    @Override
    public void onDestroy() {
        Log.v("123123123213221", "1");
        super.onDestroy();
        stopSelf();
    }
}