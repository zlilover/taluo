package com.fairytale.fortunetarot.entity;

/**
 * Created by lizhen on 2018/2/7.
 */

public class InfoEntity extends BaseEntity {
    private String contentid;
    private String title;
    private String title_pic;
    private String content_tip;

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_pic() {
        return title_pic;
    }

    public void setTitle_pic(String title_pic) {
        this.title_pic = title_pic;
    }

    public String getContent_tip() {
        return content_tip;
    }

    public void setContent_tip(String content_tip) {
        this.content_tip = content_tip;
    }
}
