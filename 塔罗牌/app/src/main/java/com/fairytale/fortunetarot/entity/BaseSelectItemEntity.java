package com.fairytale.fortunetarot.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lizhen on 16/3/24.
 */
public class BaseSelectItemEntity implements Parcelable {
    private String id;
    private String text;
    private boolean isSelected;
    private boolean isShow;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(text);
        dest.writeInt(isSelected ? 1:0);
        dest.writeInt(isShow ? 1:0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseSelectItemEntity> CREATOR = new Creator<BaseSelectItemEntity>() {
        @Override
        public BaseSelectItemEntity createFromParcel(Parcel source) {
            BaseSelectItemEntity info = new BaseSelectItemEntity();
            info.setId(source.readString());
            info.setText(source.readString());
            info.setSelected(source.readInt() == 1);
            info.setShow(source.readInt() == 1);
            return info;
        }

        @Override
        public BaseSelectItemEntity[] newArray(int size) {
            return new BaseSelectItemEntity[size];
        }
    };

}
