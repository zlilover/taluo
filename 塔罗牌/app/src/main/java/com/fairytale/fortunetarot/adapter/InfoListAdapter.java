package com.fairytale.fortunetarot.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.controller.WebDetailActivity;
import com.fairytale.fortunetarot.entity.HistoryEntity;
import com.fairytale.fortunetarot.entity.InfoEntity;
import com.fairytale.fortunetarot.widget.CustomFontTextView;
import com.fairytale.fortunetarot.widget.CustomImageView;

import java.util.ArrayList;

/**
 * Created by lizhen on 2018/2/2.
 */

public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.InfoListViewHolder>{
    private ArrayList<InfoEntity> mInfoEntities = new ArrayList<>();
    private Context mContext;

    public InfoListAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<InfoEntity> mInfoEntities,boolean isLoadMore) {
        if (!isLoadMore) {
            this.mInfoEntities.clear();
        }
        this.mInfoEntities.addAll(mInfoEntities);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mInfoEntities == null ? 0 : mInfoEntities.size();
    }

    @Override
    public InfoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_info_list_item, parent, false);
        return new InfoListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InfoListViewHolder holder, final int position) {
        holder.tv_title.setText(mInfoEntities.get(position).getTitle());
        holder.tv_content.setText(mInfoEntities.get(position).getContent_tip());
        Glide.with(mContext).load(mInfoEntities.get(position).getTitle_pic()).crossFade().into(holder.img_info);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("contentId", mInfoEntities.get(position).getContentid());
                bundle.putString("contentType", 1 + "");
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    class InfoListViewHolder extends RecyclerView.ViewHolder {
        View view;
        CustomFontTextView tv_content;
        CustomFontTextView tv_title;
        ImageView img_info;

        InfoListViewHolder(View view) {
            super(view);
            this.view = view;
            tv_content = (CustomFontTextView) view.findViewById(R.id.tv_content);
            tv_title = (CustomFontTextView) view.findViewById(R.id.tv_title);
            img_info = (ImageView) view.findViewById(R.id.img_info);
        }
    }


}
