package com.fairytale.fortunetarot.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.adapter.LargerCountFragmentAdapter;
import com.fairytale.fortunetarot.entity.DivinationItemEntity;
import com.fairytale.fortunetarot.fragment.DivinationDetailFragment;
import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.util.DialogFactory;
import com.fairytale.fortunetarot.util.SPUtil;
import com.fairytale.fortunetarot.util.Util;
import com.fairytale.fortunetarot.widget.CustomFontTextView;

import java.util.ArrayList;

/**
 * Created by lizhen on 2018/4/3.
 */

public class DivinationItemActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    private ArrayList<DivinationItemEntity> divinationItemEntities;
    private ViewPager viewPager;
    private RelativeLayout rl_unlock;
    private LargerCountFragmentAdapter fragmentAdapter;
    private CustomFontTextView customFontTextView_action;
    private int type;
    private int currentItem;
    private String currentId;
    private boolean isGoodOpinionToUnlock;
    private boolean isGoodOpinion;
    private Dialog opinionDialog;

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        divinationItemEntities = getIntent().getParcelableArrayListExtra("DivinationItemEntityList");
        int id = getIntent().getIntExtra("id", 0);
        isGoodOpinionToUnlock = SPUtil.get(this,"isGoodOpinionToUnlock","").toString().equals("1");
        isGoodOpinion = Boolean.parseBoolean(SPUtil.get(DivinationItemActivity.this,type+ "isGoodOpinion","false").toString());
        if (divinationItemEntities != null && divinationItemEntities.size() > 0) {
            setTv_title(divinationItemEntities.get(id).getTitle());
            Class[] fragments = new Class[divinationItemEntities.size()];
            for (int i = 0; i < divinationItemEntities.size();i ++) {
                fragments[i] = DivinationDetailFragment.class;
            }
            fragmentAdapter = new LargerCountFragmentAdapter(getSupportFragmentManager(),fragments);
            type = getIntent().getIntExtra("type",0);
            fragmentAdapter.setData(type,divinationItemEntities);
            viewPager.setAdapter(fragmentAdapter);
            viewPager.setCurrentItem(id);
            if (id == currentItem) { // 当前item未发生变化时viewpager setCurrentItem无效
                onPageSelected(id);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentItem = position;
        setTv_title(divinationItemEntities.get(position).getTitle());
        String id = divinationItemEntities.get(position).getInfoId();
        currentId = id;
        Object isUnlock = SPUtil.get(DivinationItemActivity.this,"id" + id,"");
        String text = null;
        int resId = 0;
        if (isGoodOpinionToUnlock && isGoodOpinion) {   //提供好评解锁并且给了好评
            text = "开始占卜";
            resId = R.mipmap.icon_divination_normal;
        } else if (!TextUtils.isEmpty(isUnlock.toString())) {   // 提供好评的情况下没给好评，或者为提供好评解锁的条件下先差寻是否免费,先查询是否保存过这个排阵的免费信息，未保存从txt中国呢读取
            if (isUnlock.toString().equals("1")) {
                text = "解锁牌阵";
                resId = R.mipmap.icon_card_array_normal;
            } else {
                text = "开始占卜";
                resId = R.mipmap.icon_divination_normal;
            }
        } else {
            String content = Util.getStringfromAssets(DivinationItemActivity.this,"cardarrayinfo/" + type + "/" + id + ".txt");
            if (!TextUtils.isEmpty(content)) {
                String unlock_temp = content.split("@")[0].split("#")[4];
                boolean isContentUnlock = "1".equals(unlock_temp);
                SPUtil.put(DivinationItemActivity.this,"id" + id,unlock_temp);
                text = isContentUnlock ? "解锁牌阵" : "开始占卜";
                resId = R.mipmap.icon_card_array_normal;
            }
        }
        if (resId != 0 && !TextUtils.isEmpty(text)) {
            customFontTextView_action.setText(text);
            Drawable drawable = getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            customFontTextView_action.setCompoundDrawables(drawable,null,null,null);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void initSelf() {
        needTop(true);
        needBack(true);
        rl_unlock = initViewById(R.id.rl_unlock);
        viewPager = initViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);
        rl_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customFontTextView_action.getText().toString().equals("解锁牌阵")) {
                    if (isGoodOpinionToUnlock) {
                        if (isGoodOpinion) {

                        }
                        //TODO
                        opinionDialog = DialogFactory.getInstance().showUnlockDialog(DivinationItemActivity.this, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String packageName = getPackageName();
                                Uri uri = Uri.parse("market://details?id=" + packageName);
                                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                                try {
                                    startActivity(goToMarket);
                                } catch (ActivityNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        final Dialog dialog = DialogFactory.getInstance().showBuyDialog(DivinationItemActivity.this, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //  购买
                            }
                        },new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                //回复购买
                            }
                        });
                    }
                } else {

                }
            }
        });
        customFontTextView_action = initViewById(R.id.customFontTextView_action);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_divination_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (opinionDialog != null && opinionDialog.isShowing()) {
            SPUtil.put(DivinationItemActivity.this,type+ "isGoodOpinion","true");
            SPUtil.put(DivinationItemActivity.this,"id" + currentId,"0");
            customFontTextView_action.setText("开始占卜");
            opinionDialog.dismiss();
        }
    }
}
