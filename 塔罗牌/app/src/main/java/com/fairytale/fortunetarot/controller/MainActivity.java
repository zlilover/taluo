package com.fairytale.fortunetarot.controller;


import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.comm.Config;
import com.fairytale.fortunetarot.entity.BaseConfigEntity;
import com.fairytale.fortunetarot.fragment.BaseFragment;
import com.fairytale.fortunetarot.fragment.CardArrayFragment;
import com.fairytale.fortunetarot.fragment.CardMeaningFragment;
import com.fairytale.fortunetarot.fragment.DailyFragment;
import com.fairytale.fortunetarot.fragment.DivinationFragment;
import com.fairytale.fortunetarot.fragment.InfoFragment;
import com.fairytale.fortunetarot.http.request.BaseConfigRequest;
import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.util.ActivityManager;
import com.fairytale.fortunetarot.util.JsonUtils;
import com.fairytale.fortunetarot.util.SPUtil;
import com.fairytale.fortunetarot.util.Util;

import rx.Observer;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{
    private BottomNavigationBar bottom_navigation_bar;
    private FragmentManager mFragmentManager;
    private DailyFragment mDailyFragment;
    private InfoFragment mInfoFragment;
    private DivinationFragment mDivinationFragment;
    private CardMeaningFragment mCardMeaningFragment;
    private CardArrayFragment mCardArrayFragment;
    private BaseFragment currentFragment;
    private int mJumpToWitchItem;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initSelf() {
        mFragmentManager = getSupportFragmentManager();
        Typeface typeface;
        try {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/font_italics.ttf");
        } catch (RuntimeException e) {
            typeface = null;
        }
        needTop(false);
        needBack(false);
        bottom_navigation_bar = initViewById(R.id.bottom_navigation_bar);
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_FIXED);
        bottom_navigation_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottom_navigation_bar.addItem(new BottomNavigationItem(R.mipmap.icon_daily_selected, "每日",typeface).setActiveColorResource(R.color.navigation_text_red).setInActiveColorResource(R.color.base_clor_dark).setInactiveIconResource(R.mipmap.icon_daily_normal))
                .addItem(new BottomNavigationItem(R.mipmap.icon_info_selected, "资讯",typeface).setActiveColorResource(R.color.navigation_text_red).setInActiveColorResource(R.color.base_clor_dark).setInactiveIconResource(R.mipmap.icon_info_normal))
                .addItem(new BottomNavigationItem(R.mipmap.icon_divination_selected, "占卜",typeface).setActiveColorResource(R.color.navigation_text_red).setInActiveColorResource(R.color.base_clor_dark).setInactiveIconResource(R.mipmap.icon_divination_normal))
                .addItem(new BottomNavigationItem(R.mipmap.icon_card_meaning_selected, "牌义",typeface).setActiveColorResource(R.color.navigation_text_red).setInActiveColorResource(R.color.base_clor_dark).setInactiveIconResource(R.mipmap.icon_card_meaning_normal))
                .addItem(new BottomNavigationItem(R.mipmap.icon_card_array_selected, "牌阵",typeface).setActiveColorResource(R.color.navigation_text_red).setInActiveColorResource(R.color.base_clor_dark).setInactiveIconResource(R.mipmap.icon_card_array_normal))
                .setFirstSelectedPosition(2)
                .initialise();
        bottom_navigation_bar.setTabSelectedListener(this);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        new BaseConfigRequest().getBaseConfig().subscribe(new Observer<BaseConfigEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mJumpToWitchItem = getIntent().getIntExtra(Config.NOTIFICATION_CLICKED_TO_MAIN,2); // 默认跳转至占卜tab
                onTabSelected(mJumpToWitchItem);
            }

            @Override
            public void onNext(BaseConfigEntity baseConfigEntity) {
                String baseConfig = JsonUtils.entityToJsonString(baseConfigEntity);
                SPUtil.put(MainActivity.this,"BaseConfig",baseConfig);
                SPUtil.put(MainActivity.this,"isGoodOpinionToUnlock",baseConfigEntity.getIshaoping());
                mJumpToWitchItem = getIntent().getIntExtra(Config.NOTIFICATION_CLICKED_TO_MAIN,2); // 默认跳转至占卜tab
                onTabSelected(mJumpToWitchItem);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void tabChanged(BaseFragment baseFragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if(currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        if (!baseFragment.isAdded()) {
            fragmentTransaction.add(R.id.ll_container, baseFragment);
        } else {
            fragmentTransaction.show(baseFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
        currentFragment = baseFragment;
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                mDailyFragment = DailyFragment.newInstance();
                tabChanged(mDailyFragment);
                break;
            case 1:
                mInfoFragment = InfoFragment.newInstance();
                tabChanged(mInfoFragment);
                break;
            case 2:
                mDivinationFragment = DivinationFragment.newInstance();
                tabChanged(mDivinationFragment);
                break;
            case 3:
                mCardMeaningFragment = CardMeaningFragment.newInstance();
                tabChanged(mCardMeaningFragment);
                break;
            case 4:
                mCardArrayFragment = CardArrayFragment.newInstance();
                tabChanged(mCardArrayFragment);
                break;
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onBackPressed() {
        if (!Util.delayBtnClick()) {
            Toast.makeText(MainActivity.this, "再次点击退出塔罗", Toast.LENGTH_SHORT).show();
            return;
        }
        ActivityManager.getAppManager().AppExit(true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        mJumpToWitchItem = intent.getIntExtra(Config.NOTIFICATION_CLICKED_TO_MAIN,-1);
        boolean mIsNeedToRefreshCurrent = false;
        if (mJumpToWitchItem != -1) {
            switch (mJumpToWitchItem) {
                case 1:
                    mIsNeedToRefreshCurrent = true;
                    break;
            }
            onTabSelected(mJumpToWitchItem);
            if (mIsNeedToRefreshCurrent) {
                currentFragment.refresh();
            }
        }
    }
}
