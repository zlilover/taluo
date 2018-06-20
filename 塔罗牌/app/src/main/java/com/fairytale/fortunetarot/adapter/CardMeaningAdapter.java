package com.fairytale.fortunetarot.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.controller.CardMeaningActivity;
import com.fairytale.fortunetarot.entity.ArcanaCards;
import com.fairytale.fortunetarot.util.CardUtil;
import com.fairytale.fortunetarot.util.MyResource;
import com.fairytale.fortunetarot.util.Util;
import com.fairytale.fortunetarot.widget.CustomFontTextView;

import java.util.ArrayList;


/**
 * Created by lizhen on 2018/4/17.
 */

public class CardMeaningAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ArcanaCards> cardsInfo;
    private Context context;

    public CardMeaningAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setCardsInfo(ArrayList<ArcanaCards> cardsInfo) {
        this.cardsInfo = cardsInfo;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return cardsInfo == null ? 0 : cardsInfo.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return cardsInfo.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_card_meaning_item,null);
            viewHolder.imgView = (ImageView) convertView.findViewById(R.id.img_info);
            viewHolder.tv_name = (CustomFontTextView) convertView.findViewById(R.id.tv_card_name);
            viewHolder.text_constellation = (CustomFontTextView) convertView.findViewById(R.id.text_constellation);
            viewHolder.tv_name_eng = (CustomFontTextView) convertView.findViewById(R.id.tv_card_name_eng);
            viewHolder.text_number = (CustomFontTextView) convertView.findViewById(R.id.text_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ArcanaCards cardsInfo = (ArcanaCards) getItem(position);
        int id = MyResource.getIdByName(context,"mipmap",cardsInfo.getName_for_icon().toLowerCase() + "_icon");
        viewHolder.imgView.setImageResource(id);
        viewHolder.tv_name.setText(cardsInfo.getName());
        viewHolder.text_constellation.setText(cardsInfo.getConstellation().equals("null") ? "" : cardsInfo.getConstellation());
        viewHolder.tv_name_eng.setText(cardsInfo.getName_eng());
        viewHolder.text_number.setText(cardsInfo.getRoma_num());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CardMeaningActivity.class);
                Bundle bundle = new Bundle();
                String cardName = cardsInfo.getName_for_icon();
                String htmlPath = cardName + ".html";
                bundle.putString("path", "file:///android_asset/" + htmlPath);
                bundle.putString("cardName",cardsInfo.getName());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView imgView;
        CustomFontTextView tv_name;
        CustomFontTextView text_constellation;
        CustomFontTextView tv_name_eng;
        CustomFontTextView text_number;
    }
}
