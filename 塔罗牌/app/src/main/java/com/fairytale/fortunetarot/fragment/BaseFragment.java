package com.fairytale.fortunetarot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.view.BaseView;

/**
 * Created by lizhen on 2018/1/22.
 */

public abstract class BaseFragment extends Fragment implements BaseView{
    protected BasePresenter basePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        basePresenter = setPresenter();
    }

    public void refresh() {}

    protected <T extends View> T initViewById(View view, int id) {
        return (T)view.findViewById(id);
    }

    @Override
    public void finishLoading() {

    }

    @Override
    public void loadingDialog() {

    }

    public void nofifyChanged() {}

    abstract BasePresenter setPresenter();

    @Override
    public void onDestroy() {
        if (basePresenter != null) {
            basePresenter.cancelAll();
        }
        super.onDestroy();
    }
}
