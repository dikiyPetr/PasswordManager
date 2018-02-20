package com.example.dikiy.passwordmain;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dikiy on 08.02.2018.
 */

public class Fly extends Service {

    WindowManager wm;
    LinearLayout ll;

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("123123123213221","1");
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.v("123123123213221","1");
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay();
        ll = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParameteres = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(layoutParameteres);
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        final WindowManager.LayoutParams parameters = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,

//                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//                WindowManager.LayoutParams.TYPE_PHONE
                LAYOUT_FLAG
                ,
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                       | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
//                       | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                         WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                ,

                PixelFormat.TRANSLUCENT
        );
        parameters.gravity = Gravity.CENTER | Gravity.BOTTOM;
        parameters.x = 0;
        parameters.y = 0;

//        TextView stop = new TextView(this);
//        stop.setText("Все пиздец, перезагружай");
//        ViewGroup.LayoutParams btnParameters = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        stop.setLayoutParams(btnParameters);

        View itemView = LayoutInflater.from(GetContext.getContext())
                .inflate(R.layout.flyactivity,ll,false);
        ImageView copylogin = itemView.findViewById(R.id.copylogin);
        ImageView copypassword = itemView.findViewById(R.id.copypassword);
        ImageView close = itemView.findViewById(R.id.close);
        ImageView back = itemView.findViewById(R.id.back);
        copylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetContext.getContext(),"copy login",Toast.LENGTH_LONG).show();
            }
        });
        copypassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetContext.getContext(),"copy password",Toast.LENGTH_LONG).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                ComponentName componentName = new ComponentName(
                        "com.example.dikiy.passwordmain",
                        "com.example.dikiy.passwordmain.Main2Activity");
                intent.setComponent(componentName);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                wm.removeView(ll);
                stopSelf();
                System.exit(0);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(ll);
                stopSelf();
                System.exit(0);
            }
        });
        ll.addView(itemView);


        wm.addView(ll, parameters);

        ll.setOnTouchListener(new View.OnTouchListener() {
            WindowManager.LayoutParams updatedParameters = parameters;
            double x;
            double y;
            double pressedX;
            double pressedY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

//                        x = updatedParameters.x;
                        y = updatedParameters.y;

//                        pressedX = event.getRawX();
                        pressedY = event.getRawY();

                        break;

                    case MotionEvent.ACTION_MOVE:
//                        updatedParameters.x = (int) (x + (event.getRawX() - pressedX));
                        updatedParameters.y = (int) (y - (event.getRawY() - pressedY));

                        wm.updateViewLayout(ll, updatedParameters);

                    default:
                        break;
                }

                return false;
            }
        });

//        ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                wm.removeView(ll);
//                stopSelf();
//                System.exit(0);
//
//            }
//        });
    }

    @Override
    public void onDestroy() {
        Log.v("123123123213221","1");
        super.onDestroy();
        stopSelf();
    }
}