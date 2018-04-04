package com.fairytale.fortunetarot.presenter;

import com.fairytale.fortunetarot.entity.HistoryEntity;
import com.fairytale.fortunetarot.http.request.HistoryRequest;
import com.fairytale.fortunetarot.http.request.HttpRequestManager;
import com.fairytale.fortunetarot.http.request.RequestCode;
import com.fairytale.fortunetarot.http.response.Response;
import com.fairytale.fortunetarot.view.HistoryView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhen on 2018/2/2.
 */

public class HistoryPresenter extends BasePresenter {
    private HistoryView mHistoryView;
    private String day;
    private String month;
    private HistoryRequest mHistoryRequest;

    public HistoryPresenter(HistoryView historyView) {
        super(historyView);
        this.mHistoryView = historyView;
        mHistoryRequest = new HistoryRequest();
        tags = new int[]{RequestCode.REQUEST_HISTORY};
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

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth (String month) {
        this.month = month;
    }

    @Override
    public void finishRequest() {
        super.finishRequest();
        cancelByTag(RequestCode.REQUEST_HISTORY);
    }

    @Override
    void success(Response data) {
        super.success(data);
        if (data.getInfos() != null) {
            ArrayList<HistoryEntity> list = (ArrayList<HistoryEntity>) data.getInfos();
            mHistoryView.showHistorys(list);
        }
    }
}
