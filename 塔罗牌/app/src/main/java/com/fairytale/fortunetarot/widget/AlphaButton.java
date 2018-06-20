package com.fairytale.fortunetarot.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.fairytale.fortunetarot.R;

/**
 * Created by lizhen on 2018/4/8.
 */

public class AlphaButton extends AppCompatButton {
    private int clickAlpha;

    public AlphaButton(Context context) {
        super(context);
    }

    public AlphaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AlphaButton);
        clickAlpha = array.getInt(R.styleable.AlphaButton_clickAlpha,255);
        array.recycle();

    }

    public AlphaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getBackground().setAlpha(clickAlpha);
                break;

            case MotionEvent.ACTION_UP:
                getBackground().setAlpha(255);
                break;
        }
        return super.onTouchEvent(event);
    }
}
