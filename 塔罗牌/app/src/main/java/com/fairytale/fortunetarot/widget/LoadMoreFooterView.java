package com.fairytale.fortunetarot.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.widget.spinnerloading.SpinnerLoading;

/**
 * Created by aspsine on 16/3/14.
 */
public class LoadMoreFooterView extends FrameLayout {

    private Status mStatus;

    private View loading_view;

    private SpinnerLoading spinner_loading;

    private CustomFontTextView tv_loading;

    public LoadMoreFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loading_view = LayoutInflater.from(context).inflate(R.layout.layout_irecyclerview_load_more_footer_view, this, true);

        spinner_loading = (SpinnerLoading) findViewById(R.id.spinnerLoading);
        tv_loading = (CustomFontTextView) findViewById(R.id.tv_loading);
        setStatus(Status.GONE);
    }


    public void setStatus(Status status) {
        this.mStatus = status;
        change();
    }

    public Status getStatus() {
        return mStatus;
    }

    public boolean canLoadMore() {
        return mStatus == Status.GONE || mStatus == Status.ERROR;
    }

    private void change() {
        switch (mStatus) {
            case GONE:
                loading_view.setVisibility(GONE);
                break;

            case LOADING:
                loading_view.setVisibility(VISIBLE);
                spinner_loading.setVisibility(VISIBLE);
                tv_loading.setText("加载中");

                break;

//            case ERROR:
//                mLoadingView.setVisibility(GONE);
//                mErrorView.setVisibility(VISIBLE);
//                mTheEndView.setVisibility(GONE);
//                break;

            case THE_END:
                loading_view.setVisibility(VISIBLE);
                spinner_loading.setVisibility(GONE);
                tv_loading.setVisibility(VISIBLE);
                tv_loading.setText("没有更多内容啦");
                break;
        }
    }

    public enum Status {
        GONE, LOADING, ERROR, THE_END
    }

}
