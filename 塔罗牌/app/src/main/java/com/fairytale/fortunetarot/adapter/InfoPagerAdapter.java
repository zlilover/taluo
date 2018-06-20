package com.fairytale.fortunetarot.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fairytale.fortunetarot.fragment.BaseFragment;
import com.fairytale.fortunetarot.fragment.InfoListFragment;

/**
 * Created by lizhen on 2018/2/26.
 */

public class InfoPagerAdapter extends FragmentPagerAdapter {
    private Class[] fragmentList;
    private BaseFragment[] fragments;
    private String[] titles;

    public InfoPagerAdapter(FragmentManager fragmentManager,Class[] fragmentList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
        fragments = new BaseFragment[fragmentList.length];
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getCount() {
        return fragmentList.length;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = fragments[position];
        if (fragment == null) {
            try {
                fragment = (BaseFragment) fragmentList[position].newInstance();
                Bundle bundle = new Bundle();
                bundle.putInt("type",position);
                if (titles != null) {
                    bundle.putString("groupName",titles[position]);
                }
                fragment.setArguments(bundle);
                fragments[position] = fragment;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }
}
