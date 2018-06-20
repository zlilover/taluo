package com.fairytale.fortunetarot.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.controller.CardArrayItemActivity;
import com.fairytale.fortunetarot.entity.DivinationItemEntity;
import com.fairytale.fortunetarot.util.Util;
import com.fairytale.fortunetarot.widget.CustomFontTextView;
import com.fairytale.fortunetarot.widget.CustomImageView;

import java.util.ArrayList;

/**
 * Created by lizhen on 2018/5/10.
 */

public class CardArrayAdapter extends RecyclerView.Adapter<CardArrayAdapter.CardArrayViewHolder> {
    private ArrayList<DivinationItemEntity> entities;
    private Context context;

    public CardArrayAdapter(Context context,ArrayList<DivinationItemEntity> entities) {
        this.context = context;
        this.entities = entities;
    }

    @Override
    public int getItemCount() {
        return entities == null ? 0 : entities.size();
    }

    @Override
    public CardArrayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_card_array_item,parent,false);
        return new CardArrayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardArrayViewHolder holder, final int position) {
        holder.customFontTextView_cardName.setText(entities.get(position).getTitle());
        String path = "cardarrayinfo/" + entities.get(position).getGroupId() + "/" +entities.get(position).getInfoId();
        int count = Util.getStringfromAssets(context,path).split("@").length - 1;
        holder.customFontTextView_cardNum.setText(entities.get(position).getGroupName() + "&" + Util.arabToChinese(count) + "张牌");
        String imgId = entities.get(position).getInfoId().split("\\.")[0];
        Glide.with(context).load("file:///android_asset/cardarrayinfo/cardimgs/" + imgId + ".png").into(holder.customImageView_card);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CardArrayItemActivity.class);
                intent.putExtra("info",entities.get(position));
                context.startActivity(intent);
            }
        });
    }

    class CardArrayViewHolder extends RecyclerView.ViewHolder{
        CustomFontTextView customFontTextView_cardNum;
        CustomFontTextView customFontTextView_cardName;
        CustomImageView customImageView_card;
        View view;

        public CardArrayViewHolder(View view) {
            super(view);
            this.view = view;
            customFontTextView_cardName = (CustomFontTextView) view.findViewById(R.id.customFontTextView_cardName);
            customFontTextView_cardNum = (CustomFontTextView) view.findViewById(R.id.customFontTextView_cardNum);
            customImageView_card = (CustomImageView) view.findViewById(R.id.customImageView_card);
        }
    }


}
