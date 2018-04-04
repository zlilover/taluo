package com.fairytale.fortunetarot.entity;

/**
 * Created by lizhen on 2018/4/1.
 */

public class TitleEntity extends BaseEntity {
    int id;
    String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
