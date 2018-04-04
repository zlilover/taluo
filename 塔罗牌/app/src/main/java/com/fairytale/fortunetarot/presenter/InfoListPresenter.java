package com.fairytale.fortunetarot.presenter;

import com.fairytale.fortunetarot.entity.InfoEntity;
import com.fairytale.fortunetarot.http.request.InfoRequest;
import com.fairytale.fortunetarot.http.request.RequestCode;
import com.fairytale.fortunetarot.http.response.Response;
import com.fairytale.fortunetarot.view.InfoListView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhen on 2018/2/27.
 */

public class InfoListPresenter extends BasePresenter{
    private InfoListView mInfoListView;
    private InfoRequest mInfoRequest;
    private int type;   //  0表示全部，1表示运势，2表示测试，3表示牌阵，4表示教程，5表示知识
    private int page = 1;

    public void setType(int type) {
        this.type = type;
    }

    public void reset() {
        page = 1;
    }

    public InfoListPresenter(InfoListView mInfoView) {
        super(mInfoView);
        this.mInfoListView = mInfoView;
        tags = new int[]{RequestCode.REQUEST_INFO_LIST};
        mInfoRequest = new InfoRequest();
    }

    @Override
    public void startRequestByCode(int httpCode) {
        switch (httpCode) {
            case RequestCode.REQUEST_INFO_LIST:
                subscribe(tags[0],mInfoRequest.getInfoByType(type,page).subscribe(new ResponseFilter<List<InfoEntity>>(),errorAction));
                break;
        }
    }

    @Override
    public void finishRequest() {
        cancelByTag(RequestCode.REQUEST_INFO_LIST);
    }

    @Override
    void appError() {
        if (page == 1) {
            mInfoListView.showErrorView();
        }
    }

    @Override
    void success(Response data) {
        if (data.getInfos() != null) {
            ArrayList<InfoEntity> list = (ArrayList<InfoEntity>) data.getInfos();
            if (page++ == 1) {
                mInfoListView.showRefreshData(list);
            }
            else {
                mInfoListView.showLoadMoreData(list);
            }
            mInfoListView.hideNoContentView();
        } else {
            if (page == 1) {
                mInfoListView.stopRefresh();
                mInfoListView.showNoContentView();
            } else {
                mInfoListView.stopLoadMore();
                mInfoListView.showNoMore();
            }
        }
    }

}
