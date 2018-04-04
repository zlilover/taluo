package com.fairytale.fortunetarot.view;

import com.fairytale.fortunetarot.entity.HistoryEntity;

import java.util.ArrayList;

/**
 * Created by lizhen on 2018/2/2.
 */

public interface HistoryView extends BaseView {

    void showHistorys(ArrayList<HistoryEntity> historyEntities);

}
