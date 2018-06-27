package com.webant.password.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.webant.password.manager.DBase.DBWorker;
import com.webant.password.manager.DBase.LoadText;

public class DialogWindows {
    public static void exit(final Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Вы точно хотите выйти?")
                .setPositiveButton("выйти", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new DBWorker(context).setNull();
                        LoadText.setNull(context);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                                            }
                })
                .setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create().show();
    }
    public static void sendMesageToUser(Context context,boolean mode) {
        String key = "";
        if (mode) {
            key = LoadText.getMasterPass(context);
        } else {
            key = ((AplicationListner) context.getApplicationContext()).getMasterPass();
        }
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                LoadText.getText(context, "email"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "MainPass");
        String reqest;
        if (mode)
            reqest = "/?cryptoMasterPass=" + Uri.encode(key);
        else
            reqest = "/?masterPass=" + Uri.encode(key);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "pass.add.com" + reqest);
        context.startActivity(Intent.createChooser(emailIntent,
                context.getString(R.string.message_to_save_main_pass_on_mail)));
    }
}
