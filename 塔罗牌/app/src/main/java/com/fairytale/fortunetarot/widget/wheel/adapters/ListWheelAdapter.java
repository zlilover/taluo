package com.fairytale.fortunetarot.widget.wheel.adapters;

import android.content.Context;

import com.fairytale.fortunetarot.entity.BaseSelectItemEntity;

import java.util.ArrayList;

/**
 * Created by lizhen on 16/3/24.
 */
public class ListWheelAdapter extends AbstractWheelTextAdapter {
    private ArrayList<BaseSelectItemEntity> list;


    public ListWheelAdapter(Context context, ArrayList<BaseSelectItemEntity> list) {
        super(context);
        this.list = list;
    }


    @Override
    public int getItemsCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected CharSequence getItemText(int index) {
        if (index < 0 || index >= list.size()) {
            return "";
        }
        return list.get(index).getText();
    }

    public void setList(ArrayList<BaseSelectItemEntity> list) {
        this.list = list;
        notifyDataChangedEvent();
    }

}
