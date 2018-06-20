package com.fairytale.fortunetarot.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.controller.CardMeaningActivity;
import com.fairytale.fortunetarot.controller.HistoryActivity;
import com.fairytale.fortunetarot.http.request.RequestCode;
import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.presenter.DailyPresenter;
import com.fairytale.fortunetarot.util.DateUtil;
import com.fairytale.fortunetarot.util.Lunar;
import com.fairytale.fortunetarot.util.RotateTransformation;
import com.fairytale.fortunetarot.view.DailyView;
import com.fairytale.fortunetarot.widget.CustomFontTextView;

/**
 * Created by lizhen on 2018/1/22.
 */

public class DailyFragment extends BaseFragment implements DailyView{
    private ImageView img_daily;
    private CustomFontTextView tv_card;
    private CustomFontTextView tv_date;
    private CustomFontTextView tv_lunnar_date;
    private CustomFontTextView tv_card_meaning;
    private CustomFontTextView tv_content;
    private static DailyFragment dailyFragment;
    private DailyPresenter mDailyPresenter;
    private String[] mDailyCardInfos;
    private Context context;

    public static DailyFragment newInstance(){
        if (dailyFragment == null) {
            dailyFragment = new DailyFragment();
        }
        return dailyFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    BasePresenter setPresenter() {
        mDailyPresenter = new DailyPresenter(this);
        mDailyCardInfos = mDailyPresenter.disPlayDailyCard();
        mDailyPresenter.setMonth(DateUtil.getMonth());
        mDailyPresenter.setDay(DateUtil.getDay());
        return mDailyPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_daily,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        showDailyCard(mDailyCardInfos);
        initData();
    }

    private void initView(View view) {
        img_daily = initViewById(view, R.id.img_daily);
        tv_card = initViewById(view, R.id.tv_card);
        tv_date = initViewById(view, R.id.tv_date);
        tv_lunnar_date = initViewById(view, R.id.tv_lunnar_date);
        tv_card_meaning = initViewById(view, R.id.tv_card_meaning);
        tv_card_meaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CardMeaningActivity.class);
                Bundle bundle = new Bundle();
                String cardName = mDailyCardInfos[0].split("/")[1];
                String htmlPath = cardName.substring(0,cardName.indexOf(".")) + ".html";
                bundle.putString("path", "file:///android_asset/" + htmlPath);
                bundle.putString("cardName",mDailyCardInfos[1]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        initViewById(view, R.id.tv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, HistoryActivity.class));
            }
        });
        tv_content = initViewById(view, R.id.tv_content);
    }

    private void initData() {
        mDailyPresenter.startRequestByCode(RequestCode.REQUEST_HISTORY);
        tv_date.setVerticalText(DateUtil.GetNowDateWithPoint() + DateUtil.dateToWeek(DateUtil.GetNowDate()));

    }

    @Override
    public void showHistoryContent(String content) {
        tv_content.setText(content);
    }

    @Override
    public void showDailyCard(String[] cardsInfo) {
        boolean isReverse = cardsInfo[2].equals("Y");
        tv_card.setText(cardsInfo[1] + (isReverse ? "逆位" : "正位"));
        Glide.with(context).load("file:///android_asset/" + cardsInfo[0]).transform(new RotateTransformation(context, isReverse ? 180f : 0f)).crossFade().into(img_daily);
        tv_lunnar_date.setVerticalText(new Lunar(DateUtil.GetNowDate()).dayToLunar());
    }


}
