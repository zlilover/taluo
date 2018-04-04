package com.fairytale.fortunetarot.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.adapter.HistoryAdapter;
import com.fairytale.fortunetarot.adapter.ItemDivider;
import com.fairytale.fortunetarot.entity.HistoryEntity;
import com.fairytale.fortunetarot.http.request.RequestCode;
import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.presenter.HistoryPresenter;
import com.fairytale.fortunetarot.util.AndroidUtil;
import com.fairytale.fortunetarot.util.DateUtil;
import com.fairytale.fortunetarot.view.HistoryView;
import com.fairytale.fortunetarot.widget.PopupTimePicker;

import java.util.ArrayList;

/**
 * Created by lizhen on 2018/2/1.
 */

public class HistoryActivity extends BaseActivity implements HistoryView{
    private RecyclerView recyclerView;
    private HistoryPresenter mHistoryPresenter;
    private String day = DateUtil.getDay();
    private String month = DateUtil.getMonth();
    private HistoryAdapter historyAdapter;
    private PopupTimePicker timePicker;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initSelf() {
        recyclerView = initViewById(R.id.recycle_view);
        needTop(true);
        needBack(true);
        setImg_base_right(R.mipmap.lssdjt_date_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timePicker == null) {
                    timePicker = new PopupTimePicker(HistoryActivity.this,4);
                    timePicker.setCallBack(new PopupTimePicker.ClickCallBack() {
                        @Override
                        public void confirm(String time) {
                            if (time != null) {
                                day = timePicker.getDayWithNo0();
                                month = timePicker.getMonthWithNo0();
                                initData();
                                setTv_title(timePicker.getMonth() + "." + timePicker.getDay());
                            }
                        }
                    });
                }
                timePicker.show(AndroidUtil.getScreenParams(HistoryActivity.this)[0],600, Gravity.BOTTOM);
            }
        });
        setTv_titleWithTypeface(DateUtil.getMonth() + "." + DateUtil.getDay(),"font_apple_chancery.ttf");
        historyAdapter = new HistoryAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new ItemDivider().setDividerWith(AndroidUtil.dip2px(this,16.0f)));
        recyclerView.setAdapter(historyAdapter);
    }

    @Override
    protected void initData() {
        mHistoryPresenter.setDay(day);
        mHistoryPresenter.setMonth(month);
        mHistoryPresenter.startRequestByCode(RequestCode.REQUEST_HISTORY);
    }

    @Override
    protected BasePresenter initPresenter() {
        mHistoryPresenter = new HistoryPresenter(this);
        return mHistoryPresenter;
    }

    @Override
    public void showHistorys(ArrayList<HistoryEntity> historyEntities) {
        historyAdapter.setData(historyEntities);
    }
}
