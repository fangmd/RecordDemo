package com.adouble.recorddemo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import com.adouble.recorddemo.BaseApplication;


/**
 * Created by double on 16-6-9.
 * Project: WayTone
 */
public class ToastUtil {

    private static Toast sToast;
    private static Toast sLToast;
    private static Toast sCToast;
    private static Toast sCLToast;

    private static Handler mHandler;

    static {
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * - @param context
     * - @param text
     */
    @SuppressLint("ShowToast")
    public static void showToast(Context context, String text) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(text);
        }
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                sToast.show();
            }
        });
    }

    /**
     * - @param text
     */
    @SuppressLint("ShowToast")
    public static void showToast(String text) {
        if (sToast == null) {
            sToast = Toast.makeText(BaseApplication.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(text);
        }
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                sToast.show();
            }
        });
    }

    /**
     * - @param context
     * - @param text
     */
    @SuppressLint("ShowToast")
    public static void showLToast(Context context, String text) {
        if (sLToast == null) {
            sLToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG);
        } else {
            sLToast.setText(text);
        }
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                sLToast.show();
            }
        });
    }

    /**
     * - @param context
     * - @param resId
     */
    public static void showToast(Context context, int resId) {
        String text = context.getString(resId);
        showToast(context, text);
    }

    /**
     * - @param context
     * - @param text
     */
    @SuppressLint("ShowToast")
    public static void showCToast(Context context, String text) {
        if (sCToast == null) {
            sCToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
            sCToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            sCToast.setText(text);
        }
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                sCToast.show();
            }
        });
    }

    /**
     * - @param context
     * - @param resId
     */
    public static void showCToast(Context context, int resId) {
        String text = context.getString(resId);
        showCToast(context, text);
    }


    /**
     * - 提示字符串
     * - short Toast
     * <p/>
     * - @param context
     * - @param text
     */
    @SuppressLint("ShowToast")
    public static void showCLToast(Context context, String text) {
        if (sCLToast == null) {
            sCLToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            sCLToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            sCLToast.setText(text);
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                sCLToast.show();
            }
        });
    }


    /**
     * - 提示根据ResId关联字符串
     * - 时常long   Toast
     * <p/>
     * - @param context
     * - @param resId
     */
    public static void showCLToast(Context context, int resId) {
        String s = context.getString(resId);
        showCLToast(context, s);
    }

}
