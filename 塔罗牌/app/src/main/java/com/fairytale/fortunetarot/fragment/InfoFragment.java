package com.fairytale.fortunetarot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.adapter.InfoPagerAdapter;
import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.widget.ViewPagerIndicator;

/**
 * Created by lizhen on 2018/1/22.
 */

public class InfoFragment extends BaseFragment{
    private static InfoFragment instance;
    private ViewPagerIndicator viewPagerIndicator;
    private ViewPager viewPager;
    private InfoPagerAdapter infoPagerAdapter;

    public  static InfoFragment newInstance() {
        if (instance == null) {
            instance = new InfoFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        viewPagerIndicator = initViewById(view, R.id.viewPagerIndicator);
        viewPagerIndicator.setTitles(new String[] {"占卜","运势","测试","教程","知识"});
        viewPager = initViewById(view,R.id.viewPager);
        viewPagerIndicator.setOnPageChangeListener(new ViewPagerIndicator.OnPageChangeListener() {
            @Override
            public void onPageChanged(int currentPage) {
                viewPager.setCurrentItem(currentPage);
            }
        });
        infoPagerAdapter = new InfoPagerAdapter(getChildFragmentManager(),new Class[]{InfoListFragment.class,InfoListFragment.class,InfoListFragment.class,InfoListFragment.class,InfoListFragment.class});
        viewPager.setAdapter(infoPagerAdapter);
        viewPagerIndicator.setViewPager(viewPager);
    }

    @Override
    BasePresenter setPresenter() {
        return null;
    }
}
