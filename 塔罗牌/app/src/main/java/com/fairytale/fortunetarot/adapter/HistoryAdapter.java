package com.fairytale.fortunetarot.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.controller.WebDetailActivity;
import com.fairytale.fortunetarot.entity.HistoryEntity;
import com.fairytale.fortunetarot.widget.CustomFontTextView;

import java.util.ArrayList;

/**
 * Created by lizhen on 2018/2/2.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{
    private ArrayList<HistoryEntity> mHistoryEntities;
    private Context mContext;

    public HistoryAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<HistoryEntity> historyEntities) {
        mHistoryEntities = historyEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mHistoryEntities == null ? 0 : mHistoryEntities.size();
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_history_item, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, final int position) {
        holder.tv_content.setText(mHistoryEntities.get(position).getTitle());
        Glide.with(mContext).load(mHistoryEntities.get(position).getTitle_pic()).crossFade().into(holder.img_history);
        if (position == getSectionForYear(mHistoryEntities.get(position).getYear())) {
            holder.rl_top.setVisibility(View.VISIBLE);
            holder.tv_year.setText(mHistoryEntities.get(position).getYear());
        } else {
            holder.rl_top.setVisibility(View.GONE);
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("contentId", mHistoryEntities.get(position).getId());
                bundle.putString("contentType", 2 + "");
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        View view;
        RelativeLayout rl_top;
        CustomFontTextView tv_year;
        CustomFontTextView tv_content;
        ImageView img_history;

        public HistoryViewHolder(View view) {
            super(view);
            this.view = view;
            rl_top = (RelativeLayout) view.findViewById(R.id.rl_top);
            tv_year = (CustomFontTextView) view.findViewById(R.id.tv_year);
            tv_content = (CustomFontTextView) view.findViewById(R.id.tv_content);
            img_history = (ImageView) view.findViewById(R.id.img_history);
        }
    }


    private int getSectionForYear(String year) {
        for (int i = 0;i < mHistoryEntities.size(); i ++) {
            if (year.equals(mHistoryEntities.get(i).getYear())) {
                return i;
            }
        }
        return -1;
    }

}
