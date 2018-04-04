package com.fairytale.fortunetarot.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.util.Logger;

/**
 * Created by lizhen on 2018/1/30.
 */

public class CustomImageView extends AppCompatImageView {
    private int measureMode;
    private boolean autoMeasure; // 是采用系统自动计算宽高还是手动计算
    private final int MEASURE_BY_HEIGHT= 0;
    private final int MEASURE_BY_WIDTH = 1;
    private boolean isSquare;

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);
        measureMode = array.getInt(R.styleable.CustomImageView_isMeasuredByWithOrHeight, -1);
        autoMeasure = array.getBoolean(R.styleable.CustomImageView_autoMeasure,true);
        isSquare = array.getBoolean(R.styleable.CustomImageView_isSquare,false);
        array.recycle();
        if (measureMode != -1 && autoMeasure) {
           throw  new RuntimeException("if you want to measure this view yourself,you should specify a mode int xml like app:isMeasuredByWithOrHeight=\"width\" to decide how to measure this view ");
        }
        if (getScaleType() != ScaleType.FIT_CENTER && !isSquare) {
            throw  new RuntimeException("don't specify ScaleType in xml for this view when you don't want keep scale for image");
        }
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!autoMeasure) {
            int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
            int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
            if (isSquare) {
                if (measureMode == MEASURE_BY_HEIGHT) {
                    measuredWidth = measuredHeight;
                } else {
                    measuredHeight = measuredWidth;
                }
            }
            else if (getDrawable() != null) {
                int width = getDrawable().getBounds().width();
                int height = getDrawable().getBounds().height();
                float scale = ((float)width)/height;
                if (measureMode == MEASURE_BY_HEIGHT) {
                    measuredWidth = (int)(measuredHeight * scale);

                } else if (measureMode == MEASURE_BY_WIDTH){
                    measuredHeight = (int)(measuredWidth / scale);
                }
            }
            setMeasuredDimension(measuredWidth, measuredHeight);
        }
    }
}
