package com.fairytale.fortunetarot.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fairytale.fortunetarot.entity.DivinationItemEntity;
import com.fairytale.fortunetarot.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by lizhen on 2018/4/3.
 */

public class LargerCountFragmentAdapter extends FragmentStatePagerAdapter {
    private Class[] fragmentList;
    private BaseFragment[] fragments;
    private int type;
    private ArrayList<DivinationItemEntity> divinationItemEntities;

    public LargerCountFragmentAdapter(FragmentManager fragmentManager,Class[] fragmentList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
        fragments = new BaseFragment[fragmentList.length];
    }

    public void setData(int type, ArrayList<DivinationItemEntity> divinationItemEntities) {
        this.type = type;
        this.divinationItemEntities = divinationItemEntities;
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
                bundle.putInt("type",type);
                bundle.putInt("current",position + 1);
                bundle.putInt("total",divinationItemEntities.size());
                bundle.putString("id", divinationItemEntities.get(position).getInfoId());
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
