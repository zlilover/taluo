package com.fairytale.fortunetarot.fragment;

import com.fairytale.fortunetarot.presenter.BasePresenter;

/**
 * Created by lizhen on 2018/1/22.
 */

public class CardMeaningFragment extends BaseFragment {

    private static CardMeaningFragment cardMeaningFragment;


    public static CardMeaningFragment newInstance(){
        if (cardMeaningFragment == null) {
            cardMeaningFragment = new CardMeaningFragment();
        }
        return cardMeaningFragment;
    }

    @Override
    BasePresenter setPresenter() {
        return null;
    }
}
