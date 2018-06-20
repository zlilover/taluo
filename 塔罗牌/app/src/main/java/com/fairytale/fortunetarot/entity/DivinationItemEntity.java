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
    private String groupName;
    private String groupId;
    private int cardCount;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getCardCount() {
        return cardCount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
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
        dest.writeString(groupName);
        dest.writeString(groupId);
        dest.writeInt(cardCount);
    }

    public static final Creator<DivinationItemEntity> CREATOR = new Creator<DivinationItemEntity>() {
        @Override
        public DivinationItemEntity createFromParcel(Parcel source) {
            DivinationItemEntity divinationItemEntity = new DivinationItemEntity();
            divinationItemEntity.setTitle(source.readString());
            divinationItemEntity.setInfoId(source.readString());
            divinationItemEntity.setTitleIconPath(source.readString());
            divinationItemEntity.setContentIconPath(source.readString());
            divinationItemEntity.setGroupName(source.readString());
            divinationItemEntity.setGroupId(source.readString());
            divinationItemEntity.setCardCount(source.readInt());
            return divinationItemEntity;
        }

        @Override
        public DivinationItemEntity[] newArray(int size) {
            return new DivinationItemEntity[size];
        }
    };
}
