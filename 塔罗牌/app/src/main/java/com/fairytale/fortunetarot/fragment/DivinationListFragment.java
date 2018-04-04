package com.fairytale.fortunetarot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.adapter.DivinationGridViewAdapter;
import com.fairytale.fortunetarot.adapter.InfoPagerAdapter;
import com.fairytale.fortunetarot.entity.BaseConfigEntity;
import com.fairytale.fortunetarot.entity.DivinationItemEntity;
import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.util.JsonUtils;
import com.fairytale.fortunetarot.util.SPUtil;
import com.fairytale.fortunetarot.util.Util;
import com.fairytale.fortunetarot.widget.ViewPagerIndicator;

import java.util.ArrayList;

/**
 * Created by lizhen on 2018/1/22.
 */

public class DivinationListFragment extends BaseFragment{
    private int type;
    private GridView gridView;
    private View rootView;
    private ArrayList<DivinationItemEntity> divinationItemEntities;
    private DivinationGridViewAdapter divinationGridViewAdapter;

    @Override
    public void setArguments(Bundle args) {
        type = args.getInt("type") + 1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_divination_list,container,false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configContents();
        initView(view);
    }

    private void initView(View view) {
        gridView = initViewById(view,R.id.gridView);
        divinationGridViewAdapter = new DivinationGridViewAdapter(getActivity(),divinationItemEntities,type);
        gridView.setAdapter(divinationGridViewAdapter);
    }

    private void configContents() {
        if (divinationItemEntities == null) {
            divinationItemEntities = new ArrayList<>();
            String text = Util.getStringfromAssets(getContext(), "cardarrayinfo/" +  type + ".txt");
            String[] contents = text.split("@");
            String baseConfig = SPUtil.get(getActivity(),"BaseConfig","").toString();
            if (!TextUtils.isEmpty(baseConfig)) {
                BaseConfigEntity baseConfigEntity = JsonUtils.jsonStringToEntity(baseConfig,BaseConfigEntity.class);
                if (baseConfigEntity == null) {
                    baseConfigEntity = new BaseConfigEntity();
                }
                for (int i = 0; i < contents.length; i ++) {
                    DivinationItemEntity entity = new DivinationItemEntity();
                    String[] info = contents[i].split("#");
                    entity.setTitle(info[0]);
                    String id = info[1].split("\\.")[0];
                    entity.setInfoId(id);
                    if ("1".equals(baseConfigEntity.getIsusefont())) {
                        entity.setTitleIconPath("thetitles/the_titles_" + id + ".png");
                    } else {
                        entity.setTitleIconPath("thetitles/the_titles_kaiti" + id + ".png");
                    }
                    String name;
                    switch (type) {
                        case 1:
                            name = "aiqing";
                            break;
                        case 2:
                            name = "caiyun";
                            break;
                        case 3:
                            name = "jiankang";
                            break;
                        case 4:
                            name = "shiye";
                            break;
                        case 5:
                            name = "xuexi";
                            break;
                        case 6:
                            name = "yuncheng";
                            break;
                        default:
                            name = "aiqing";
                    }
                    if ("a".equals(baseConfigEntity.getIndexres())) {
                        entity.setContentIconPath("icon_with_nopermision/leibie_" + name + ".png");
                    } else {
                        entity.setContentIconPath("icon_with_permision/leibie_" + name + ".png");
                    }
                    divinationItemEntities.add(entity);
                }
            }

        }
    }

    @Override
    BasePresenter setPresenter() {
        return null;
    }
}
