package com.fairytale.fortunetarot.controller;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.entity.DivinationItemEntity;
import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.util.DialogFactory;
import com.fairytale.fortunetarot.util.SPUtil;
import com.fairytale.fortunetarot.util.Util;
import com.fairytale.fortunetarot.widget.CustomFontTextView;

/**
 * Created by lizhen on 2018/5/10.
 */

public class CardArrayItemActivity extends BaseActivity {
    private ImageView img_card;
    private CustomFontTextView customFontTextView_cardArrayContent;
    private CustomFontTextView customFontTextView_cardContent;
    private CustomFontTextView customFontTextView_action;
    private boolean isGoodOpinionToUnlock;
    private boolean isGoodOpinion;
    private Dialog opinionDialog;
    private String type;
    private String id;

    @Override
    protected void initSelf() {
        img_card = initViewById(R.id.img_card);
        customFontTextView_cardContent = initViewById(R.id.customFontTextView_cardContent);
        customFontTextView_cardArrayContent = initViewById(R.id.customFontTextView_cardArrayContent);
        customFontTextView_action = initViewById(R.id.customFontTextView_action);
        initViewById(R.id.rl_unlock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customFontTextView_action.getText().toString().equals("解锁牌阵")) {
                    if (isGoodOpinionToUnlock) {
                        if (isGoodOpinion) {

                        }
                        //TODO
                        opinionDialog = DialogFactory.getInstance().showUnlockDialog(CardArrayItemActivity.this, new View.OnClickListener() {
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
                        final Dialog dialog = DialogFactory.getInstance().showBuyDialog(CardArrayItemActivity.this, new View.OnClickListener() {
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
    }

    @Override
    protected void initData() {
        DivinationItemEntity entity = getIntent().getParcelableExtra("info");
        type = entity.getGroupId();
        isGoodOpinionToUnlock = SPUtil.get(this,"isGoodOpinionToUnlock","").toString().equals("1");
        isGoodOpinion = Boolean.parseBoolean(SPUtil.get(CardArrayItemActivity.this,type+ "isGoodOpinion","false").toString());
        setTv_title(entity.getTitle());
        id = entity.getInfoId().split("\\.")[0];
        Object isUnlock = SPUtil.get(this,"id" + id,"");
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
            String content = Util.getStringfromAssets(this,"cardarrayinfo/" + type + "/" + id + ".txt");
            if (!TextUtils.isEmpty(content)) {
                String unlock_temp = content.split("@")[0].split("#")[4];
                boolean isContentUnlock = "1".equals(unlock_temp);
                SPUtil.put(this,"id" + id,unlock_temp);
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
        Glide.with(this).load("file:///android_asset/cardarrayinfo/cardimgs_with_num/" + id + ".png").into(img_card);

        String path = "cardarrayinfo/" + type + "/" + id + ".txt";
        String content = Util.getStringfromAssets(this,path);
        if (!TextUtils.isEmpty(content)) {
            String details[] = content.split("@");
            String[] detailInfo = details[0].split("#");
            customFontTextView_cardArrayContent.setText(detailInfo[2]);
            customFontTextView_cardContent.setText(details[1] + "\n" + details[2] + "\n" + details[3]);
            SPUtil.put(this,"id" + id,detailInfo[4]);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_array;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (opinionDialog != null && opinionDialog.isShowing()) {
            SPUtil.put(this,type+ "isGoodOpinion","true");
            SPUtil.put(this,"id" + id,"0");
            customFontTextView_action.setText("开始占卜");
            opinionDialog.dismiss();
        }
    }
}
