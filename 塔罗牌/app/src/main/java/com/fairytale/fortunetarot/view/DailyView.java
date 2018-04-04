package com.fairytale.fortunetarot.view;

/**
 * Created by lizhen on 2018/1/11.
 */

public interface DailyView extends BaseView{

    void showHistoryContent(String content);

    void showDailyCard(String[] cardsInfo);
}
