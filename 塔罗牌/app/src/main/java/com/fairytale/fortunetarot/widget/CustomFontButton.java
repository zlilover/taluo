package com.fairytale.fortunetarot.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.fairytale.fortunetarot.R;

/**
 * Created by lizhen on 2018/4/8.
 */

public class CustomFontButton  extends AppCompatButton {

    private Typeface typeface;

    public CustomFontButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public CustomFontButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context,AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomFontButton);
        String font = array.getString(R.styleable.CustomFontButton_btn_customFont);
        boolean isBold = array.getBoolean(R.styleable.CustomFontButton_btn_isBold,false);
        String tag = array.getString(R.styleable.CustomFontButton_btn_departTag);
        int departByPos = array.getInt(R.styleable.CustomFontButton_btn_departByPos,0);
        array.recycle();
        String text = getText().toString();
        if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(tag) && departByPos > 0) {
            String verticalText = stringInsertWithTag(text,departByPos,tag);
            setText(verticalText);
        }
        if (!TextUtils.isEmpty(font)) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(),"fonts/" + font);
            } catch (Exception e) {
                typeface = null;
            }
        }
        if (typeface != null) {
            setTypeface(typeface);
        }
        TextPaint paint = getPaint();
        paint.setFakeBoldText(isBold);

    }

    public void setVerticalText(String text) {
        setVerticalTextWithTag(text, "\n" ,1);
    }

    public void setVerticalTextWithTag(String text,String tag, int pos) {
        if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(tag) && pos > 0) {
            String verticalText = stringInsertWithTag(text,pos,tag);
            setText(verticalText);
        }
    }

    private String stringInsertWithTag(String source, int space, String tag) {
        if (TextUtils.isEmpty(source)) {
            return null;
        }
        char[] latters = source.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < latters.length;i++) {
            if (((i+1)%space) == 0) {
                if (space == 1 && i == latters.length - 1) {
                    stringBuilder.append(String.valueOf(latters[i]));
                } else {
                    stringBuilder.append(String.valueOf(latters[i])).append(tag);
                }
            } else {
                stringBuilder.append(String.valueOf(latters[i]));
            }
        }
        return stringBuilder.toString();
    }
}
