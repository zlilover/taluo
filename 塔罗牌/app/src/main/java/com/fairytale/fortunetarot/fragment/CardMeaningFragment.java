package com.fairytale.fortunetarot.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.adapter.CardMeaningListAdapter;
import com.fairytale.fortunetarot.entity.ArcanaCardGroup;
import com.fairytale.fortunetarot.entity.BaseConfigEntity;
import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.util.CardUtil;
import com.fairytale.fortunetarot.util.JsonUtils;
import com.fairytale.fortunetarot.util.SPUtil;
import com.fairytale.fortunetarot.widget.CustomFontTextView;
import com.fairytale.fortunetarot.widget.CustomListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by lizhen on 2018/1/22.
 */

public class CardMeaningFragment extends BaseFragment {
    private CustomFontTextView tv_ads;
    private CustomListView list_card_meaning;
    private static CardMeaningListAdapter cardMeaningListAdapter;
    private Context context;
    private static CardMeaningFragment cardMeaningFragment;


    public static CardMeaningFragment newInstance() {
        if (cardMeaningFragment == null) {
            cardMeaningFragment = new CardMeaningFragment();
        }
        return cardMeaningFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_meaning_new, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ArcanaCardGroup> list = CardUtil.getCardsInfoByType(context, "allnames.txt");
                Message message = new Message();
                message.obj = list;
                message.what = 0;
                handler.sendMessage(message);
            }
        }).start();
        list_card_meaning = initViewById(view, R.id.list_card_meaning);
        tv_ads = initViewById(view, R.id.tv_ads);

        cardMeaningListAdapter = new CardMeaningListAdapter(context);
        list_card_meaning.setAdapter(cardMeaningListAdapter);

        String baseConfig = SPUtil.get(context, "BaseConfig", "").toString();
        BaseConfigEntity baseConfigEntity = JsonUtils.jsonStringToEntity(baseConfig, BaseConfigEntity.class);
        if (baseConfigEntity != null) {
            String text = String.format(context.getResources().getString(R.string.ads), baseConfigEntity.getQqqun());
            tv_ads.setText(text);
        }
    }

    private MyHandler handler = new MyHandler(this);

    static class MyHandler extends Handler{
        private WeakReference mFragment;
        MyHandler(BaseFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mFragment.get() != null && !cardMeaningFragment.isDetached()) {
                if (msg.what == 0) {
                    ArrayList<ArcanaCardGroup> list = (ArrayList<ArcanaCardGroup>) msg.obj;
                    cardMeaningListAdapter.setData(list);
                }
            }
        }
    }

    @Override
    BasePresenter setPresenter() {
        return null;
    }
}
