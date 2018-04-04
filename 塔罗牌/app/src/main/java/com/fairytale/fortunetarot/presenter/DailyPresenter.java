package com.fairytale.fortunetarot.presenter;

import com.fairytale.fortunetarot.entity.HistoryEntity;
import com.fairytale.fortunetarot.http.request.HistoryRequest;
import com.fairytale.fortunetarot.http.request.HttpRequestManager;
import com.fairytale.fortunetarot.http.request.RequestCode;
import com.fairytale.fortunetarot.http.response.Response;
import com.fairytale.fortunetarot.util.CardUtil;
import com.fairytale.fortunetarot.view.DailyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhen on 2018/1/11.
 */

public class DailyPresenter extends BasePresenter {
    private String day;
    private String month;
    public DailyView mDailyView;
    private HistoryRequest mHistoryRequest;

    public DailyPresenter(DailyView mDailyView) {
        super(mDailyView);
        this.mDailyView = mDailyView;
        mHistoryRequest = new HistoryRequest();
        tags = new int[]{RequestCode.REQUEST_INFO_LIST};
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth (String month) {
        this.month = month;
    }

    public String [] disPlayDailyCard() {
        return new CardUtil().getDailyCardInfo();
    }

    @Override
    public void startRequestByCode(int httpCode) {
        super.startRequestByCode(httpCode);
        switch (httpCode) {
            case RequestCode.REQUEST_HISTORY:
                subscribe(tags[0],mHistoryRequest.getTodayInHistory(month, day).subscribe(new ResponseFilter<List<HistoryEntity>>(),errorAction));
                break;
        }
    }

    @Override
    void success(Response data) {
        super.success(data);
        if (data.getInfos() != null) {
            ArrayList<HistoryEntity> list = (ArrayList<HistoryEntity>) data.getInfos();
            mDailyView.showHistoryContent(list.get(0).getTitle());
        } else {

        }
    }
}
