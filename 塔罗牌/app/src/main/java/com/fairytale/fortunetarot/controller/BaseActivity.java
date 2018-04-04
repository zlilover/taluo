package com.fairytale.fortunetarot.controller;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.util.ActivityManager;
import com.fairytale.fortunetarot.view.BaseView;
import com.fairytale.fortunetarot.widget.CustomFontTextView;
import com.fairytale.fortunetarot.widget.SwingIndicator;

/**
 * Created by lizhen on 2018/1/4.
 */

public abstract class BaseActivity extends FragmentActivity implements BaseView{
    private RelativeLayout rl_top;
    private CustomFontTextView tv_title;
    private ImageView img_base_left;
    private ImageView img_base_right;
    private LinearLayout ll_content;
    private SwingIndicator swingIndicator;
    private BasePresenter basePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base);
        initView();
        addChildView();
        initSelf();
        basePresenter = initPresenter();
        initData();
        ActivityManager.getAppManager().addActivity(this);
    }


    private void initView() {
        rl_top = initViewById(R.id.rl_top);
        tv_title = initViewById(R.id.tv_title);
        img_base_left = initViewById(R.id.img_base_left);
        img_base_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_base_right= initViewById(R.id.img_base_right);
        ll_content = initViewById(R.id.ll_content);
        swingIndicator = initViewById(R.id.swingIndicator);
    }

    protected abstract BasePresenter initPresenter();

    protected abstract void initData();

    protected abstract void initSelf();

    protected abstract int getLayoutId();

    protected void needTop(boolean isVisible) {
        rl_top.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    protected void needBack(boolean isVisible)  {
        img_base_left.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    protected void setImg_base_right(int laeblId, View.OnClickListener onClickListener) {
        img_base_right.setVisibility(View.VISIBLE);
        img_base_right.setImageResource(laeblId);
        img_base_right.setOnClickListener(onClickListener);
    }

    protected void setTv_title(String title) {
        tv_title.setText(title);
    }

    protected void setTv_titleWithTypeface(String title, String fontPath) {
        Typeface typeface;
        try {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fontPath);
        } catch (RuntimeException e) {
            typeface = null;
        }
        tv_title.setText(title);
        if (typeface != null) {
            tv_title.setTypeface(typeface);
        }
    }

    private void addChildView() {
        if (getLayoutId() == 0) {
            return;
        }
        LayoutInflater.from(this).inflate(getLayoutId(),ll_content,true);
    }

    protected <T extends View> T initViewById(int id) {
        return (T) findViewById(id);
    }


    protected void showProgress(){
        swingIndicator.setVisibility(View.VISIBLE);
    }

    protected void hideProgress(){
        swingIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loadingDialog() {
        showProgress();
    }

    @Override
    public void finishLoading() {
        hideProgress();
    }

    @Override
    protected void onStop() {
        if (basePresenter != null) {
            basePresenter.cancelAll();
        }
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
        ActivityManager.getAppManager().removeActivity(this);
    }
}
