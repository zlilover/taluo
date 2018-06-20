package com.fairytale.fortunetarot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.adapter.CardArrayAdapter;
import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.util.CardUtil;

/**
 * Created by lizhen on 2018/1/22.
 */

public class CardArrayFragment extends BaseFragment {

    private static CardArrayFragment cardArrayFragment;
    private RecyclerView recyclerView;
    private CardArrayAdapter cardArrayAdapter;

    public static CardArrayFragment newInstance(){
        if (cardArrayFragment == null) {
            cardArrayFragment = new CardArrayFragment();
        }
        return cardArrayFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_array,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        cardArrayAdapter = new CardArrayAdapter(getContext(), CardUtil.getDivinationItemEntityFromAssets(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(cardArrayAdapter);
    }

    @Override
    BasePresenter setPresenter() {
        return null;
    }
}
