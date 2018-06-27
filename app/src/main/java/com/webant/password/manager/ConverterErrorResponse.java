package com.webant.password.manager;

import android.content.Context;
import android.view.Gravity;
import android.widget.Switch;
import android.widget.Toast;

public class ConverterErrorResponse {
    int code = 0;
    String message = "";

    public ConverterErrorResponse(int code, String message) {
        this.code = code;
        if (message != null)
            this.message = message;
    }

    public static void connectionFailed(Context context) {
        Toast toast = Toast.makeText(context, R.string.no_connect_to_internet_message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void sendMessage(Context context) {
        switch (code) {
            case 409:
                message = context.getString(R.string.is_not_empty);
                break;
            case 500:
                message = context.getString(R.string.server_unknown_error);
        }
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public String getRegisterMessage(Context context) {
        Toast toast;
        switch (code) {
            case 409:
                message = context.getString(R.string.email_is_already_used);
                break;
            case 400:
                message = context.getString(R.string.invalid_email_response);
                break;
            case 500:
                toast = Toast.makeText(context, context.getString(R.string.server_unknown_error), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                message = "";
                break;
            default:
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                message = "";
        }
        return message;
    }

    public String createItem(Context context) {
        Toast toast;
        switch (code) {
            case 400:
                message = context.getString(R.string.name_is_already_used);
                break;
            case 403:
                message = context.getString(R.string.name_is_already_used);
                break;
            case 500:
                toast = Toast.makeText(context, context.getString(R.string.server_unknown_error), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                message = "";
                break;
            default:
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                message = "";
        }
        return message;
    }

    public void sendMessageSharePass(Context context) {
        switch (code) {
            case 404:
                message = context.getString(R.string.there_is_no_such_user);
                break;
            case 500:
                message = context.getString(R.string.server_unknown_error);
        }
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void deleteItemsMessage(Context context) {
        switch (code) {
            case 409:
                message = context.getString(R.string.folder_is_not_empty);
                break;
            case 500:
                message = context.getString(R.string.server_unknown_error);
        }
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public String  loginMessage(Context context) {
        Toast toast;
        switch (code) {
            case 400:
                message = context.getString(R.string.invalid_login);
                break;
            case 500:
                toast = Toast.makeText(context, context.getString(R.string.server_unknown_error), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                message = "";
                break;
            default:
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                message = "";
        }
        return message;
    }
}
