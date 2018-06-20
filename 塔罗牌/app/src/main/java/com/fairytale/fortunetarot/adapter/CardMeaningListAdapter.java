package com.fairytale.fortunetarot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.entity.ArcanaCardGroup;
import com.fairytale.fortunetarot.widget.CustomFontTextView;
import com.fairytale.fortunetarot.widget.CustomGridView;

import java.util.ArrayList;
/**
 * Created by lizhen on 2018/4/18.
 */

public class CardMeaningListAdapter extends BaseAdapter {
    private ArrayList<ArcanaCardGroup> cardsGroup;
    private Context context;
    private LayoutInflater inflater;

    public CardMeaningListAdapter(Context context) {
        this.context = context;
        this.cardsGroup = cardsGroup;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<ArcanaCardGroup> cardsGroup) {
        this.cardsGroup = cardsGroup;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cardsGroup == null ? 0 : cardsGroup.size();
    }

    @Override
    public Object getItem(int position) {
        return cardsGroup.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_cardmeaning_list_item,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_type.setText(cardsGroup.get(position).getTypeName());
        holder.adapter.setCardsInfo(cardsGroup.get(position).getCards());
        return convertView;
    }

    class ViewHolder {
        CustomGridView gridView_arcana;
        CustomFontTextView tv_type;
        CardMeaningAdapter adapter;

        public ViewHolder(View view) {
            tv_type = (CustomFontTextView) view.findViewById(R.id.tv_type);
            gridView_arcana = (CustomGridView) view.findViewById(R.id.gridView_arcana);
            adapter = new CardMeaningAdapter(context);
            gridView_arcana.setAdapter(adapter);
        }
    }

}
