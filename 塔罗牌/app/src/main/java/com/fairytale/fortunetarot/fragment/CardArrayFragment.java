package com.fairytale.fortunetarot.fragment;

import com.fairytale.fortunetarot.presenter.BasePresenter;

/**
 * Created by lizhen on 2018/1/22.
 */

public class CardArrayFragment extends BaseFragment {

    private static CardArrayFragment cardArrayFragment;


    public static CardArrayFragment newInstance(){
        if (cardArrayFragment == null) {
            cardArrayFragment = new CardArrayFragment();
        }
        return cardArrayFragment;
    }

    @Override
    BasePresenter setPresenter() {
        return null;
    }
}
