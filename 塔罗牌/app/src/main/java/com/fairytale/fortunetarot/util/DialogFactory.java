package com.fairytale.fortunetarot.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.controller.BaseActivity;
import com.fairytale.fortunetarot.widget.AlphaButton;
import com.fairytale.fortunetarot.widget.CustomFontTextView;
/**
 * Created by lizhen on 16/2/16.
 */
public class DialogFactory {
    public static DialogFactory instance;

    public static DialogFactory getInstance () {
        if (instance == null) {
            instance = new DialogFactory();
        }
        return instance;
    }

    public void showUpddateDialog(final Context context, final String content, final String url, final boolean isForceUpdate, final Handler handler) {

    }

    public Dialog showBuyDialog(Context context,final View.OnClickListener rightClickListener,final View.OnClickListener leftClickListener) {
        final Dialog dialog = new Dialog(context,R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_buy,null);
        view.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });;
        CustomFontTextView customFontTextView_unlock = (CustomFontTextView) view.findViewById(R.id.customFontTextView_unlock);
        CustomFontTextView customFontTextView_unlock_revert = (CustomFontTextView) view.findViewById(R.id.customFontTextView_unlock_revert);
        customFontTextView_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightClickListener != null) {
                    rightClickListener.onClick(v);
                }
            }
        });
        customFontTextView_unlock_revert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftClickListener != null) {
                    leftClickListener.onClick(v);
                }
            }
        });
        int width = AndroidUtil.getScreenParams((BaseActivity)context)[0] * 8 / 10;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,width);
        view.setLayoutParams(params);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = width;
        lp.height = (int)(width * (1.6f));
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
        if (!((BaseActivity)context).isFinishing()) {
            dialog.show();
        }
        return dialog;
    }


    public Dialog showUnlockDialog(Context context, final View.OnClickListener onClickListener) {
        final Dialog dialog = new Dialog(context,R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_good_opinion,null);
        view.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });;
        CustomFontTextView customFontTextView_unlock = (CustomFontTextView) view.findViewById(R.id.customFontTextView_unlock);
        customFontTextView_unlock.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/" + "font_mini.ttf"));
        if (onClickListener != null) {
            customFontTextView_unlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v);
                    }
                }
            });
        }
        int width = AndroidUtil.getScreenParams((BaseActivity)context)[0] * 8 / 10;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,width);
        view.setLayoutParams(params);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = width;
        lp.height = (int)(width * (1.6f));
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
//        Drawable drawable = context.getResources().getDrawable(R.drawable.bg_dialog);
//        dialogWindow.setBackgroundDrawable(drawable);
        if (!((BaseActivity)context).isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

}
