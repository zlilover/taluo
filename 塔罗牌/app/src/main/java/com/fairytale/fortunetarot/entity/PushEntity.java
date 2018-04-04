package com.fairytale.fortunetarot.entity;

import java.io.Serializable;

/**
 * Created by lizhen on 2018/2/1.
 */

public class PushEntity extends BaseEntity implements Serializable{
    private String contentid;
    private int pushtype;

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public int getPushtype() {
        return pushtype;
    }

    public void setPushtype(int pushtype) {
        this.pushtype = pushtype;
    }
}
