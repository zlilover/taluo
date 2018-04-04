package com.fairytale.fortunetarot.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by lizhen on 2018/4/3.
 */

public class CustomFrameLayout extends FrameLayout {
    private int childCount;
    private int height;

    public CustomFrameLayout(@NonNull Context context) {
        super(context);
    }

    public CustomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (getBackground() != null) {
            int width = getBackground().getIntrinsicWidth();
            int height = getBackground().getIntrinsicHeight();
            float scale = ((float)width)/height;
//            if (measureMode == MEASURE_BY_HEIGHT) {
//                measuredWidth = (int)(measuredHeight * scale);
//
//            } else if (measureMode == MEASURE_BY_WIDTH){
                measuredHeight = (int)(measuredWidth / scale);
//            }
        }
        setMeasuredDimension(measuredWidth, measuredHeight);

    }
}
