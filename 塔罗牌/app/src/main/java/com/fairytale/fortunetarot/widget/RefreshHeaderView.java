package com.fairytale.fortunetarot.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.aspsine.irecyclerview.RefreshTrigger;
import com.fairytale.fortunetarot.R;

/**
 * Created by aspsine on 16/3/14.
 */
public class RefreshHeaderView extends RelativeLayout implements RefreshTrigger {

    private int mHeight;

    private CustomFontTextView tvRefresh;

    private SwingIndicator swingIndicator;

    public RefreshHeaderView(Context context) {
        this(context, null);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.layout_irecyclerview_classic_refresh_header_view, this);
        tvRefresh = (CustomFontTextView) findViewById(R.id.tvRefresh);
        swingIndicator = (SwingIndicator) findViewById(R.id.swingIndicator);
    }

    @Override
    public void onStart(boolean automatic, int headerHeight, int finalHeight) {
        this.mHeight = headerHeight;
    }

    @Override
    public void onMove(boolean isComplete, boolean automatic, int moved) {
        swingIndicator.setVisibility(VISIBLE);
        if (!isComplete) {
            if (moved <= mHeight) {
                tvRefresh.setText("下拉刷新");
            } else {
                tvRefresh.setText("松开刷新");
            }
        }
    }

    @Override
    public void onRefresh() {
        tvRefresh.setText("加载中");
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void onComplete() {
        swingIndicator.setVisibility(GONE);
        tvRefresh.setText("加载完成");
    }

    @Override
    public void onReset() {
        swingIndicator.setVisibility(GONE);
    }
}
