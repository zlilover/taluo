package com.fairytale.fortunetarot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.util.SPUtil;
import com.fairytale.fortunetarot.util.Util;
import com.fairytale.fortunetarot.widget.CustomFontTextView;
import com.fairytale.fortunetarot.widget.CustomImageView;

/**
 * Created by lizhen on 2018/4/3.
 */

public class DivinationDetailFragment extends BaseFragment {

    private View rootView;
    private int type;
    private CustomImageView customImageView_cardArray;
    private CustomFontTextView customFontTextView_currentPage;
    private CustomFontTextView customFontTextView_totalPage;
    private CustomFontTextView customFontTextView_cardArrayName;
    private CustomFontTextView customFontTextView_cardArrayContent;
    private CustomFontTextView customFontTextView_tipsContent;
    private int total;
    private int current;
    private String id;

    @Override
    public void setArguments(Bundle args) {
        type = args.getInt("type");
        total = args.getInt("total");
        current = args.getInt("current");
        id = args.getString("id");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_divination_item,container,false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        initView(rootView);
        initData();
        return rootView;
    }

    private void initView(View view) {
        customImageView_cardArray = initViewById(view,R.id.customImageView_cardArray);
        customFontTextView_currentPage = initViewById(view,R.id.customFontTextView_currentPage);
        customFontTextView_totalPage = initViewById(view,R.id.customFontTextView_totalPage);
        customFontTextView_cardArrayName = initViewById(view,R.id.customFontTextView_cardArrayName);
        customFontTextView_cardArrayContent = initViewById(view,R.id.customFontTextView_cardArrayContent);
        customFontTextView_tipsContent = initViewById(view,R.id.customFontTextView_tipsContent);
    }

    private void initData() {
        String path = "cardarrayinfo/" + type + "/" + id + ".txt";
        String content = Util.getStringfromAssets(getActivity(),path);
        if (!TextUtils.isEmpty(content)) {
            String details = content.split("@")[0];
            String[] detailInfo = details.split("#");
            Glide.with(getActivity()).load("file:///android_asset/cardarrayinfo/cardimgs/" + detailInfo[0] + ".png").into(customImageView_cardArray);
            customFontTextView_currentPage.setText(String.valueOf(current));
            customFontTextView_totalPage.setText(String.valueOf(total));
            customFontTextView_cardArrayName.setText(detailInfo[1]);
            customFontTextView_cardArrayContent.setText(detailInfo[2]);
            customFontTextView_tipsContent.setText(detailInfo[3]);
            SPUtil.put(getActivity(),"id" + id,detailInfo[4]);
        }
    }

    @Override
    BasePresenter setPresenter() {
        return null;
    }
}
