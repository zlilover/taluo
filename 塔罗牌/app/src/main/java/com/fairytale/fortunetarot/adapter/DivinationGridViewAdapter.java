package com.fairytale.fortunetarot.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.bumptech.glide.Glide;
import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.controller.DivinationItemActivity;
import com.fairytale.fortunetarot.entity.DivinationItemEntity;
import com.fairytale.fortunetarot.util.Logger;
import com.fairytale.fortunetarot.widget.CustomImageView;
import java.util.ArrayList;

/**
 * Created by lizhen on 16/6/2.
 */
public class DivinationGridViewAdapter extends BaseAdapter {
    private ArrayList<DivinationItemEntity> divinationItemEntities;
    private Context context;
    private LayoutInflater layoutInflater;
    private int type;
    private int imgId;

    public DivinationGridViewAdapter(Context context,ArrayList<DivinationItemEntity> divinationItemEntities,int type,boolean isPermission) {
        this.context = context;
        this.divinationItemEntities = divinationItemEntities;
        layoutInflater = LayoutInflater.from(context);
        this.type = type;
        configImg(type,isPermission);
    }

    private void configImg(int type,boolean isPermission) {
        switch (type) {
            case 1:
                if (isPermission) {
                    imgId = R.mipmap.leibie_aiqing;
                } else {
                    imgId = R.mipmap.leibie_aiqing_no_permision;
                }
                break;
            case 2:
                if (isPermission) {
                    imgId = R.mipmap.leibie_caiyun;
                } else {
                    imgId = R.mipmap.leibie_caiyun_no_permision;
                }
                break;
            case 3:
                if (isPermission) {
                    imgId = R.mipmap.leibie_jiankang;
                } else {
                    imgId = R.mipmap.leibie_jiankang_no_permision;
                }
                break;
            case 4:
                if (isPermission) {
                    imgId = R.mipmap.leibie_shiye;
                } else {
                    imgId = R.mipmap.leibie_shiye_no_permision;
                }
                break;
            case 5:
                if (isPermission) {
                    imgId = R.mipmap.leibie_xuexi;
                } else {
                    imgId = R.mipmap.leibie_xuexi_no_permision;
                }
                break;
            case 6:
                if (isPermission) {
                    imgId = R.mipmap.leibie_yuncheng;
                } else {
                    imgId = R.mipmap.leibie_yuncheng_no_permision;
                }
                break;
            default:
                if (isPermission) {
                    imgId = R.mipmap.leibie_aiqing;
                } else {
                    imgId = R.mipmap.leibie_aiqing_no_permision;
                }
        }
    }

    @Override
    public int getCount() {
        return divinationItemEntities == null ? 0:divinationItemEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return divinationItemEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Logger.e("tag","---------");
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.adapter_divination_list_item,null);
            holder.customImageView_content = (CustomImageView) convertView.findViewById(R.id.customImageView_content);
            holder.customImageView_title = (CustomImageView) convertView.findViewById(R.id.customImageView_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(imgId).into(holder.customImageView_content);
        Glide.with(context).load("file:///android_asset/" + divinationItemEntities.get(position).getTitleIconPath()).into(holder.customImageView_title);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DivinationItemActivity.class);
                intent.putExtra("id", position);
                intent.putExtra("type", type);
                intent.putParcelableArrayListExtra("DivinationItemEntityList",divinationItemEntities);
                context.startActivity(intent);
            }
        });
        Animation animation_top = AnimationUtils.loadAnimation(
                context, R.anim.anim_recycleview_item_in);
        Animation animation_bottom = AnimationUtils.loadAnimation(
                context, R.anim.anim_recycleview_item_frombottom);
        holder.customImageView_content.setAnimation(animation_top);
        holder.customImageView_title.setAnimation(animation_bottom);
        return convertView;
    }

    class ViewHolder {
        CustomImageView customImageView_title;
        CustomImageView customImageView_content;
    }
}
