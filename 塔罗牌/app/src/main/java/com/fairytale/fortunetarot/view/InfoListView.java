package com.fairytale.fortunetarot.view;

import com.fairytale.fortunetarot.entity.InfoEntity;

import java.util.ArrayList;

/**
 * Created by lizhen on 2018/2/8.
 */

public interface InfoListView extends BaseView{

    void showRefreshData(ArrayList<InfoEntity> infoEntities);

    void showLoadMoreData(ArrayList<InfoEntity> infoEntities);

    void stopRefresh();

    void stopLoadMore();

    void showNoMore();

    void showNoContentView();

    void hideNoContentView();

    void showLoadingView();

    void showErrorView();

}
