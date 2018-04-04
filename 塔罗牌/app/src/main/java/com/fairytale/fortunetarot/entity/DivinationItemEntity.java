package com.fairytale.fortunetarot.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lizhen on 2018/4/2.
 */

public class DivinationItemEntity extends BaseEntity implements Parcelable{
    private String title;
    private String infoId;
    private String titleIconPath;
    private String contentIconPath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getTitleIconPath() {
        return titleIconPath;
    }

    public void setTitleIconPath(String titleIconPath) {
        this.titleIconPath = titleIconPath;
    }

    public String getContentIconPath() {
        return contentIconPath;
    }

    public void setContentIconPath(String contentIconPath) {
        this.contentIconPath = contentIconPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(infoId);
        dest.writeString(titleIconPath);
        dest.writeString(contentIconPath);
    }

    public static final Creator<DivinationItemEntity> CREATOR = new Creator<DivinationItemEntity>() {
        @Override
        public DivinationItemEntity createFromParcel(Parcel source) {
            DivinationItemEntity divinationItemEntity = new DivinationItemEntity();
            divinationItemEntity.setTitle(source.readString());
            divinationItemEntity.setInfoId(source.readString());
            divinationItemEntity.setTitleIconPath(source.readString());
            divinationItemEntity.setContentIconPath(source.readString());
            return divinationItemEntity;
        }

        @Override
        public DivinationItemEntity[] newArray(int size) {
            return new DivinationItemEntity[size];
        }
    };
}
