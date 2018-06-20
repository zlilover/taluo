package com.fairytale.fortunetarot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.adapter.InfoPagerAdapter;
import com.fairytale.fortunetarot.entity.TitleEntity;
import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.util.JsonUtils;
import com.fairytale.fortunetarot.util.SPUtil;
import com.fairytale.fortunetarot.util.Util;
import com.fairytale.fortunetarot.widget.ViewPagerIndicator;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by lizhen on 2018/1/22.
 */

public class DivinationFragment extends BaseFragment {

    private static DivinationFragment instance;
    private ViewPagerIndicator viewPagerIndicator;
    private ViewPager viewPager;
    private InfoPagerAdapter infoPagerAdapter;

    public  static DivinationFragment newInstance() {
        if (instance == null) {
            instance = new DivinationFragment();
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

    private String[] initTitles() {
        ArrayList<TitleEntity> titleInfos;
        if (TextUtils.isEmpty(SPUtil.get(getContext(),"divination_titles","").toString())) {
            String text = Util.getStringfromAssets(getContext(),"cardarrayinfo/info.txt");
            if (text == null) {
                return null;
            }
            String[] titles_temp = text.split("@");
            titleInfos = new ArrayList<>();
            for (int i = 0; i < titles_temp.length;i++ ) {
                TitleEntity titleInfo = new TitleEntity();
                titleInfo.setId(Integer.parseInt(titles_temp[i].split("#")[0]));
                titleInfo.setTitle(titles_temp[i].split("#")[1]);
                titleInfos.add(titleInfo);
            }
            SPUtil.put(getContext(),"divination_titles", JsonUtils.entityToJsonString(titleInfos));
        } else {
            titleInfos = JsonUtils.jsonStringToEntity(SPUtil.get(getContext(),"divination_titles","").toString(),
                    new TypeToken<ArrayList<TitleEntity>>(){}.getType());
        }
        String[] titles = new String[titleInfos.size()];
        for (int i = 0; i < titleInfos.size();i++ ) {
            titles[i] = titleInfos.get(i).getTitle();
        }
        return titles;
    }

    private void initView(View view) {
        viewPagerIndicator = initViewById(view, R.id.viewPagerIndicator);

        String[] titles = initTitles();
        viewPagerIndicator.setTitles(titles);
        viewPager = initViewById(view,R.id.viewPager);
        viewPagerIndicator.setOnPageChangeListener(new ViewPagerIndicator.OnPageChangeListener() {
            @Override
            public void onPageChanged(int currentPage) {
                viewPager.setCurrentItem(currentPage);
            }
        });
        Class[] fragments = new Class[titles.length];
        for (int i = 0; i < fragments.length;i++) {
            fragments[i] = DivinationListFragment.class;
        }
        infoPagerAdapter = new InfoPagerAdapter(getChildFragmentManager(),fragments);
        infoPagerAdapter.setTitles(titles);
        viewPager.setAdapter(infoPagerAdapter);
        viewPagerIndicator.setViewPager(viewPager);
    }

    @Override
    BasePresenter setPresenter() {
        return null;
    }

}
