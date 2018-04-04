package com.fairytale.fortunetarot.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.controller.BaseActivity;
import com.fairytale.fortunetarot.util.AndroidUtil;
import com.fairytale.fortunetarot.util.Logger;

import java.util.ArrayList;

/**
 * Created by lizhen on 2018/2/8.
 */

public class ViewPagerIndicator extends FrameLayout implements View.OnClickListener{
    private String[] titles;
    private LinearLayout ll_titles;
    private View scroll_wrap;
    private ArrayList<TextView> tv_titles;
    private Context context;
    private int currentPos;//当前游标位置
    private int lastPos;//上次游标位置
    private int scrollDistance;
    private OnPageChangeListener onPageChangeListener;
    private ViewPager viewPager;
    private boolean isScrollEnable = false;
    private HorizontalScrollView horizontalScrollView;

    public ViewPagerIndicator(Context context) {
        super(context);
        initView(context);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.frame_indicator,this);
        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollView);
        ll_titles = (LinearLayout) view.findViewById(R.id.ll_title);
        scroll_wrap = view.findViewById(R.id.scroll_wrap);
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Logger.e("pos", "" + position);
                if (currentPos != position) {
                    currentPos = position;
                    changeSelected();
                    if (onPageChangeListener != null) {
                        onPageChangeListener.onPageChanged(currentPos);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (titles.length != 0 && scroll_wrap.getMeasuredHeight() == getMeasuredHeight()) { // 游标未初始化
            int width = getTextRect(titles[0],tv_titles.get(0)).width();
            int height = getTextRect(titles[0],tv_titles.get(0)).height();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) scroll_wrap.getLayoutParams();
            params.width = width + AndroidUtil.dip2px(context, 24.0f);
            params.height = height + AndroidUtil.dip2px(context, 14.0f);
            params.leftMargin = (tv_titles.get(0).getMeasuredWidth() - params.width) / 2;
            Logger.e("params.leftMargin", "" + params.leftMargin);
            scrollDistance = tv_titles.get(0).getMeasuredWidth();
            scroll_wrap.setLayoutParams(params);
        }
    }

    private Rect getTextRect(String content,TextView textView) {
        Rect bounds = new Rect();
        TextPaint textPaint = textView.getPaint();
        textPaint.getTextBounds(content,0,content.length(),bounds);
        return bounds;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
        if (titles.length > 5) {
            isScrollEnable = true;
        }
        int childCount = ll_titles.getChildCount();
        if (childCount == 0) {
            configTitleContainer();
        } else {
            configTitleView();
        }
        ll_titles.requestLayout();
    }

    private void configTitleContainer() {

        Typeface typeface;
        try {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/font_italics.ttf");
        } catch (RuntimeException e) {
            typeface = null;
        }
        tv_titles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((AndroidUtil.getScreenParams(((BaseActivity)context))[0] - AndroidUtil.dip2px(context,16) * 2)/ 5, LinearLayout.LayoutParams.MATCH_PARENT);
            if (isScrollEnable) {
                params = new LinearLayout.LayoutParams((AndroidUtil.getScreenParams(((BaseActivity)context))[0] - AndroidUtil.dip2px(context,16) * 2) / 4,LinearLayout.LayoutParams.MATCH_PARENT);
            }
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(context.getResources().getColorStateList(R.color.title_color_selector));
            textView.setLayoutParams(params);
            textView.setText(titles[i]);
            textView.setTag(i);
            if (typeface != null) {
                textView.setTypeface(typeface);
            }
            textView.getPaint().setFakeBoldText(true);
            textView.setOnClickListener(this);
            ll_titles.addView(textView);
            tv_titles.add(textView);
            tv_titles.get(0).setSelected(true);
        }
    }

    private void configTitleView() {
        for (int i = 0 ;i < ll_titles.getChildCount();i++) {
            ((TextView)ll_titles.getChildAt(i)).setText(titles[i]);
        }
    }

    @Override
    public void onClick(View v) {
        currentPos = (int) v.getTag();
        changeSelected();
    }

    private void changeSelected() {
        if (lastPos != currentPos) {
            for (int i = 0; i < titles.length;i ++) {
                tv_titles.get(i).setSelected(i == currentPos);
            }
            int currentLeftMargin = ((FrameLayout.LayoutParams)scroll_wrap.getLayoutParams()).leftMargin;
            int targetLeftMargin = currentLeftMargin + (currentPos - lastPos) * scrollDistance;
            ScrollViewWrapper scrollViewWrapper = new ScrollViewWrapper(scroll_wrap);
            ObjectAnimator.ofInt(scrollViewWrapper,"leftMargin",currentLeftMargin,targetLeftMargin).setDuration(100).start();
            if (isScrollEnable) {
                if (currentPos < lastPos ) {
                    if (ll_titles.getChildAt(currentPos).getLeft() < (ll_titles.getChildCount() - 4 ) * scrollDistance) { // 右滑
                        horizontalScrollView.scrollBy(-scrollDistance,0);
                    }
                } else {
                    if (ll_titles.getChildAt(currentPos).getRight() > scrollDistance * 4) { // 说明tab未完全显示
                        horizontalScrollView.scrollBy(scrollDistance,0);
                    }
                }
            }
            lastPos = currentPos;
            viewPager.setCurrentItem(lastPos);
        }

    }

    private class ScrollViewWrapper {
        private View view;
        private FrameLayout.LayoutParams params;
        ScrollViewWrapper(View view) {
            this.view = view;
            params = (FrameLayout.LayoutParams)view.getLayoutParams();
        }

        public void setLeftMargin(int leftMargin) {
            params.leftMargin = leftMargin;
            view.setLayoutParams(params);
            view.requestLayout();
        }

        public int getLeftMargin() {
            return params.leftMargin;
        }
    }

    public interface OnPageChangeListener {
        void onPageChanged(int currentPage);
    }

}
